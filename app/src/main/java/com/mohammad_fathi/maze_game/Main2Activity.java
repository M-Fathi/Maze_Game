package com.mohammad_fathi.maze_game;


import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import java.util.Timer;
import java.util.TimerTask;


public class Main2Activity extends AppCompatActivity implements SensorEventListener {

    private int radius = 30, radius_ball = 25, top, bottom, right, left;
    private float sensorX, sensorZ, sensorY, cx, cy, cy_goal, cx_goal,     cy_black_df = 0;
    MyView myView;


    long lastSensorUpdateTime;

    int height, width, heightPixels, widthPixels;


    SensorManager sensorManager;
    Sensor sensor;


    Timer timer;
    Handler handler = new Handler();

    //LinkedHashMap<Float, Float> map;
    List<GameCircle> aList =new ArrayList<GameCircle>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //map = new LinkedHashMap<>();
        //map2 = new LinkedHashMap<>();
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//------------------------------------------------
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
//------------------------------------------------

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

        Hole_producer();

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
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pointInGoal(cx, cy, cx_goal, cy_goal, radius);
                            pointInHole(cx, cy,  radius);
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
            radius_ball = 0;
            myView.invalidate();
            Toast.makeText(this, "You Won", Toast.LENGTH_LONG).show();
            timer.cancel();
            timer.purge();
            return;
        } else {
        }
    }


    public void pointInHole(float x_test, float y_test,  double radius) {


        for (GameCircle entry : aList) {
            float x_center = entry.XCordinate;
            float y_center = entry.YCordinate;

            double dx = x_test - x_center;
            double dy = y_test - y_center;

            double d = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            if (d < radius) {
                cy = height / 50;
                cx = width / 2;
                myView.invalidate();
                Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
                timer.cancel();
                timer.purge();
                return;
                //break;
            } else {
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
                sensorZ = sensorZ = sensorEvent.values[2];

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
            //pen.setTextSize(30f);

            pen.setColor(Color.BLUE);
            canvas.drawCircle(cx, cy, radius_ball, pen);

            //------------------------------------------------
            pen.setColor(getResources().getColor(R.color.gold));
            canvas.drawCircle(cx_goal, cy_goal, radius, pen);
            //------------------------------------------------

            /*float minStart_Y = heightPixels/2;
            float maxStart_Y = 3*heightPixels;

            float minStart_X = 0;
            float maxStart_X = widthPixels;

            float random_num = (float) Math.random();
            float cy_rand = random_num * (maxStart_Y - minStart_Y) + minStart_Y;
            float cx_rand = random_num * (maxStart_X - minStart_X) + minStart_X;*/

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

            //-------------------------------------------------------
            //map=ShuffleIt(map);
            for (GameCircle entry : aList) {
                pen.setColor(Color.BLACK);
                 entry.YCordinate=entry.YCordinate-cy_black_df;

                canvas.drawCircle(entry.XCordinate, entry.YCordinate, radius, pen);
                //map.remove(entry.getKey());

                //(new GameCircle(i,cx_black,cy_black));
            }
        }
    }

    public void Hole_producer() {

        float minStart_Y = heightPixels/2;
        float maxStart_Y = 4*heightPixels;

        float minStart_X = 0;
        float maxStart_X = widthPixels;

        float random_num;


        for (int i = 1; i < 20; i++) {
            random_num = (float) Math.random();
            float  cy_black = random_num * (maxStart_Y - minStart_Y) + minStart_Y;
            float  cx_black = random_num * (maxStart_X - minStart_X) + minStart_X;
            //map.put(cx_black, cy_black);
            aList.add(new GameCircle(i,cx_black,cy_black));

        }
         //ShuffleList();
        Collections.shuffle( aList);

    }




}
