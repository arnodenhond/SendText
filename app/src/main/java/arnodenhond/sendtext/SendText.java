package arnodenhond.sendtext;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
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
        Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());
        String text = null;
        if (remoteInput!=null) {
            text = remoteInput.getCharSequence(getString(R.string.app_name)).toString();
        } else {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if (clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                text = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
            }
        }
        if (text==null) {
            Toast.makeText(this,"Clipboard empty", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setType("text/plain");
            Intent chooser = Intent.createChooser(intent, text);
            startActivity(chooser);
        }
        finish();
    }
}
