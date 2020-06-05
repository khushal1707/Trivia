package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.example.trivia.controller.AppControler;
import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;

import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;
import com.example.trivia.model.Score;
import com.example.trivia.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
    private ImageButton nextButton;
    private ImageButton prevButton;
    private Button trueButton;
    private Button falseButton;
    private TextView questionText;
    private TextView questionCounter;
    private TextView CurScore;
    private TextView Highscore;
    private int questionIndex =0;
    private List<Question> questionList;
    private Score curscore ;
    private Prefs prefs ;

    private int score=0;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curscore =new Score();

        prefs = new Prefs(MainActivity.this);


        Log.d("second","onClick "+ prefs.getHighScore());

        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        questionCounter = findViewById(R.id.counter_text);
        questionText = findViewById(R.id.question_text);
        Highscore =findViewById(R.id.highscore);
        CurScore = findViewById(R.id.CurScore);


        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        CurScore.setText("Current Score:" + curscore.getScore());

        //  Log.d("second","onClick"+prefs.getHighScore());
        Highscore.setText("Highest Score:"+ prefs.getHighScore());



        questionList = new QuestionBank().getQuestion(new AnswerListAsyncResponse() {
            @Override
            public void processfinished(ArrayList<Question> questionArrayList) {
                questionText.setText(questionList.get(questionIndex).getAnswer());
                questionCounter.setText(questionIndex+1 +" out of "+questionList.size());

                Log.d("inside","onResponse"+ questionArrayList);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prev_button:
                questionIndex = (questionIndex-1);
                if(questionIndex<0)
                    questionIndex = questionList.size()-1;
                updateque();
                break;
            case R.id.next_button:
                questionIndex = (questionIndex+1)%questionList.size();
                updateque();
                break;
            case R.id.true_button:
                checkans(true);
                //questionText.setText(questionList.get(questionIndex).getAnswer());
                questionCounter.setText(questionIndex+1 +" out of "+questionList.size());
                break;
            case R.id.false_button:
                checkans(false);
                //questionText.setText(questionList.get(questionIndex).getAnswer());
                questionCounter.setText(questionIndex+1 +" out of "+questionList.size());
                break;

        }

    }

    private void pluscore(){
        score += 100;
        curscore.setScore(score);
        CurScore.setText("Current Score:" + curscore.getScore());

    }
    private void minuscore(){
        if(score>0)
            score -= 100;
        else
            score = 0;
        curscore.setScore(score);
        CurScore.setText("Current Score:" + curscore.getScore());

    }


    private void checkans(boolean b) {
        boolean correctans = questionList.get(questionIndex).isAnsTrue();
        int toastMessageID =0;

        if(correctans == b){ toastMessageID = R.string.correct_ans;
        fadeView();
        pluscore();}

        else{ toastMessageID = R.string.wrong_ans;
            shakeanim();
        minuscore();}

        Toast.makeText(this,toastMessageID,Toast.LENGTH_SHORT).show();


    }


    private void updateque() {
        questionText.setText(questionList.get(questionIndex).getAnswer());
        questionCounter.setText(questionIndex+1 +" out of "+questionList.size());
    }

    private void shakeanim(){
        Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake_anim);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.RED);

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.WHITE);
                questionIndex = (questionIndex+1)%questionList.size();
                updateque();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeView() {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);

        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.GREEN);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.WHITE);
                questionIndex = (questionIndex+1)%questionList.size();
                updateque();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }

   @Override
    protected void onPause() {
        prefs.saveHighScore(score);
        super.onPause();
    }
}
