package com.android.yockie.geoquiz;

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

    //Adding member variables
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView ;

    private TrueFalse[] mQuestionsBank = new TrueFalse[]{
        new TrueFalse(R.string.question_text1, true),
        new TrueFalse(R.string.question_text2, true),
        new TrueFalse(R.string.question_text3, false),
        new TrueFalse(R.string.question_text4, true),
        new TrueFalse(R.string.question_text5, true)
    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) method called");
        setContentView(R.layout.activity_quiz);

        //Retrieve information of the state of the program so that it will
        //remember the information it had before rotating the device
        if (savedInstanceState != null){
            //This mehod will work on primitive types of types implementing Serializable
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        //Getting references to widgets
        mTrueButton = (Button) findViewById(R.id.trueButton);
        mFalseButton = (Button) findViewById(R.id.falseButton);
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
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });


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
        int question = mQuestionsBank[mCurrentIndex].getmQuestion();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].ismTrueQuestion();
        //As the string references in xml are int, as we mentioned above
        //we need an int to store the value of the string that will be printed
        //according the the user answer
        int messageResId = 0;
        if(answerIsTrue == userPressedTrue){
            messageResId = R.string.correct_toast;
        }else{
            messageResId = R.string.incorrect_toast;
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
    }
}
