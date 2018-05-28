package org.mira.companion;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;


public class CompanionIntentService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SCAN_LAN_FOR_PS4 = "org.mira.companion.action.SCAN_LAN_FOR_PS4";
    private static final String ACTION_CONNECT_TO_PS4 = "org.mira.companion.action.CONNECT_TO_PS4";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "org.mira.companion.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "org.mira.companion.extra.PARAM2";

    public CompanionIntentService() {
        super("CompanionIntentService");
    }


    public static void startActionScanLanForPs4(Context context, String param1, String param2) {
        Intent intent = new Intent(context, CompanionIntentService.class);
        intent.setAction(ACTION_SCAN_LAN_FOR_PS4);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    public static void startActionConnectToPs4(Context context, String param1, String param2) {
        Intent intent = new Intent(context, CompanionIntentService.class);
        intent.setAction(ACTION_CONNECT_TO_PS4);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SCAN_LAN_FOR_PS4.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionScanLanForPs4(param1, param2);
            } else if (ACTION_CONNECT_TO_PS4.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionConnectToPs4(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionScanLanForPs4(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionConnectToPs4(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
