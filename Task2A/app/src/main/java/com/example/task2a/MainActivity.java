package com.example.task2a;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnOk;
    private Button btnCancel;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Нажми на любую кнопку!");
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        if (btnOk != null)
            textView.setText("Кнопка есть!");
        else textView.setText("Кнопки нет!");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                textView.setText("Ok! Нажми Cancel");
                btnOk.setEnabled(false);
                btnCancel.setEnabled(true);
                break;
            case R.id.btnCancel:
                textView.setText("Cancel! Нажми Ok");
                btnCancel.setEnabled(false);
                btnOk.setEnabled(true);
                break;
        }
    }
}
