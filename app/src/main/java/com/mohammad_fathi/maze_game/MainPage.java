package com.mohammad_fathi.maze_game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mohammad_fathi.maze_game.database.DBHelper;

public class MainPage extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        dbHelper = new DBHelper(this);

    }

    public void playGame(View view) {
        Intent intent = new Intent(MainPage.this, Main2Activity.class);
        startActivity(intent);
    }

    public void score(View view) {
//       Integer maxScore= dbHelper.selectBiggest();
//       if(maxScore!=null){
//       String title="The Best Score";
//       String message= String.valueOf(maxScore);
//       dialog(view,title,message);}
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
}
