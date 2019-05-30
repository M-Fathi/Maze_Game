package com.mohammad_fathi.maze_game.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.mohammad_fathi.maze_game.entity.Scores;

public class DBHelper extends SQLiteOpenHelper {
    static String databaseName = "ScoreTable";
    static int version = 1;

    public DBHelper(Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "drop table if exists " + Scores.class.getSimpleName();
        db.execSQL(query);
        query = "create table " + Scores.class.getSimpleName() + " (id Integer primary key , scoreNumber Integer )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(Scores scores) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("scoreNumber", scores.getScoreNumber());
        long rowId = db.insert(Scores.class.getSimpleName(), null, contentValues);
        db.close();
        return rowId;
    }

    public Integer selectBiggest() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Scores.class.getSimpleName(), new String[]{"scoreNumber"}, "scoreNumber = SELECT MAX(scoreNumber)", null, null, null, null);
        int result = cursor.getInt(0);
        cursor.close();
        db.close();
        return result;
    }
    public void update(Scores scores) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("scoreNumber", scores.getScoreNumber());
        db.update(Scores.class.getSimpleName(),contentValues,"id=?",new String[]{String.valueOf(scores.getId())});
        db.close();

    }
}
