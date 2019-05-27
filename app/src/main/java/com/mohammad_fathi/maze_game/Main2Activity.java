package com.mohammad_fathi.maze_game;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity implements SensorEventListener {

    private float radius = 30, radius_ball, top, bottom, right, left, score = 0;
    private float sensorX,  sensorY, cx, cy, cy_goal, cx_goal, cx_black, cy_black, cy_black_df = 0;
    String str_score = "0000";
    MyView myView;
    long lastSensorUpdateTime;
    float height, width, heightPixels, widthPixels;
    SensorManager sensorManager;
    Sensor sensor;
    Timer timer;
    Handler handler = new Handler();
    List<GameBall> ballList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//------------------- Screen Dimension-----------------------------
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
//-----------------------------------------------------------------

        cy = height / 50;
        cx = width / 2;

        cy_goal = height - (height / 5);
        cx_goal = width / 2;

        myView = new MyView(this);
        setContentView(myView);

        top = radius;
        bottom = height - 3 * radius;
        right = width - radius;
        left = radius;

        Hole_producer_2();

        try {

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (sensorY > 0 && cy >= top && cy <= bottom) {
                        cy += 1;
                    } else if (sensorY < 0 && cy >= top && cy <= bottom) {
                        cy -= 1;
                    } else if (cy < top) {
                        cy = top;
                    } else if (cy > bottom) {
                        cy = bottom;
                    }

                    if (sensorX < 0 && cx >= left && cx <= right) {
                        cx += 1;
                    } else if (sensorX > 0 && cx >= left && cx <= right) {
                        cx -= 1;
                    } else if (cx < left) {
                        cx = left;
                    } else if (cx > right) {
                        cx = right;
                    }

                    cy_black_df += 0.0025;
                    score += 1;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pointInGoal(cx, cy, cx_goal, cy_goal, radius);
                            pointInHole(cx, cy, radius);
                            myView.invalidate();
                        }
                    });
                }
            }, 0, 5);

        } catch (Exception e) {
        }
    }

    public void pointInGoal(float x_test, float y_test, float x_center, float y_center, double radius) {
        double dx = x_test - x_center;
        double dy = y_test - y_center;

        double d = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        if (d < radius) {
            timer.cancel();
            radius_ball = 0;
            myView.invalidate();
            String title = "You Won";
            String message = "Do you want to play again?";
            dialog(myView, title, message);
            //Toast.makeText(this, "You Won", Toast.LENGTH_LONG).show();
        }
    }

    public void pointInHole(float x_test, float y_test, double radius) {
        for (GameBall entry : ballList) {
            float x_center = entry.XCoordinate;
            float y_center = entry.YCoordinate;
            double dx = x_test - x_center;
            double dy = y_test - y_center;

            double d = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            if (d < radius) {
                timer.cancel();
                timer.purge();
                myView.invalidate();
                cy = height / 50;
                cx = width / 2;
                myView.invalidate();
                timer.cancel();
                String title = "Game Over";
                String message = "Do you want to play again?";
                dialog(myView, title, message);

                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {


            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastSensorUpdateTime) > 100) {
                lastSensorUpdateTime = currentTime;
                sensorX = sensorEvent.values[0];
                sensorY = sensorEvent.values[1];
                //sensorZ =  sensorEvent.values[2];
                myView.invalidate();

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    private class MyView extends View {

        private Paint pen;
        Context context1;

        public MyView(Context context) {
            super(context);
            pen = new Paint();
            heightPixels = height;
            widthPixels = width;
            context1 = context;

        }

        int back_color = Color.parseColor("#e2e2e0");

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            pen.setStyle(Paint.Style.FILL);
            pen.setColor(back_color);
            canvas.drawPaint(pen);

            //------------------------------------------------
            //---------------------------------------------------
            // تولید اتوماتیک و دایمی نقاط مشکی
            /*for (int i = 1; i < 6; i++) {
                pen.setColor(Color.BLACK);
                cy_black = cy_rand - cy_black_df;
                cx_black = i * cx_rand + radius * 3 * i;
                canvas.drawCircle(cx_black, cy_black, radius, pen);
                map.put(cx_black, cy_black);

            }*/
            // map_clone.putAll(map);
            //------------------------------------------------------
            // برای زمانیه که مکان دایره های مشکی ثابت بود
            /*for (int i = 1; i < 30; i++) {
                pen.setColor(Color.BLACK);
                cy_black = i * heightPixels / 10 + radius * 3 * i - cy_black_df;
                cx_black = i * widthPixels / 10 + radius * 3 * i;
                canvas.drawCircle(cx_black, cy_black, radius, pen);
                map.put(cx_black, cy_black);
            }*/

            //------------------ Block Point -----------------------------------
            for (GameBall entry : ballList) {
                pen.setColor(back_color);
                float new_Cy_black = entry.YCoordinate - cy_black_df;
                float new_Cx_black = entry.XCoordinate;
                //------------ کد برای کشیدن دایره Hole---------------------
                //canvas.drawCircle(new_Cx_black, new_Cy_black, radius, pen);
                //map.remove(entry.getKey());
                //-----------------------------------------------------------

                int left = (int) (new_Cx_black - radius);
                int right = (int) (new_Cx_black + radius);
                int top = (int) (new_Cy_black - radius);
                int bottom = (int) (new_Cy_black + radius);
                Rect rect = new Rect(left, top, right, bottom);
                canvas.drawRect(rect, pen);
                Drawable drawable = getResources().getDrawable(R.drawable.d4);
                drawable.setBounds(rect);
                drawable.draw(canvas);

                //note:all references  will update original object for classes and reference types
                //there is no need to re add it to collection and push it to hash again
                //decimal and base types are Value types and each reference have separate copy
                entry.XCoordinate = new_Cx_black;
                entry.YCoordinate = new_Cy_black;
                //map.put(new_Cx_black, new_Cy_black);
            }
            //---------------- Score Board --------------------------------
            pen.setColor(Color.BLUE);
            pen.setTextSize(30f);
            pen.setTypeface(Typeface.DEFAULT_BOLD);
            str_score = String.valueOf(score);
            String str_sc = "Score : ";
            canvas.drawText(str_sc, (widthPixels - 200), (float) (35), pen);
            canvas.drawText(str_score,  (widthPixels - 100), (float) (35), pen);

            //----------------Goal Point----------------------------------
            pen.setColor(getResources().getColor(R.color.gold));
            canvas.drawCircle(cx_goal, cy_goal, radius, pen);
            //---------------- Ball --------------------------------------
            pen.setColor(Color.BLUE);
            canvas.drawCircle(cx, cy, radius_ball, pen);
        }
    }

    public void Hole_producer() {

        float minStart_Y = heightPixels / 2;
        float maxStart_Y = 4 * heightPixels;

        float minStart_X = 0;
        float maxStart_X = widthPixels;

        radius_ball = (int) (widthPixels * 0.02);

        float random_num;

        for (int i = 1; i < 20; i++) {
            random_num = (float) Math.random();
            cy_black = random_num * (maxStart_Y - minStart_Y) + minStart_Y;
            cx_black = random_num * (maxStart_X - minStart_X) + minStart_X;
            ballList.add(new GameBall(i, cx_black, cy_black));
        }

    }

    public void Hole_producer_2() {

        float minStart_Y = heightPixels / 2;
        float maxStart_Y = 4 * heightPixels;

        float minStart_X = 0;
        float maxStart_X = widthPixels;

        radius_ball = (int) (widthPixels * 0.02);

        for (int i = 1; i < 50; i++) {
            cy_black = Helpers.getRandom((int) minStart_Y, (int) maxStart_Y);
            cx_black = Helpers.getRandom((int) minStart_X, (int) maxStart_X);
            //map.put(cx_black, cy_black);
            ballList.add(new GameBall(i, cx_black, cy_black));
        }

    }


    public   void dialog(View view, String title, String message) {
        String str_title = title;
        String str_Msg = message;
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(str_title);
        builder.setMessage(str_Msg);
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Main2Activity.this.finish();
            }
        });


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Main2Activity.this, Main2Activity.class);
                Main2Activity.this.finish();
                startActivity(intent);


            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }


}
