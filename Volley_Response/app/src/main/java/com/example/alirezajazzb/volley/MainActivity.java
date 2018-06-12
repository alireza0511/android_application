package com.example.alirezajazzb.volley;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private TextView btnRequest;
    private static Context mCtx;


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://66.54.176.11:8000/api/stores";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRequest =  findViewById(R.id.text);


//                                              sendAndRequestResponse();


        //Toast.makeText(getApplicationContext(),"Response :" + sendAndRequestResponse(), Toast.LENGTH_LONG).show();//display the response on screen
//        dialogFunction();

    }

    public void onResume(){
        super.onResume();

        getString(new VolleyCallback(){
            @Override
            public void onSuccessResponse(String result) {
                Toast.makeText(getApplicationContext(),"Response :" + result, Toast.LENGTH_LONG).show();//display the response on screen
                if (!result.isEmpty()){
                    dialogFunction();
                }
            }
//            @Override
//            public void onSuccessRes(String result){
//              //do stuff here
//            }
        });
    }

    public void getString(final VolleyCallback callback) {
        mRequestQueue = Volley.newRequestQueue(this);


        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url,
                new     Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             // (optionally) some manipulation of the response
                callback.onSuccessResponse(response);
            }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);

    }

/*
    private void sendAndRequestResponse() {
        String result = null;
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int i = 0;
                while (i < 3) {
//            result = postHttpsRequest(apiPath, parameters);
//                    result = response;
                    if (!response.isEmpty()) {
                        break;
                    } else {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ++i;
                        if (i == 3) {
                            try {
                                throw new TimeoutException("Timed out after waiting for " + i + " seconds");
                            } catch (TimeoutException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                 Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);

        return ;
    }
*/
    public void dialogFunction() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView1 = getLayoutInflater().inflate(R.layout.refer_code, null);
        final EditText editText = mView1.findViewById(R.id.editText);

        mBuilder.setView(mView1);
        mBuilder.setCancelable(false);

        mBuilder.setPositiveButton(
                "Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!editText.getText().toString().isEmpty()) {
                            String editReferralCode = editText.getText().toString();


//                                startJazzb();
                                Toast.makeText(MainActivity.this, "Thanks, We get your referral code is :" + editReferralCode, Toast.LENGTH_SHORT).show();




                        } else {
                            dialogFunction();
                            Toast.makeText(MainActivity.this, "Please put your referral code here.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        mBuilder.setNegativeButton(
                "Dismiss",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        startJazzb();
                    }
                });
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }





}
