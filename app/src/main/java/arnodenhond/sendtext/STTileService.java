package arnodenhond.sendtext;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

/**
 * Created by arnodenhond on 19/07/16.
 */
public class STTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();

        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, SendText.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent speakintent = PendingIntent.getActivity(this, 0, new Intent(this, SpeakText.class), PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput input = new RemoteInput.Builder(getString(R.string.app_name)).build();
        Notification.Action pasteAction = new Notification.Action.Builder(null, getString(R.string.paste), intent).build();
        Notification.Action speakAction = new Notification.Action.Builder(null, getString(R.string.speak), speakintent).build();
        Notification.Action inputAction = new Notification.Action.Builder(null, getString(R.string.type), intent).addRemoteInput(input).build();
        Notification.Builder builder = new Notification.Builder(this);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setSmallIcon(R.drawable.ic_insert_comment_black_48dp);
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        builder.setCategory(Notification.CATEGORY_MESSAGE);
        builder.setVibrate(new long[] {0});
        builder.setActions(pasteAction, speakAction, inputAction);
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
        getQsTile().setState(Tile.STATE_INACTIVE);
        getQsTile().updateTile();
    }

}
