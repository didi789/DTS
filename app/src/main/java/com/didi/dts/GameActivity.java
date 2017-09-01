package com.didi.dts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    String[] letters = {"א", "ב", "ג", "ד", "ה", "ו", "ז", "ח", "ט", "י", "כ", "ל", "מ", "נ", "ס", "ע", "פ", "צ", "ק", "ר", "ש", "ת", "ם", "ן", "ץ", "ף"};
    Button answerF[] = new Button[8];   // Max song name first word for now - 8
    Button answerS[] = new Button[6];   // Max song name second word for now - 6
    String text = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        String firstWord = text;
        String secondWord = "";
        ArrayList<String> secondWordArray = new ArrayList<>();
        if (text.contains(" ")) {
            firstWord = text.split(" ")[0];
            secondWord = text.split(" ")[1];
            secondWordArray.addAll((Arrays.asList(secondWord.split("(?!^)"))));
        }

        ArrayList<String> firstWordArray = new ArrayList<>(Arrays.asList(firstWord.split("(?!^)")));
        ArrayList<String> WordsArray = new ArrayList<>();
        WordsArray.addAll(firstWordArray);
        WordsArray.addAll(secondWordArray);

        // Fix first answer width and height according to the word
        float width = 50;
        float height = 50;
        float textSize = 30;
        LinearLayout.LayoutParams parms;
        if (firstWordArray.size() > 6) {
            width -= firstWordArray.size() * 1.3;
            height -= firstWordArray.size() * 1.3;
            textSize -= firstWordArray.size();
        }
        parms = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics()));

        for (int i = 0; i < firstWordArray.size(); i++) {
            String buttonID = "btnAF" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            answerF[i] = ((Button) findViewById(resID));
            answerF[i].setVisibility(View.VISIBLE);
            answerF[i].setLayoutParams(parms);
            answerF[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

            answerF[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Button) v).setText("");
                }
            });
        }

        for (int i = 0; i < secondWordArray.size(); i++) {
            String buttonID = "btnAS" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            answerS[i] = ((Button) findViewById(resID));
            answerS[i].setVisibility(View.VISIBLE);
            answerS[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Button) v).setText("");
                }
            });
        }

        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(24);  //0 to 25, heb letters

        while (WordsArray.size() < 14) {
            WordsArray.add(letters[random]);
            random = randomGenerator.nextInt(24);
        }
        Collections.shuffle(WordsArray);
        Button keyboard[] = new Button[14];
        for (int i = 0; i < 14; i++) {
            String buttonID = "btn" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            keyboard[i] = ((Button) findViewById(resID));
            keyboard[i].setText(WordsArray.get(i));
            keyboard[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        // get the text from the button and insert into the answer buttons
        for (Button b : answerF) {
            if (b == null)
                break;
            if (b.getText().equals("")) {
                b.setText(((Button) v).getText().toString());
                checkForWinning();
                return;
            }
        }
        for (Button b : answerS) {
            if (b == null)
                break;
            if (b.getText().equals("")) {
                b.setText(((Button) v).getText().toString());
                checkForWinning();
                return;
            }
        }
    }

    private void checkForWinning() {
        String userAnswer = "";
        for (Button b : answerF) {
            if (b == null)
                break;
            userAnswer += b.getText().toString();
        }
        if (answerS[0] != null) {
            userAnswer += " ";
            for (Button b : answerS) {
                if (b == null)
                    break;
                userAnswer += b.getText().toString();
            }
        }
        if (userAnswer.equals(text)) {
            Toast.makeText(this, "כל הכבוד!", Toast.LENGTH_SHORT).show();
        }
    }

    public void help(View view) {
        // Show dialog to choose between 'show me the artist' to share with friends to give me 1 more second from the song...
        // Options:
        // "show artist name"
        // "Give me one more second from the song"
        // Give me one letter"
        // according to the points.....
    }
}
