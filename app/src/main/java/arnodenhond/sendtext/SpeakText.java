package arnodenhond.sendtext;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

/**
 * Created by arnodenhond on 02/09/16.
 */
public class SpeakText extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(STTileService.TILE_NOTIFICATION);
        if (data != null && data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) != null) {
            String text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            SendText.sendText(text, this);
        }
        finish();
    }

}
