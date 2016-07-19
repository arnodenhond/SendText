package arnodenhond.sendtext;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

/**
 * Created by arnodenhond on 19/07/16.
 */
public class Info extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        int version = 0;
        try {
            version = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException nnfe) {
        }
        setTitle(String.format(getString(R.string.header), getString(R.string.app_name), version));
        hideDisableLauncher();
    }

    private void hideDisableLauncher() {
        if (getPackageManager().getComponentEnabledSetting(new ComponentName(this, Launcher.class))==PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            findViewById(R.id.infoheader).setVisibility(View.GONE);
            findViewById(R.id.disablelauncher).setVisibility(View.GONE);
        }
    }

    public void postcomment(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=arnodenhond.sendtext"));
        startActivity(intent);
    }

    public void shareurl(View v) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + " http://play.google.com/store/apps/details?id=arnodenhond.sendtext");
        startActivity(intent);
    }

    public void showappsettings(View v) {
        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:arnodenhond.sendtext")));
    }

    public void disablelauncher(View v) {
        getPackageManager().setComponentEnabledSetting(new ComponentName(this, Launcher.class), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        hideDisableLauncher();
    }
}
