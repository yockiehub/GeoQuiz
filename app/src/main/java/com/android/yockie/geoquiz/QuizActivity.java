package com.android.yockie.geoquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity {

    public static final String TAG = "QuizActivity";
    //This variable will be the jey for the key-value pair
    //that will be restored with the bundle
    public static final String KEY_INDEX = "index";
    public static final String OBJECT_RECOVER = "object";

    //Adding member variables
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView ;

    private TrueFalse[] mQuestionsBank = new TrueFalse[]{
        new TrueFalse(R.string.question_text1, true, false),
        new TrueFalse(R.string.question_text2, true, false),
        new TrueFalse(R.string.question_text3, false, false),
        new TrueFalse(R.string.question_text4, true, false),
        new TrueFalse(R.string.question_text5, true, false)
    };
    private int mCurrentIndex = 0;

    private boolean mIsCheater;

    @TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) method called");
        setContentView(R.layout.activity_quiz);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setSubtitle("Worldwide Questions");
            }
        }

        //Retrieve information of the state of the program so that it will
        //remember the information it had before rotating the device
        if (savedInstanceState != null){
            //This method will work on primitive types or types implementing Serializable
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(CheatActivity.EXTRA_SHOWN_ANSWER, false);
            //I use the same status describing the user cheated to update the value of cheat status
            //of the question dealing with.

            mQuestionsBank[0] = (TrueFalse)savedInstanceState.getParcelableArray(OBJECT_RECOVER)[0];
            mQuestionsBank[1] = (TrueFalse)savedInstanceState.getParcelableArray(OBJECT_RECOVER)[1];
            mQuestionsBank[2] = (TrueFalse)savedInstanceState.getParcelableArray(OBJECT_RECOVER)[2];
            mQuestionsBank[3] = (TrueFalse)savedInstanceState.getParcelableArray(OBJECT_RECOVER)[3];
            mQuestionsBank[4] = (TrueFalse)savedInstanceState.getParcelableArray(OBJECT_RECOVER)[4];
        }

        System.out.println("ESTADO DE LA PREGUNTA CHEAT");
        System.out.println(mQuestionsBank[mCurrentIndex].getmCheatedThisQuestion());

        //Getting references to widgets
        mTrueButton = (Button) findViewById(R.id.trueButton);
        mFalseButton = (Button) findViewById(R.id.falseButton);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mNextButton = (ImageButton) findViewById(R.id.nextButton);
        mPrevButton = (ImageButton) findViewById(R.id.prevButton);
        mQuestionTextView = (TextView) findViewById(R.id.questionTV);

        updateQuestion();

        //Set listener for true button
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });
        //Set listener for false button
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });
        //set listener for next button
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                //The status of cheater will be removed once the user moves onto another question
                mIsCheater = false;
                updateQuestion();
            }
        });
        //Set listener for previous button
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (((mCurrentIndex - 1) % mQuestionsBank.length + mQuestionsBank.length)) % mQuestionsBank.length;
                //The status of cheater will be removed once the user moves onto another question
                mIsCheater = false;
                updateQuestion();
            }
        });
        //Set listener for text view. Its function will be the same as next button
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });
        //Set listener for cheat button. This button will start a new activity.
        //We will pass information to the new activity using putExtra, and we
        //will also request some information from the new activity once this is over.
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Here the second activity will start
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionsBank[mCurrentIndex].ismTrueQuestion();
                //Now, we attach to that extra value created in CheatActivity the boolean obtained from
                //the question we are dealing with
                i.putExtra(CheatActivity.EXTRA_IS_TRUE, answerIsTrue);
                //With this form of startActivity, by sending a default value set by the user, we can obtain
                //some information back from that activity. That value in the second parameters is worth to
                //know what activity is responding back in case the parent activity is starting more than one
                startActivityForResult(i, 0);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null){
            return;
        }
        //Retrieves from CheatActivity whether the user cheated or not
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_SHOWN_ANSWER, false);
        if(mIsCheater){
            mQuestionsBank[mCurrentIndex].setmCheatedThisQuestion(mIsCheater);
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() method called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() method called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() method called");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() method called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() method called");
    }

    private void updateQuestion(){
        //Log.d(TAG, "Updating question "+mCurrentIndex, new Exception());
        int question = mQuestionsBank[mCurrentIndex].getmQuestion();
        //NEW PIECE, DOES NOT WORK YET. KEEP WORKING ON IT
        /*if(mIsCheater){
            mQuestionsBank[mCurrentIndex].setmCheatedThisQuestion(mIsCheater);
        }*/
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].ismTrueQuestion();
        //As the string references in xml are int, as we mentioned above
        //we need an int to store the value of the string that will be printed
        //according the the user answer
        int messageResId = 0;

        if(mQuestionsBank[mCurrentIndex].getmCheatedThisQuestion()/*mIsCheater*/){
            messageResId = R.string.judgement_toast;
        }else {
            if (answerIsTrue == userPressedTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this,
                messageResId,
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState method");
        //We save the index in a key-value pair using the constant created on top.
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(CheatActivity.EXTRA_SHOWN_ANSWER, mIsCheater);
        savedInstanceState.putParcelableArray(OBJECT_RECOVER, mQuestionsBank);

    }
}
