package arnodenhond.sendtext;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by arnodenhond on 19/07/16.
 */
public class SendText extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        Bundle remoteInput = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            RemoteInput.getResultsFromIntent(getIntent());
        }
        CharSequence text = null;
        if (remoteInput != null) {
            text = remoteInput.getCharSequence(getString(R.string.app_name));
        } else {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if (clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClip().getItemCount() > 0) {
                text = clipboardManager.getPrimaryClip().getItemAt(0).coerceToText(this);
            }
        }
        if (text == null) {
            Toast.makeText(this, R.string.clipboardempty, Toast.LENGTH_SHORT).show();
        } else {
            sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, text.toString());
            intent.setType("text/plain");
            Intent chooser = Intent.createChooser(intent, text);
            startActivity(chooser);
        }
        finish();
    }
}
