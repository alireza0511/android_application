package com.jazzb.alireza.errorhandling;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Activity mContext = null;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private String log = "logs";

    public ExceptionHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically
        prepareLogs(e);

        Intent intent = new Intent ();
        intent.setAction ("com.jazzb.alireza.errorhandling.SEND_LOG"); // see step 5.
        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        intent.putExtra("logs", log);

        mContext.startActivity (intent);

        System.exit(1); // kill off the crashed app
    }

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
