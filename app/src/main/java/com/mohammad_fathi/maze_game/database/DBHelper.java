package com.mohammad_fathi.maze_game.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.mohammad_fathi.maze_game.entity.Scores;

public class DBHelper extends SQLiteOpenHelper {
    static String databseName = "ScoreTable";
    static int version = 1;

    public DBHelper(Context context) {
        super(context, databseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table Scores (id Integer primary key , scoreNumber varchar(50) )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(Scores scores){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("scoreNumber",scores.getScoreNumber());
        long rowId= db.insert(Scores.class.getSimpleName(),null,contentValues);
        db.close();
        return rowId;
    }
}
