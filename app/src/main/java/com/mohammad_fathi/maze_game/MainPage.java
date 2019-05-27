package com.mohammad_fathi.maze_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);


    }

    public void playGame(View view) {
        Intent intent =new Intent(MainPage.this,Main2Activity.class);
        startActivity(intent);
    }
}
