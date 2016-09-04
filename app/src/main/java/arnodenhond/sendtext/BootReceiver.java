package arnodenhond.sendtext;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

public class BootReceiver extends BroadcastReceiver {

    public static final int CLIPBOARD_NOTIFICATION = 3;
    public static String CLIP_LISTENER_ON = "cliplisteneron";
    private static Context context;
    static ClipboardManager.OnPrimaryClipChangedListener clipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            CharSequence text = null;
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClip().getItemCount() > 0) {
                text = clipboardManager.getPrimaryClip().getItemAt(0).coerceToText(context);
            }
            if (text != null) {
                Intent intent = new Intent(context, SendClipboard.class);
                intent.putExtra(Intent.EXTRA_TEXT, text);

                Notification.Builder builder = new Notification.Builder(context);
                builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                builder.setContentTitle(context.getString(R.string.taptoshare));
                builder.setStyle(new Notification.BigTextStyle().bigText(text).setBigContentTitle(context.getString(R.string.taptoshare)));
                builder.setVibrate(new long[]{0});
                builder.setPriority(Notification.PRIORITY_MAX);
                builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
                PendingIntent disableintent = PendingIntent.getActivity(context, 0, new Intent(context, DisableClipboard.class), PendingIntent.FLAG_UPDATE_CURRENT);
                builder.addAction(0, context.getString(R.string.disableclipboard), disableintent);
                builder.setTicker(context.getString(R.string.taptoshare));
                builder.setSmallIcon(R.drawable.ic_comment_white_48dp);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    builder.setShowWhen(false);
                }
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(CLIPBOARD_NOTIFICATION, builder.build());
            }
        }
    };

    @Override
    public void onReceive(final Context ctx, Intent intent) {
        context = ctx;
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        if (context.getSharedPreferences(CLIP_LISTENER_ON, Context.MODE_PRIVATE).getBoolean(CLIP_LISTENER_ON, false)) {
            clipboardManager.addPrimaryClipChangedListener(clipChangedListener);
        } else {
            NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(CLIPBOARD_NOTIFICATION);
            clipboardManager.removePrimaryClipChangedListener(clipChangedListener);
        }
    }

}
