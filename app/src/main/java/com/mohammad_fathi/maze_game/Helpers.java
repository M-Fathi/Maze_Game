package com.mohammad_fathi.maze_game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class Helpers {


//    public static LinkedHashMap<Float, Float> ShuffleIt(HashMap<Float, Float> map) {
//        LinkedHashMap mapNew = new LinkedHashMap<>();
//
//        List keys = new ArrayList(map.keySet());
//        Collections.shuffle(keys);
//        for (Object o : keys)     // Access keys/values in a random order
//            mapNew.put(o, map.get(o));
//        return mapNew;
//    }

    private static Random r = new Random();

    static int getRandom(int low, int high) {
        return r.nextInt(high - low) + low;
    }



}
