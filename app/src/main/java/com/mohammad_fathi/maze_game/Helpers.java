package com.mohammad_fathi.maze_game;

import android.content.Context;
import android.media.MediaPlayer;

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

    public static void playSound(Context context){


        MediaPlayer mp = MediaPlayer.create(context, R.raw.symphony9);
        mp.start();
        //mp.setLooping(true);
        //mp.oncom
//        SoundPool soundPool=new SoundPool(5,AudioManager.STREAM_MUSIC,0);
//        int soundId = soundPool.load(context, R.raw.symphony9, 1);
//        soundPool.play(soundId, 1, 1, 0, 0, 1);
//        soundPool.release();
//        soundPool = null;

    }



}
