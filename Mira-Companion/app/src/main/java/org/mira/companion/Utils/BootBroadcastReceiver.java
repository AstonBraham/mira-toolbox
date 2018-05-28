package org.mira.companion.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import org.mira.companion.CompanionIntentService;


public class BootBroadcastReceiver extends BroadcastReceiver {     

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";   
    @Override   
    public void onReceive(Context context, Intent intent) {

        // BOOT_COMPLETED start Service
        if (intent.getAction().equals(ACTION)) {   
            // Start Companion Service on Boot
          Intent serviceIntent = new Intent(context, CompanionIntentService.class);
          context.startService(serviceIntent);

        }   
    }    
}  
