package com.firstapp.jayden.braintrainer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    Button resetButton;//initialize
    Button startGameButton;
    TextView answerText;//initialize
    TextView countDownText;
    TextView questionText;
    TextView questionCounterText;
    GridLayout gridLayout;
    int randomPosition;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    ImageView timesUpImage;
    int score = 0;//keep track score
    int numberOfQuestion = 0;
    boolean gameStart = true;
    public void startTheGame(View view){
         gameStart = true;
         countDownText = (TextView) findViewById(R.id.countDownText);
         questionText = (TextView) findViewById(R.id.questionText);
         questionCounterText = (TextView) findViewById(R.id.questionCounterText);
        gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        startGameButton = (Button) findViewById(R.id.startGameButton);
        countDownText.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        questionCounterText.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        startGameButton.setVisibility(View.INVISIBLE);
        countDownTimer();
    }

    public void countDownTimer(){
       final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.wow_sound);
        timesUpImage = (ImageView)findViewById(R.id.TimesUpImage);
        new CountDownTimer(30000,1000){
            public void onTick(long l){
                countDownText.setText(String.valueOf(l/1000) + " S");
            }
            public void onFinish(){
                gameStart = false;
                answerText.setText("Done");
                resetButton.setVisibility(View.VISIBLE);
                timesUpImage.setVisibility(View.VISIBLE);
                timesUpImage.animate().rotation(3600).setDuration(1200);
                mediaPlayer.start();
            }
        }.start();
    }

    public void answerClick(View view) {//through view I can access each single element
        answerText = (TextView) findViewById(R.id.answerText);
        if (gameStart) {
            if (Integer.toString(randomPosition).equals(view.getTag().toString())) {//buttonClick.getTag show position and randomPosition been created in Oncreate
                answerText.setVisibility(View.VISIBLE);
                answerText.setText("Correct");
                score++;//keep track the score
                restartQuestion();//regenerate the question

            } else {
                answerText.setText("Wrong :(");
                answerText.setVisibility(View.VISIBLE);
                restartQuestion();//regenerate the question
            }
            numberOfQuestion++;//keep track how many questions been played
            questionCounterText.setText(Integer.toString(score) + " / " + Integer.toString(numberOfQuestion));
        }
    }

    public void restartQuestion(){
        if(gameStart)
        {
        Random rand = new Random();
        int a = rand.nextInt(21);//for question
        int b = rand.nextInt(21);//for question

        questionText.setText(Integer.toString(a) + " + " + Integer.toString(b));//set up the question
        randomPosition = rand.nextInt(4);
        answers.clear();//clean all before restart
        for(int i=0; i < 4 ; i++){
            if(i == randomPosition){
                answers.add(a+b);
            }else{
                int wrongAnswer = rand.nextInt(41);
                while (wrongAnswer == a+b){
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);//if it's not duplicate then we add
            }
        }
        button1.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));
        }
    }

    public void restartGame(View view){
        gameStart = true;
        numberOfQuestion = 0;
        score = 0;
        restartQuestion();
        questionCounterText.setText("0/0");
        countDownTimer();
        resetButton.setVisibility(View.INVISIBLE);
        timesUpImage.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetButton = (Button)findViewById(R.id.resetButton);
        answerText = (TextView) findViewById(R.id.answerText);
        questionText = (TextView) findViewById(R.id.questionText);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        restartQuestion();
    }
}
