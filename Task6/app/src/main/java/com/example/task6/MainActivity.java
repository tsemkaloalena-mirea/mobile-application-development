package com.example.task6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView resumesText;
    private TextView listItemsText;
    private EditText idOfDeletingItem;
    private EditText idOfReplacingItem;
    private EditText editTextDevice;
    private EditText editTextExternal;
    private Button saveOnDeviceButton;
    private Button deleteFromDeviceButton;
    private Button saveOnExternalButton;
    private Button deleteFromExternalButton;
    private Button deleteButton;
    private Button replaceButton;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private FeedReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new FeedReaderDbHelper(this);
        listItemsText = (TextView) findViewById(R.id.list_of_items);
        resumesText = (TextView) findViewById(R.id.resumes_text);
        idOfDeletingItem = (EditText) findViewById(R.id.deleting_item_id);
        deleteButton = (Button) findViewById(R.id.delete_button);
        idOfReplacingItem = (EditText) findViewById(R.id.replacing_item_id);
        replaceButton = (Button) findViewById(R.id.replace_button);
        editTextDevice = (EditText) findViewById(R.id.editText_device);
        editTextExternal = (EditText) findViewById(R.id.editText_external);
        saveOnDeviceButton = (Button) findViewById(R.id.save_on_device_button);
        deleteFromDeviceButton = (Button) findViewById(R.id.delete_from_device_button);
        saveOnExternalButton = (Button) findViewById(R.id.save_on_external_button);
        deleteFromExternalButton = (Button) findViewById(R.id.delete_from_external_button);

        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idOfDeletingItem.getText() != null) {
                    Long rowId = Long.valueOf(String.valueOf(idOfDeletingItem.getText()));
                    String selection = FeedReaderContract.FeedEntry._ID + " LIKE ?";
                    String[] selectionArgs = { String.valueOf(rowId)};
                    SQLiteDatabase database = mDbHelper.getWritableDatabase();
                    database.delete("resume_records", selection, selectionArgs);
                    showRecords();
                }
            }
        });

        replaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idOfReplacingItem.getText() != null) {
                    SQLiteDatabase database = mDbHelper.getReadableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(FeedReaderContract.FeedEntry.NUMBER_OF_RESUME, sharedPreferences.getInt("resumes", 0));
                    values.put(FeedReaderContract.FeedEntry.DATE_OF_RESUME, dateFormat.format(Calendar.getInstance().getTime()));
                    String selection = FeedReaderContract.FeedEntry._ID + " LIKE ?";
                    Long rowId = Long.valueOf(String.valueOf(idOfReplacingItem.getText()));
                    String[] selectionArgs = { String.valueOf(rowId) };
                    int count = database.update(
                            FeedReaderContract.FeedEntry.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs
                    );
                    showRecords();
                }
            }
        });

        saveOnDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editTextDevice.getText().toString();
                String fileName = "deviceFile.txt";
                File file = new File(getFilesDir(),fileName);
                FileOutputStream fileOutputStream;
                try {
                    fileOutputStream = openFileOutput(fileName,Context.MODE_PRIVATE);
                    fileOutputStream.write(text.getBytes());
                    fileOutputStream.close();
                    System.out.println("Path to device memory: " + getFilesDir());
                    System.out.println("Free place left on device: " + getFilesDir().getTotalSpace());
                } catch (IOException ioException){
                    return;
                }
            }
        });

        deleteFromDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File files = getFilesDir();
                File delFile = new File(files,"deviceFile.txt");
                delFile.delete();
            }
        });

        saveOnExternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExternalStorageReadable() && isExternalStorageWritable()){
                    File root = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                    System.out.println(root);
                    File textFile = new File(root,"externalFile.txt");
                    try {
                        FileOutputStream fos = new FileOutputStream(textFile);
                        fos.write(editTextExternal.getText().toString().getBytes());
                        fos.flush();
                        fos.close();
                        System.out.println("Path to external memory: " + getExternalFilesDir(null));
                        System.out.println("Free place left in external memory: " + root.getTotalSpace());
                    } catch (IOException ioException){
                        ioException.fillInStackTrace();
                    }
                }
            }
        });

        deleteFromExternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File root = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                File textFile = new File(root,"externalFile.txt");
                textFile.delete();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        int resumes = sharedPreferences.getInt("resumes", -1);
        resumesText.setText("The app was resumed for " + String.valueOf(resumes) + " times.");
        showRecords();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("resumes", sharedPreferences.getInt("resumes", 0) + 1);
        editor.commit();
        makeRecord(sharedPreferences.getInt("resumes", 0));
    }

    public void makeRecord(Integer resumeNumber) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.NUMBER_OF_RESUME, resumeNumber);
        values.put(FeedReaderContract.FeedEntry.DATE_OF_RESUME, dateFormat.format(Calendar.getInstance().getTime()));

        long newRowId = database.insert(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                null,
                values);
    }

    public void showRecords() {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.NUMBER_OF_RESUME,
                FeedReaderContract.FeedEntry.DATE_OF_RESUME
        };
        String sortOrder = FeedReaderContract.FeedEntry.NUMBER_OF_RESUME + " DESC";
        Cursor cursor = database.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        cursor.moveToFirst();
        StringBuilder stringBuilder = new StringBuilder();
        while(! cursor.isAfterLast()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            Integer itemResumeNumber = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.NUMBER_OF_RESUME));
            String itemResumeDate = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.DATE_OF_RESUME));
            System.out.println(itemId + " " + itemResumeNumber + " " + itemResumeDate);
            stringBuilder.append(itemId);
            stringBuilder.append(": ");
            stringBuilder.append(itemResumeNumber);
            stringBuilder.append(", ");
            stringBuilder.append(itemResumeDate);
            stringBuilder.append("\n");
            cursor.moveToNext();
        }
        listItemsText.setText(stringBuilder.toString());
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