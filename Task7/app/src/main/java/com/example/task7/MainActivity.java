package com.example.task7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_CONTACT_REQUEST = 1;
    private Button callBtn;
    private Button mapBtn;
    private Button webBtn;
    private Button mailBtn;
    private Button calendarBtn;
    private Button pickContactBtn;
    private TextView textFromOtherApp;
    private ImageView imageFromOtherApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        Intent intent = getIntent();
        try {
            if (intent.getType().contains("image/")) {
                Uri data = intent.getClipData().getItemAt(0).getUri();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data);
                imageFromOtherApp.setImageBitmap(bitmap);
                Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
                setResult(Activity.RESULT_OK, result);
                //finish();
            } else if ("text/plain".equals(intent.getType())) {
                textFromOtherApp.setText(intent.getClipData().getItemAt(0).getText().toString());
                Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
                setResult(Activity.RESULT_OK, result);
//                finish();
            }
        } catch (NullPointerException | IOException exception) {
            exception.printStackTrace();
        }
    }

    private void initView() {
        callBtn = (Button) findViewById(R.id.call_btn);
        mapBtn = (Button) findViewById(R.id.map_btn);
        webBtn = (Button) findViewById(R.id.web_btn);
        mailBtn = (Button) findViewById(R.id.mail_btn);
        calendarBtn = (Button) findViewById(R.id.calendar_btn);
        pickContactBtn = (Button) findViewById(R.id.pick_contact_btn);
        textFromOtherApp = (TextView) findViewById(R.id.text_from_other_app);
        imageFromOtherApp = (ImageView) findViewById(R.id.image_from_other_app);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });
        webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage();
            }
        });
        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCalendar();
            }
        });
        pickContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickContact();
            }
        });
    }

    public void call() {
        Uri number = Uri.parse("tel:5551234");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (checkIntent(callIntent)) {
            startActivity(callIntent);
        }
    }

    public void openMap() {
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

        if (checkIntent(mapIntent)) {
            startActivity(mapIntent);
        }
    }

    public void openWebPage() {
        Uri webpage = Uri.parse("https://ru.wikipedia.org/wiki/Заглавная_страница");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        if (checkIntent(webIntent)) {
            startActivity(webIntent);
        }
    }

    public void sendMail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"tsemkaloalena@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));

        String title = "Send email";
        Intent chooser = Intent.createChooser(emailIntent, title);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    public void createCalendar() {
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        endTime.set(2012, 0, 19, 10, 30);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
        if (checkIntent(calendarIntent)) {
            startActivity(calendarIntent);
        }
    }

    public void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    public Boolean checkIntent(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                System.out.println(number);
            }
        }
    }
}
