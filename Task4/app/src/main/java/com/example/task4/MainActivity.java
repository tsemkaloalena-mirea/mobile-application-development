package com.example.task4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private String greeting = "";
    static final String KILLING_NUMBER = "killingTangerineNumber";
    static final String RAISING_NUMBER = "raisingTangerineNumber";
    private int killingTangerineNumber;
    private int raisingTangerineNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            killingTangerineNumber = savedInstanceState.getInt(KILLING_NUMBER);
            raisingTangerineNumber = savedInstanceState.getInt(RAISING_NUMBER);
        }
        else {
            killingTangerineNumber = 0;
            raisingTangerineNumber = 0;
        }
        textView = (TextView) findViewById(R.id.textView2);
        greeting = textView.getText() + " " + Build.VERSION.SDK_INT;
        textView.setText(greeting);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(KILLING_NUMBER, killingTangerineNumber);
        savedInstanceState.putInt(RAISING_NUMBER, raisingTangerineNumber);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        imageView.setImageResource(R.drawable.dry_tangerine);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        imageView.setImageResource(R.drawable.tangerine);
        textView.setText(greeting + "!\nYou killed this tangerine for " + killingTangerineNumber + " times.\n" +
                "Then you raised the plant for " + raisingTangerineNumber + " times.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        killingTangerineNumber++;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        raisingTangerineNumber++;
        imageView.setImageResource(R.drawable.tangerine);
    }
}