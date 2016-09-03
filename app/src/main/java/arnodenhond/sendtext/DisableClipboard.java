package arnodenhond.sendtext;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

public class DisableClipboard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        getSharedPreferences(BootReceiver.CLIP_LISTENER_ON, MODE_PRIVATE).edit().putBoolean(BootReceiver.CLIP_LISTENER_ON, false).commit();
        sendBroadcast(new Intent(DisableClipboard.this, BootReceiver.class));
        finish();
    }
}
