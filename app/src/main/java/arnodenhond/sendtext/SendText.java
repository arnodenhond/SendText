package arnodenhond.sendtext;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by arnodenhond on 19/07/16.
 */
public class SendText extends Activity {

    public static void sendText(CharSequence text, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SayThanks.SAYTHANKS, MODE_PRIVATE);
        if (!prefs.getBoolean(SayThanks.THANKSDONE, false)) {
            int times = prefs.getInt(SayThanks.SAYTHANKS, 0) + 1;
            prefs.edit().putInt(SayThanks.SAYTHANKS, times).commit();
            if (times >= SayThanks.TIMES_BEG) {
                SayThanks.notify(context);
            }
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        Intent chooser = Intent.createChooser(intent, text);
        context.startActivity(chooser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(STTileService.TILE_NOTIFICATION);
        Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());
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
            sendText(text, this);
        }
        finish();
    }
}
