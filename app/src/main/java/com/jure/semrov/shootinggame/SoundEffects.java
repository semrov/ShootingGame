package com.jure.semrov.shootinggame;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

/**
 * Created by Jure on 9.3.2018.
 */

public enum SoundEffects {
    INSTANCE;
    public static final int SOUND_EXPLOSION = R.raw.explosion;
    public static final int SOUND_SHOOT = R.raw.beep4;
    public static final int SOUND_ANDROID_GUY_FALLEN_OUT = R.raw.beep9;

    private Context mContext;
    private SoundPool mSoundPool;
    private HashMap<Integer,Integer> mSoundPoolMap;
    private AudioManager mAudioManager;
    private int streamVolume;

    @SuppressWarnings("deprecation")
    public void initSounds(Context context)
    {
        mContext = context;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else
        {
            mSoundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
        }

        mSoundPoolMap = new HashMap<>();
        mSoundPoolMap.put(SOUND_EXPLOSION,mSoundPool.load(mContext,SOUND_EXPLOSION,1));
        mSoundPoolMap.put(SOUND_SHOOT,mSoundPool.load(mContext,SOUND_SHOOT,1));
        mSoundPoolMap.put(SOUND_ANDROID_GUY_FALLEN_OUT,mSoundPool.load(mContext,SOUND_ANDROID_GUY_FALLEN_OUT,1));

        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        streamVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public int getNumSounds()
    {
        return mSoundPoolMap.size();
    }

    public void play_sound(int sound)
    {
        Integer s = mSoundPoolMap.get(sound);
        mSoundPool.play(s,streamVolume,streamVolume,100,0,1f);
    }
}
