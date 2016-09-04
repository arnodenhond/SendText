package arnodenhond.sendtext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DisableClipboard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSharedPreferences(BootReceiver.CLIP_LISTENER_ON, MODE_PRIVATE).edit().putBoolean(BootReceiver.CLIP_LISTENER_ON, false).commit();
        sendBroadcast(new Intent(this, BootReceiver.class));
        finish();
    }
}
