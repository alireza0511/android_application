package com.jazzb.alireza.errorhandling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private String log = "logs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                handleUncaughtException(t,e);
//            }
//        });

        TextView tvBtn = findViewById(R.id.btn);
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dsds = 1/0;
            }
        });

        Button btn2 = findViewById(R.id.button);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });


    }
    public void handleUncaughtException (Thread thread, Throwable e)
    {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically
        prepareLogs(e);

        Intent intent = new Intent ();
        intent.setAction ("com.jazzb.alireza.errorhandling.SEND_LOG"); // see step 5.
        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        intent.putExtra("logs", log);

        startActivity (intent);

        System.exit(1); // kill off the crashed app
    }

    //*********************************************************
    public void reportLogs(String errorLogs) {
        Logger.getLogger("custom error",errorLogs);
        log = errorLogs;


//        Intent intent = new Intent ();
//        intent.setAction ("com.jazzb.alireza.errorhandling.SEND_LOG"); // see step 5.
//        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
//        intent.putExtra("logs", errorLogs);
//        startActivity (intent);
        //Open Send log activity
//        Intent intent = new Intent();
//        intent.setAction("**.controller.logger.SendLogActivity"); // see step 5.
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
//        intent.putExtra("logs", errorLogs.toString());
//        myContext.startActivity(intent);
    }

    public void prepareLogs(Throwable exception){
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("************ CAUSE OF ERROR ************" + LINE_SEPARATOR);
        errorReport.append(stackTrace.toString());
        errorReport.append(LINE_SEPARATOR);
//
//        Long tsLong = System.currentTimeMillis()/1000;
//        String ts = tsLong.toString();
//        errorReport.append( "************ Timestamp ************" + ts);
        Date currentTime = Calendar.getInstance().getTime();
        errorReport.append( "************ Time ************" + LINE_SEPARATOR);
        errorReport.append(currentTime);
        errorReport.append(LINE_SEPARATOR+ "************ DEVICE INFORMATION ***********" + LINE_SEPARATOR);
        errorReport.append("Brand: "+Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: "+Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: "+Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: "+Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: "+Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append(LINE_SEPARATOR + "************ BUILD INFO ************" + LINE_SEPARATOR);
        errorReport.append("SDK: "+Build.VERSION.SDK);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: "+Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: "+Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);

        //reportLogs(errorReport.toString());
        log = errorReport.toString();

    }

}
