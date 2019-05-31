package com.example.speechtotext;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 10;
    Button b;
    TextView t;
    private  final int REQ_CODE_SPEECH_INPUT = 5;
    private Calendar calender;
    private SimpleDateFormat simpledateformat;
    private String timesysa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conecttoxml();
        b.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation();
            }


        });
    }

    private void operation() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//performs an action for speech recognisation
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);//supports multiple languages and maintain language free form
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());//uses system languge ie english or many more,put extra is use to pass multiple parameter and multiple messages in single intent
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "HI speak something");//displaying additional values
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);//we pass object of intetnt and request code in start activity for result,start activity for result method is followed by on activity result method
        } catch (ActivityNotFoundException a) {

        }
    }

    private void conecttoxml() {
        b = findViewById(R.id.button);
        t = findViewById(R.id.textView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    t.setText(result.get(0));
                    writedatainfile(t.getText().toString());
                }
                break;
            }
        }
    }
private void writedatainfile(String text)
{
    calender= Calendar.getInstance();
    simpledateformat=new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
    timesysa=simpledateformat.format(calender.getTime());
    timesysa="ExternalData"+timesysa+".txt";
    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    File folder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    File myFile=new File(folder,timesysa);//file name
    writeData(myFile,text);
}
private void writeData(File myFile,String result){
    FileOutputStream fileOutputStream=null;
    try{
        fileOutputStream =new FileOutputStream(myFile);
        fileOutputStream.write(result.getBytes());
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
}


}



