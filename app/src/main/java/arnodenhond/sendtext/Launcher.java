package arnodenhond.sendtext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by arnodenhond on 19/07/16.
 */
public class Launcher extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, Info.class));
        finish();
    }

}
