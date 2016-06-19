package com.ipower.keytest;

import android.content.Context;
import android.drm.DrmEvent;
import android.drm.DrmManagerClient;
import android.media.AudioManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity implements DrmManagerClient.OnEventListener{

    /**
     * 当前音量213213132123444
     */
    private int currentVolume;
    /**
     * 控制音量的对象
     */
    public AudioManager mAudioManager;
    /**
     * 系统最大音量
     */
    private int maxVolume;
    /**
     * 确保关闭程序后，停止线程
     */
    private boolean isDestroy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isDestroy = false;
        // 获得AudioManager对象
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);//音乐音量,如果要监听铃声音量变化，则改为AudioManager.STREAM_RING
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);


    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        isDestroy = true;
    }

    /**
     * 监听音量按键的线程
     */
    private Thread volumeChangeThread;

    /**
     * 持续监听音量变化 说明： 当前音量改变时，将音量值重置为最大值减2
     */
    public void onVolumeChangeListener()
    {

        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeChangeThread = new Thread()
        {
            public void run()
            {
                while (!isDestroy)
                {
                    int count = 0;
                    boolean isDerease = false;
                    // 监听的时间间隔
                    try
                    {
                        Thread.sleep(20);
                    } catch (InterruptedException e)
                    {
                        System.out.println("error in onVolumeChangeListener Thread.sleep(20) " + e.getMessage());
                    }

                    if (currentVolume < mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC))
                    {
                        count++;
                        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        // 设置音量等于 maxVolume-2的原因是：当音量值是最大值和最小值时，按音量加或减没有改变，所以每次都设置为固定的值。
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume - 2,
                                AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    }
                    if (currentVolume > mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC))
                    {
                        count++;
                        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume - 2,
                                AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                        if (count == 1)
                        {
                            isDerease = true;
                        }

                    }

                    if (count == 2)
                    {
                        System.out.println("按下了音量+");

                    } else if (isDerease)
                    {
                        System.out.println("按下了音量-");
                    }

                }
            };
        };
        volumeChangeThread.start();
    }

    @Override
    public void onEvent(DrmManagerClient client, DrmEvent event) {

    }


    long[] mHits = new long[3];
    public void onPreferenceTreeClick() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
        mHits[mHits.length-1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis()-500)) {


//            这里放置 三击事件要触发执行的代码


        }

    }
}
