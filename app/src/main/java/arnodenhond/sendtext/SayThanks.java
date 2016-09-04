package arnodenhond.sendtext;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

/**
 * Created by arnodenhond on 03/09/16.
 */
public class SayThanks extends Activity {

    public static final String SAYTHANKS = "saythanks";
    public static final String THANKSDONE = "thanksdone";
    public static final int THANKS_NOTIFICATION = 1;
    public static final int TIMES_BEG = 5;

    public static void notify(Context context) {
        PendingIntent intent = PendingIntent.getActivity(context, 0, new Intent(context, SayThanks.class), PendingIntent.FLAG_UPDATE_CURRENT);
        int times = context.getSharedPreferences(SAYTHANKS, MODE_PRIVATE).getInt(SAYTHANKS, 0);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setPriority(Notification.PRIORITY_LOW);
        builder.setStyle(new Notification.BigTextStyle().bigText(context.getString(R.string.infofooter)).setBigContentTitle(String.format(context.getString(R.string.timesused), times)));
        builder.addAction(0, context.getString(R.string.postcomment), intent);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setTicker(context.getString(R.string.postcomment));
        builder.setSmallIcon(R.drawable.ic_comment_white_48dp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            builder.setShowWhen(false);
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(THANKS_NOTIFICATION, builder.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(THANKS_NOTIFICATION);
        getSharedPreferences(SAYTHANKS, MODE_PRIVATE).edit().putBoolean(THANKSDONE, true).commit();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
        startActivity(intent);
        finish();
    }

}
