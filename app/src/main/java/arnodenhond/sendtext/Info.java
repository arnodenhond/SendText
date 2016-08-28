package arnodenhond.sendtext;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by arnodenhond on 19/07/16.
 */
public class Info extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        String version = new String();
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException nnfe) {
        }
        ((TextView) findViewById(R.id.infonougat)).setTextIsSelectable(true);
        ((TextView) findViewById(R.id.infomarshmallow)).setTextIsSelectable(true);
        hideNougat();
        setTitle(String.format(getString(R.string.header), getString(R.string.app_name), version));
    }

    private void hideNougat() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            findViewById(R.id.infonougat).setVisibility(View.GONE);
            findViewById(R.id.imagenougat).setVisibility(View.GONE);
            findViewById(R.id.dividernougat).setVisibility(View.GONE);
        }
    }

    public void postcomment(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
        startActivity(intent);
    }

    public void shareurl(View v) {
        String text = getString(R.string.app_name)+": "+getString(R.string.infoheader)+" https://play.google.com/store/apps/details?id="+getPackageName();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        Intent chooser = Intent.createChooser(intent, text);
        startActivity(chooser);
    }

}
