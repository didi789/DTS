package com.didi.dts;
//wifi adb
//adb tcpip 5555
//adb connect 192.168.0.101:5555

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final PlayPauseView view = (PlayPauseView) findViewById(R.id.play_pause_view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.toggle();
//            }
//        });
    }

    public void test(View view) {
        EditText ETtext = (EditText) findViewById(R.id.textInput);
        String text = ETtext.getText().toString();

        if (text.contains(" ")) {
            if ((text.split(" ")[0].length() <= 8) && (text.split(" ")[1].length() <= 6)) {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("text", "מתוק כשמרלי");
                startActivity(intent);
                return;
            }
        } else if (text.length() <= 8) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("text", "מתוק כשמרלי");
            startActivity(intent);
            return;
        }

        Toast.makeText(this, "מילה ראשונה עד 8 תווים מילה שנייה עד 6!", Toast.LENGTH_LONG).show();
    }
}
