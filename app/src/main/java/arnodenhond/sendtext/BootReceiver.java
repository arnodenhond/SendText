package arnodenhond.sendtext;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class BootReceiver extends BroadcastReceiver {

    public static String CLIP_LISTENER_ON = "cliplisteneron";
    private static Context context;

    @Override
    public void onReceive(final Context ctx, Intent intent) {
        context = ctx;
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        if (context.getSharedPreferences(CLIP_LISTENER_ON, Context.MODE_PRIVATE).getBoolean(CLIP_LISTENER_ON, false)) {
            clipboardManager.addPrimaryClipChangedListener(clipChangedListener);
        } else {
            clipboardManager.removePrimaryClipChangedListener(clipChangedListener);
        }
    }

    static ClipboardManager.OnPrimaryClipChangedListener clipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            CharSequence text = null;
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClip().getItemCount() > 0) {
                text = clipboardManager.getPrimaryClip().getItemAt(0).coerceToText(context);
            }
            if (text != null) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent intent = PendingIntent.getActivity(context, 0, new Intent(context, SendText.class), PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Builder builder = new Notification.Builder(context);
                builder.setPriority(Notification.PRIORITY_MAX);
                PendingIntent disableintent = PendingIntent.getActivity(context, 0, new Intent(context, DisableClipboard.class), PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentTitle(context.getString(R.string.taptoshare));
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    builder.addAction(0,context.getString(R.string.disableclipboard),disableintent);
                    builder.setTicker(context.getString(R.string.taptoshare));

                    builder.setSmallIcon(R.drawable.ic_comment_white_48dp);
                } else {
                    Notification.Action action = new Notification.Action.Builder(null, context.getString(R.string.disableclipboard), disableintent).build();
                    builder.setActions(action);
                    builder.setSmallIcon(R.drawable.ic_insert_comment_black_48dp);
                }
                builder.setStyle(new Notification.BigTextStyle().bigText(text));

                builder.setAutoCancel(true);
                builder.setVibrate(new long[]{0});
                builder.setContentIntent(intent);
                Notification notification = builder.build();

                notificationManager.notify(0, notification);
            }
        }
    };

}
