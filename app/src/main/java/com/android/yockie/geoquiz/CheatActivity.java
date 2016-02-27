package com.android.yockie.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by palme_000 on 02/21/16.
 */
public class CheatActivity extends AppCompatActivity {

    //The long name comes handy to avoid collisions with other apps constants
    public static final String EXTRA_IS_TRUE = "com.android.yockie.geoquiz.anser_is_true";
    public static final String EXTRA_SHOWN_ANSWER = "com.android.yockie.geoquiz.answer_shown";
    public static final String CHEAT_TEXT = "com.android.yockie.geoquiz.cheat_text";

    private boolean mAnswerIsTrue;
    private boolean mShowAnswerClicked = false;
    private String savedCheatText;

    private TextView mAnswerTextView;
    private Button mShowAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null){

            mShowAnswerClicked = savedInstanceState.getBoolean(EXTRA_SHOWN_ANSWER, false);
            setAnswerShownResult(mShowAnswerClicked);

            savedCheatText = savedInstanceState.getString(CHEAT_TEXT,"");
        }
        System.out.println(mShowAnswerClicked);
        //The second value of getBooleanExtra is a default value given if no value is found
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mAnswerTextView.setText(savedCheatText);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);

        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowAnswerClicked = true;
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText((R.string.false_button));
                }
                setAnswerShownResult(true);
            }
        });
        setAnswerShownResult(mShowAnswerClicked);
    }


    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_SHOWN_ANSWER, isAnswerShown);
        //This method is to send data back to parent activity
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(this.EXTRA_SHOWN_ANSWER, mShowAnswerClicked);
        savedInstanceState.putString(this.CHEAT_TEXT, mAnswerTextView.getText().toString());
    }
}
