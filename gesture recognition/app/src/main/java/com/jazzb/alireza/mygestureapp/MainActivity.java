package com.jazzb.alireza.mygestureapp;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private GestureDetector mDetector;

    final String DEBUG_TAG="MainActivity";

    float x1,x2;
    float y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
//
//            @Override
//            public void onLongPress(MotionEvent event) {
//
//                Log.d(DEBUG_TAG, " onLongPress: " + event.toString());
//                Toast.makeText(MainActivity.this, " onLongPress " , Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public boolean onDown(MotionEvent event) {
//                Log.d(DEBUG_TAG," onDown: " + event.toString());
//                Toast.makeText(MainActivity.this, " onDown " , Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//
//            @Override
//            public boolean onFling(MotionEvent event1, MotionEvent event2,
//                                   float velocityX, float velocityY) {
//                Log.d(DEBUG_TAG, " onFling: " + event1.toString()+event2.toString());
//                Toast.makeText(MainActivity.this, " onFling " , Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//                                    float distanceY) {
//                Log.d(DEBUG_TAG, " onScroll: " + e1.toString()+e2.toString());
//                //Toast.makeText(MainActivity.this, " onScroll " , Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//
//            @Override
//            public void onShowPress(MotionEvent event) {
//                Log.d(DEBUG_TAG, " onShowPress: " + event.toString());
//                Toast.makeText(MainActivity.this,                 " onShowPress: "
//                        , Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent event) {
//                Log.d(DEBUG_TAG, " onSingleTapUp: " + event.toString());
//                Toast.makeText(MainActivity.this, " onSingleTapUp " , Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//
//            @Override
//            public boolean onDoubleTap(MotionEvent event) {
//                Log.d(DEBUG_TAG, " onDoubleTap: " + event.toString());
//                Toast.makeText(MainActivity.this, " onDoubleTap " , Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent event) {
//                Log.d(DEBUG_TAG, " onDoubleTapEvent: " + event.toString());
//                Toast.makeText(MainActivity.this, " onDoubleTapEvent " , Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent event) {
//                Log.d(DEBUG_TAG, " onSingleTapConfirmed: " + event.toString());
//                Toast.makeText(MainActivity.this, " onSingleTapConfirmed " , Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//        });

    }
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return mDetector.onTouchEvent(ev)  || super.onTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(DEBUG_TAG,"Action was DOWN");
                x1 = event.getX();
                y1 = event.getY();
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                //Toast.makeText(this, "Action was MOVE", Toast.LENGTH_SHORT).show();
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                //Toast.makeText(this, "Action was UP", Toast.LENGTH_SHORT).show();
                x2 = event.getX();
                y2 = event.getY();
                //if left to right sweep event on screen
                if ((x2 + x1)/2 < (x2 - x1))
                {
                    Toast.makeText(this, "Left to Right Swap Performed", Toast.LENGTH_SHORT).show();
                }

                // if right to left sweep event on screen
                if ((x1 > x2) && (x2 + x1)/2 < (x1 - x2))
                {
                    Toast.makeText(this, "Right to Left Swap Performed", Toast.LENGTH_SHORT).show();
                }

                // if UP to Down sweep event on screen
                if (y1 < y2 && (y2 + y1)/2 < (y2 - y1))
                {
                    Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_SHORT).show();
                }

                                                 //if Down to UP sweep event on screen
                if (y1 > y2 && (y2 + y1)/2 < (y1 - y2))
                {
                    Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_SHORT).show();
                }

                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }
}
