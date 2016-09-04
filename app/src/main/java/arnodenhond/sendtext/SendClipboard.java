package arnodenhond.sendtext;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by arnodenhond on 04/09/16.
 */
public class SendClipboard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(BootReceiver.CLIPBOARD_NOTIFICATION);

        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_TEXT);
        SendText.sendText(text, this);
        finish();
    }

}
