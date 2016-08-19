package arnodenhond.sendtext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by arnodenhond on 19/08/16.
 */
public class ProcessText extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        Intent chooser = Intent.createChooser(intent, text);
        startActivity(chooser);
        finish();
    }
}

