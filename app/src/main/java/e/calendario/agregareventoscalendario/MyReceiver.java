package e.calendario.agregareventoscalendario;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 20);
        cal.set(Calendar.MINUTE, 06);
        cal.set(Calendar.SECOND, 0);
        creaNotificacion(cal.getTimeInMillis(), context);
    }

    public void creaNotificacion(long currentTimeMillis, Context context) {
        try {
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_new_releases_black_24dp);
            Intent notifIntent = new Intent(context, NotificationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setWhen(currentTimeMillis);
            builder.setSmallIcon(R.drawable.ic_new_releases_black_24dp);
            builder.setContentTitle("Consejo del día");
            builder.setContentText("TEXTO");
            builder.setColor(Color.GREEN);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setLights(Notification.DEFAULT_LIGHTS, 1000, 1000);
            builder.setVibrate(new long[]{Notification.DEFAULT_VIBRATE});
            builder.setDefaults(Notification.DEFAULT_SOUND);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            notificationManager.notify((int) currentTimeMillis, builder.build());
        }catch (Exception e){}
    }
}
