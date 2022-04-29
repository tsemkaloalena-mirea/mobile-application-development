package com.example.prac6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static final String APP_TOTAL_STARTS = "Total Score";

    private Button leftButton;
    private Button centerButtonTop;
    private Button centerButtonBottom;
    private Button rightButton;
    private Button deleteDeviceFile;
    private Button deleteExternalFile;

    private TextView leftText;
    private EditText centerText;
    private TextView rightText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leftButton = (Button) findViewById(R.id.leftButton);
        centerButtonTop = (Button) findViewById(R.id.centerButtonTop);
        centerButtonBottom = (Button) findViewById(R.id.centerButtonBottom);
        rightButton = (Button) findViewById(R.id.rightButton);
        deleteDeviceFile = (Button) findViewById(R.id.centerDeleteButtonTop);
        deleteExternalFile = (Button) findViewById(R.id.centerDeleteButtonBottom);

        leftText = (TextView) findViewById(R.id.left_text);
        centerText = (EditText) findViewById(R.id.center_text);
        rightText = (TextView) findViewById(R.id.right_text);

        sharedPreferences = getSharedPreferences("MyPreferences",Context.MODE_PRIVATE);

        int resumes_count = sharedPreferences.getInt("resumes_count",0);
        leftText.setText(String.valueOf(resumes_count));

        leftButton = (Button)findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int resumes_count = sharedPreferences.getInt("resumes_count",-1);
                leftText.setText(String.valueOf(resumes_count));
            }
        });


        centerButtonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = centerText.getText().toString();
                String fileName = "evselit.txt";
                File file = new File(getFilesDir(),fileName);
                FileOutputStream fileOutputStream;
                try {
                    fileOutputStream = openFileOutput(fileName,Context.MODE_PRIVATE);
                    fileOutputStream.write(text.getBytes());
                    fileOutputStream.close();
                    System.out.println("Путь до внутренней памяти: " + getFilesDir());
                    System.out.println("Свободное место на внутреннем устройстве: " + getFilesDir().getTotalSpace());
                } catch (IOException ioException){
                    return;
                }
            }
        });


        centerButtonBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExternalStorageReadable() && isExternalStorageWritable()){
                    File root = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                    System.out.println(root);
                    File textFile = new File(root,"genjilo.txt");
                    try {
                        FileOutputStream fos = new FileOutputStream(textFile);
                        fos.write(centerText.getText().toString().getBytes());
                        fos.flush();
                        fos.close();
                        System.out.println("Путь до внешней памяти: " + getExternalFilesDir(null));
                        System.out.println("Свободное место на внешнем устройстве: " + root.getTotalSpace());
                    } catch (IOException ioException){
                        ioException.fillInStackTrace();
                    }
                }
            }
        });


        deleteDeviceFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File files = getFilesDir();
                File delFile = new File(files,"evselit.txt");
                delFile.delete();
            }
        });


        deleteExternalFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File root = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                File textFile = new File(root,"genjilo.txt");
                textFile.delete();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("resumes_count",sharedPreferences.getInt("resumes_count",0)+1);
        editor.commit();
    }

    private boolean isExternalStorageWritable(){
        return (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()));
    }

    public boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }else{
            return false;
        }
    }

}