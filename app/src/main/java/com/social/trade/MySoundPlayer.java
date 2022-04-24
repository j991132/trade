package com.social.trade;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.HashMap;

public class MySoundPlayer {
    public static final int opening = R.raw.reopening;
    public static final int b = R.raw.b;
    public static final int camera = R.raw.camera2;
    public static final int confirm = R.raw.confirm2;
    public static final int diring = R.raw.diring;
    public static final int loading = R.raw.loading;
    public static final int result = R.raw.result;
    public static final int tradeok = R.raw.tradeok;

    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundPoolMap;

    //초기화
    public static void initSounds(Context context){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
        soundPoolMap = new HashMap(8);
        soundPoolMap.put(opening, soundPool.load(context, opening,1));
        soundPoolMap.put(b, soundPool.load(context, b,2));
        soundPoolMap.put(camera, soundPool.load(context, camera,3));
        soundPoolMap.put(confirm, soundPool.load(context, confirm,4));
        soundPoolMap.put(diring, soundPool.load(context, diring,5));
        soundPoolMap.put(loading, soundPool.load(context, loading,6));
        soundPoolMap.put(result, soundPool.load(context, result,7));
        soundPoolMap.put(tradeok, soundPool.load(context, tradeok,8));
    }

    public static void play(int raw_id){
        if (soundPoolMap.containsKey(raw_id)){
            soundPool.play(soundPoolMap.get(raw_id),1,1,1,0,1f);
        }
    }
}
