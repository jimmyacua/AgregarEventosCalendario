package e.calendario.agregareventoscalendario;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private static final int SOLICITUD_PERMISO_ESCRITURA = 1;
    private static final int SOLICITUD_PERMISO_LECTURA = 2;
    private Intent intentCalendario;
    private Calendar cal;
    private final static String CHANNEL_ID = "Notificación";
    private final static int NOTIFICATION_ID = 0;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boton = (Button) findViewById(R.id.button);
        final Activity activity = (Activity) this;
        cal = Calendar.getInstance();

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, SOLICITUD_PERMISO_ESCRITURA);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, SOLICITUD_PERMISO_LECTURA);
        }

        intentCalendario = new Intent(Intent.CATEGORY_APP_CALENDAR);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addEventToCalendar(activity);
                setAlarm(getApplicationContext());
                createNotificationChannel();
                showNotification();
                setPendingIntent();

            }
        });


    }

    private void setPendingIntent() {
        Intent intent = new Intent(this, NotificationActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(NotificationActivity.class);
        taskStackBuilder.addNextIntent(intent);
        pendingIntent = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void addEventToCalendar(Activity activity) {
        agregarEventosLunes();
        agregarEventosMartes();
        agregarEventosMiercoles();
        agregarEventosViernes("2019/01/25");
        agregarEventosViernes("2019/02/22");
        agregarEventosViernes("2019/03/29");
        agregarEventosViernes("2019/04/26");
        agregarEventosViernes("2019/05/31");
        agregarEventosViernes("2019/06/28");
        agregarEventosViernes("2019/07/26");
        agregarEventosViernes("2019/08/30");
        agregarEventosViernes("2019/09/27");
        agregarEventosViernes("2019/10/25");
        agregarEventosViernes("2019/11/29");
        agregarEventosViernes("2019/12/27");
        Toast.makeText(getApplication(), "Se agregaron los eventos. La aplicación se cerrará.", Toast.LENGTH_LONG).show();
        try{
            Thread.sleep(1500);
        }catch (Exception e){}
        this.finish();
    }

    public void agregarEventosLunes(){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            cal.setTime(formatter.parse("2019/01/14 06:00"));
            String eventdate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
            Log.e("event date", eventdate);
            Cursor cur = this.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
            if (cur.moveToFirst()) {
                long calendarID = cur.getLong(cur.getColumnIndex(CalendarContract.Calendars._ID));
                ContentValues eventValues = new ContentValues();
                eventValues.put(CalendarContract.Events.CALENDAR_ID, calendarID);
                eventValues.put(CalendarContract.Events.TITLE, "Residuos Orgánicos");
                eventValues.put(CalendarContract.Events.DESCRIPTION, "Cáscaras, residuos de jardín, residuos de frutas y vegetales.");
                eventValues.put(CalendarContract.Events.ALL_DAY, false);
                eventValues.put(CalendarContract.Events.HAS_ALARM, true);
                eventValues.put(CalendarContract.Events.EVENT_COLOR, 0xFF008000);
                eventValues.put(CalendarContract.Events.RRULE, "FREQ=WEEKLY;BYDAY=MO;UNTIL=20191231");
                eventValues.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis());
                eventValues.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 360 * 60 * 1000);
                eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());
                Uri eventUri = this.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, eventValues);
                Long eventID = ContentUris.parseId(eventUri);
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void agregarEventosMartes(){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            cal.setTime(formatter.parse("2019/01/15 06:00"));
            String eventdate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
            Log.e("event date", eventdate);
            Cursor cur = this.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
            if (cur.moveToFirst()) {
                long calendarID = cur.getLong(cur.getColumnIndex(CalendarContract.Calendars._ID));
                ContentValues eventValues = new ContentValues();
                eventValues.put(CalendarContract.Events.CALENDAR_ID, calendarID);
                eventValues.put(CalendarContract.Events.TITLE, "Residuos Ordinarios");
                eventValues.put(CalendarContract.Events.DESCRIPTION, "Restos de carne, comidas, pañales, toallas sanitarias, papel higíenico, envolturas de embutidos, papel sucio o con grasa, ropa, zapatos, estereofón");
                eventValues.put(CalendarContract.Events.ALL_DAY, false);
                eventValues.put(CalendarContract.Events.HAS_ALARM, true);
                eventValues.put(CalendarContract.Events.EVENT_COLOR, 0x66CCFF);
                eventValues.put(CalendarContract.Events.RRULE, "FREQ=WEEKLY;BYDAY=TU;UNTIL=20191231");
                eventValues.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis());
                eventValues.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 360 * 60 * 1000);
                eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());
                Uri eventUri = this.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, eventValues);
                Long eventID = ContentUris.parseId(eventUri);
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void agregarEventosMiercoles(){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            cal.setTime(formatter.parse("2019/01/16 06:00"));
            String eventdate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
            Log.e("event date", eventdate);
            Cursor cur = this.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
            if (cur.moveToFirst()) {
                long calendarID = cur.getLong(cur.getColumnIndex(CalendarContract.Calendars._ID));
                ContentValues eventValues = new ContentValues();
                eventValues.put(CalendarContract.Events.CALENDAR_ID, calendarID);
                eventValues.put(CalendarContract.Events.TITLE, "Residuos Reciclables");
                eventValues.put(CalendarContract.Events.DESCRIPTION, "Bolsas, galones, pichingas, cajas de plástico, vidrio(no depositar vidrio de ventanas o bombillos), papel, cartón, tetrabrik, tetrapack* y latas* (lavados y secos*)");
                eventValues.put(CalendarContract.Events.ALL_DAY, false);
                eventValues.put(CalendarContract.Events.HAS_ALARM, true);
                eventValues.put(CalendarContract.Events.EVENT_COLOR, 0x0099FF);
                eventValues.put(CalendarContract.Events.RRULE, "FREQ=WEEKLY;BYDAY=WE;UNTIL=20191231");
                eventValues.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis());
                eventValues.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 360 * 60 * 1000);
                eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());
                Uri eventUri = this.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, eventValues);
                Long eventID = ContentUris.parseId(eventUri);
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void agregarEventosViernes(String fecha){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            cal.setTime(formatter.parse(fecha + " 05:00"));
            String eventdate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
            Log.e("event date", eventdate);
            Cursor cur = this.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
            if (cur.moveToFirst()) {
                long calendarID = cur.getLong(cur.getColumnIndex(CalendarContract.Calendars._ID));
                ContentValues eventValues = new ContentValues();
                eventValues.put(CalendarContract.Events.CALENDAR_ID, calendarID);
                eventValues.put(CalendarContract.Events.TITLE, "Residuos No Tradicionales");
                eventValues.put(CalendarContract.Events.DESCRIPTION, "chatarra, electrodomésticos, madera, hierro, aceite quemado, escombros en sacos y madera");
                eventValues.put(CalendarContract.Events.ALL_DAY, false);
                eventValues.put(CalendarContract.Events.HAS_ALARM, true);
                eventValues.put(CalendarContract.Events.EVENT_COLOR, 0xFF0000);
                eventValues.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis());
                eventValues.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 360 * 60 * 1000);
                eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());
                Uri eventUri = this.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, eventValues);
                Long eventID = ContentUris.parseId(eventUri);
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificación";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void showNotification() {
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_new_releases_black_24dp);
        builder.setContentTitle("Consejo del día");
        builder.setContentText("TEXTO");
        builder.setColor(Color.GREEN);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Notification.DEFAULT_LIGHTS, 1000, 1000);
        builder.setVibrate(new long[]{Notification.DEFAULT_VIBRATE});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());*/
    }

    public void setAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        boolean alarm = (PendingIntent.getBroadcast(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_NO_CREATE) != null);

        if(!alarm){
            Log.i("TAG", "crear una alarma");
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+8640000, 1000, pendingIntent);
        } else {
            Log.i("TAG", "Alarma activa");
        }
        MyReceiver myReceiver = new MyReceiver();
        Calendar cNot = Calendar.getInstance();
        cNot.set(Calendar.HOUR_OF_DAY, 14);
        cNot.set(Calendar.MINUTE, 25);
        myReceiver.creaNotificacion(cNot.getTimeInMillis(), getApplicationContext());
    }

}
