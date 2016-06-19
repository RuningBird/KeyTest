package com.ipower.msg;

import android.telephony.SmsManager;

/**
 * Created by ipowe on 2016/6/19.
 */
public class MSG {
    String msg = "啊啊啊啊啊";
    String number = "18681808136";


    public void sendMsg() {
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(number, null, msg, null, null);
    }
}
