package arnodenhond.sendtext;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by arnodenhond on 19/07/16.
 */
public class Info extends Activity {

    CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            getSharedPreferences(BootReceiver.CLIP_LISTENER_ON, MODE_PRIVATE).edit().putBoolean(BootReceiver.CLIP_LISTENER_ON, b).commit();
            sendBroadcast(new Intent(Info.this, BootReceiver.class));
        }
    };
    private Switch enabled;
    SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            enabled.setOnCheckedChangeListener(null);
            enabled.setChecked(sharedPreferences.getBoolean(s, false));
            enabled.setOnCheckedChangeListener(switchListener);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        String version = new String();
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException nnfe) {
        }
        setTitle(String.format(getString(R.string.header), getString(R.string.app_name), version));

        enabled = (Switch) findViewById(R.id.enabled);

        ((TextView) findViewById(R.id.infojellybean)).setTextIsSelectable(true);
        ((TextView) findViewById(R.id.infonougat)).setTextIsSelectable(true);
        ((TextView) findViewById(R.id.infomarshmallow)).setTextIsSelectable(true);

        hideMarshmallow();
        hideNougat();
    }

    @Override
    protected void onStart() {
        super.onStart();
        enabled.setChecked(getSharedPreferences(BootReceiver.CLIP_LISTENER_ON, MODE_PRIVATE).getBoolean(BootReceiver.CLIP_LISTENER_ON, false));
        enabled.setOnCheckedChangeListener(switchListener);
        getSharedPreferences(BootReceiver.CLIP_LISTENER_ON, MODE_PRIVATE).registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getSharedPreferences(BootReceiver.CLIP_LISTENER_ON, MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    private void hideMarshmallow() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            findViewById(R.id.infomarshmallow).setVisibility(View.GONE);
            findViewById(R.id.imagemarshmallow).setVisibility(View.GONE);
            findViewById(R.id.dividermarshmallow).setVisibility(View.GONE);
        }
    }

    private void hideNougat() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            findViewById(R.id.infonougat).setVisibility(View.GONE);
            findViewById(R.id.imagenougat).setVisibility(View.GONE);
            findViewById(R.id.dividernougat).setVisibility(View.GONE);
        }
    }

    public void postcomment(View v) {
        startActivity(new Intent(this,SayThanks.class));
    }

}
