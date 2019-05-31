package com.mohammad_fathi.maze_game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mohammad_fathi.maze_game.database.DBHelper;

public class MainPage extends AppCompatActivity {

    DBHelper dbHelper;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        dbHelper = new DBHelper(this);

    }

    public void playGame(View view) {
        Intent intent = new Intent(MainPage.this, Main2Activity.class);
        intent.putExtra("ball_color", color);
        startActivity(intent);
    }

    public void score(View view) {

//---------------- Database -----------------
        String message = "0000";
        String maxScore = dbHelper.selectBiggest();
        if (maxScore != null) {
            message = String.valueOf(maxScore);
        }

        String title = "The Best Score";
        dialog(view, title, message);
    }


    public void dialog(View view, String title, String message) {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void customDialog(View view) {
        final AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view1 = LayoutInflater.from(this).inflate(R.layout.setting_dialog, null);
        builder.setView(view1);
        alertDialog = builder.create();
        alertDialog.show();

        Button btn_ok_setting = view1.findViewById(R.id.btn_ok_setting);
        final RadioGroup rg = view1.findViewById(R.id.rg_color);
        final RadioButton rb_blue = view1.findViewById(R.id.rb_blue);
        RadioButton rb_black = view1.findViewById(R.id.rb_black);
        RadioButton rb_green = view1.findViewById(R.id.rb_green);
        RadioButton rb_red = view1.findViewById(R.id.rb_red);

//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radioButton=group.findViewById(checkedId);
//                String str_color = radioButton.getText().toString();
//                switch (str_color) {
//                    case "Blue":
//                        color = R.color.blue;
//                        break;
//                    case "Black":
//                        color = R.color.black;
//                        break;
//                    case "Red":
//                        color = R.color.colorAccent;
//                        break;
//                    case "Green":
//                        color = R.color.green;
//                        break;
//                    default:
//                        color = R.color.blue;
//                }
//            }
//        });
        try {
            btn_ok_setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedId = rg.getCheckedRadioButtonId();
                    RadioButton radioButton = view1.findViewById(selectedId);
                    String str_color = radioButton.getText().toString();
                    switch (str_color) {
                        case "Blue":
                            //color = R.color.blue;
                            color = Color.BLUE;
                            break;
                        case "Black":
                            color = Color.BLACK;
                            break;
                        case "Red":
                            color = Color.RED;
                            break;
                        case "Green":
                            color = Color.GREEN;
                            break;
                        default:
                            color = Color.BLUE;
                    }


                    alertDialog.dismiss();
                }

            });
        } catch (Exception e) {
        }
    }

    public void btn_setting(View view) {
        try {
            customDialog(view);
        } catch (Exception e) {
        }

    }


}
