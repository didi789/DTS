package com.didi.dts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.didi.dts.utils.HelpDialogActivity;
import com.didi.dts.utils.PlayPauseView;

import java.io.IOException;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements boardFragment.OnFragmentInteractionListener {

    int current = 0;
    String text = " ";
    int Xseconeds = 10000;
    MediaPlayer mPlayer;
    boardFragment firstFragment;
    private CountDownTimer timer;
    private Context context;
    private ArrayList<Song> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_game);
        initSongList();
        mPlayer = MediaPlayer.create(GameActivity.this, songList.get(current).getLocalAudio());
        final PlayPauseView play = (PlayPauseView) findViewById(R.id.play_pause_view);
        final TextView countDownTV = (TextView) findViewById(R.id.countDownTV);
        final Boolean[] isPlaying = {false};

        final Intent intent = new Intent(this, HelpDialogActivity.class);
        Button help_btn = (Button) findViewById(R.id.help);
        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, 1);
            }
        });



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying[0]) {
                    isPlaying[0] = true;
                    play.toggle();
                    mPlayer.pause();
                } else {
                    play.toggle();
                    isPlaying[0] = true;
                    mPlayer.start();
                    timer = new CountDownTimer(Xseconeds, 1000) {//play the song for X secondes

                        @Override
                        public void onTick(long millisUntilFinished) {
                            countDownTV.setText(Long.toString(millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            try {
                                isPlaying[0] = false;
                                mPlayer.reset();
                                AssetFileDescriptor afd = context.getResources().openRawResourceFd(songList.get(current).getLocalAudio());
                                if (afd == null) return;
                                mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                                afd.close();
                                try {
                                    mPlayer.prepare();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (Exception e) {
                                Log.e("Error", "Error: " + e.toString());
                            }
                        }
                    }.start();
                }
            }
        });

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            firstFragment = boardFragment.newInstance(getIntent().getStringExtra("text"));

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            //firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String helpCode = data.getStringExtra(HelpDialogActivity.RESULT_CONTRYCODE);
            Toast.makeText(this, "בחרת בעזרה בקוד: " + helpCode, Toast.LENGTH_LONG).show();
        }
    }

    public void nextSong(View v) {
        current++;
        firstFragment = boardFragment.newInstance(songList.get(current).getName());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.fragment_container, firstFragment);
        transaction.addToBackStack(null);
        transaction.commit();


        mPlayer.reset();
        AssetFileDescriptor afd = context.getResources().openRawResourceFd(songList.get(current).getLocalAudio());
        if (afd == null) return;
        try {
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initSongList() {
        songList = new ArrayList<>();
        songList.add(new Song("מתוק כשמר לי", "אליעד נחום", "https://www.youtube.com/results?search_query=מתוק+כשמר+לי", R.raw.song1));
        songList.add(new Song("לב חופשי", "מוקי", "https://www.youtube.com/results?search_query=לב חופשי", R.raw.song2));
        songList.add(new Song("אלף כבאים", "גידי גוב", "https://www.youtube.com/results?search_query=אלף כבאים", R.raw.song3));
        songList.add(new Song("מיאמי", "אליעד נחום", "https://www.youtube.com/results?search_query=מיאמי", R.raw.song4));
    }

    public void help(View view) {
        // Show dialog to choose between 'show me the artist' to share with friends to give me 1 more second from the song...
        // Options:
        // "show artist name"
        // "Give me one more second from the song"
        // Give me one letter"
        // according to the points.....
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
