package arnodenhond.sendtext;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import java.util.ArrayList;

/**
 * Created by arnodenhond on 19/07/16.
 */
public class STTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();

        PendingIntent intent = PendingIntent.getActivity(this,0,new Intent(this,SendText.class),PendingIntent.FLAG_UPDATE_CURRENT);

        String appname = getString(R.string.app_name);
        RemoteInput input = new RemoteInput.Builder(appname).build();
        Notification.Action pasteAction = new Notification.Action.Builder(null,"paste",intent).build();
        Notification.Action inputAction = new Notification.Action.Builder(null,"type",intent).addRemoteInput(input).build();
        Notification.Builder builder = new Notification.Builder(this);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setSmallIcon(R.drawable.ic_insert_comment_black_48dp);
        builder.setVisibility(Notification.VISIBILITY_SECRET);
        builder.setActions(pasteAction, inputAction);
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
        getQsTile().setState(Tile.STATE_INACTIVE);
        getQsTile().updateTile();
    }
}
