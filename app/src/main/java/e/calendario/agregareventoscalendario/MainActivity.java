package e.calendario.agregareventoscalendario;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
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

        final Activity activity = this;
        final Button boton = (Button) findViewById(R.id.button);
        cal = Calendar.getInstance();
        final Button botonConsejos = (Button) findViewById(R.id.button_consejos);

        final TextView texto = (TextView) findViewById(R.id.texto);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, SOLICITUD_PERMISO_ESCRITURA);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, SOLICITUD_PERMISO_LECTURA);
        }

        intentCalendario = new Intent(Intent.CATEGORY_APP_CALENDAR);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
		/*if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
		    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CALENDAR}, SOLICITUD_PERMISO_ESCRITURA);
		    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CALENDAR}, SOLICITUD_PERMISO_LECTURA);
            Toast.makeText(getApplicationContext(), "ENTRA A IF SIN PERMISOS", Toast.LENGTH_LONG);
		}*/
                int permisoEscritura = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CALENDAR);
                if(permisoEscritura == -1){
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CALENDAR}, SOLICITUD_PERMISO_ESCRITURA);
                }

                int permisoLectura = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALENDAR);
                if(permisoLectura == -1){
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CALENDAR}, SOLICITUD_PERMISO_LECTURA);
                }


                addEventToCalendar(activity);
                boton.setEnabled(false);
                texto.setText("Los eventos ya han sido agregados a su calendario.");
            }
        });

        botonConsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });


        createNotificationChannel();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


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
        Toast.makeText(getApplication(), "Se agregaron los eventos. Puede revisarlos en su calendario.", Toast.LENGTH_LONG).show();
    }

    public void agregarEventosLunes() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            cal.setTime(formatter.parse("2019/01/14 06:00"));
            String eventdate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
            Log.e("event date", eventdate);
            Cursor cur = this.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
            if (cur.moveToFirst()) {
                long calendarID = cur.getLong(cur.getColumnIndex(CalendarContract.Calendars._ID));
                ContentValues eventValues = new ContentValues();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    eventValues.put(CalendarContract.Events.CALENDAR_ID, 3);
                } else{
                    eventValues.put(CalendarContract.Events.CALENDAR_ID, calendarID);
                }
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

                int eventID = (int) ContentUris.parseId(eventUri);

                ContentValues remainders = new ContentValues();
                remainders.put(CalendarContract.Reminders.EVENT_ID, eventID);
                remainders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                remainders.put(CalendarContract.Reminders.MINUTES, 10);

                Uri uri2 = this.getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, remainders);
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarEventosMartes() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            cal.setTime(formatter.parse("2019/01/15 06:00"));
            String eventdate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
            Log.e("event date", eventdate);
            Cursor cur = this.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
            if (cur.moveToFirst()) {
                long calendarID = cur.getLong(cur.getColumnIndex(CalendarContract.Calendars._ID));
                ContentValues eventValues = new ContentValues();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    eventValues.put(CalendarContract.Events.CALENDAR_ID, 3);
                } else{
                    eventValues.put(CalendarContract.Events.CALENDAR_ID, calendarID);
                }
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

                ContentValues remainders = new ContentValues();
                remainders.put(CalendarContract.Reminders.EVENT_ID, eventID);
                remainders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                remainders.put(CalendarContract.Reminders.MINUTES, 10);

                Uri uri2 = this.getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, remainders);
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarEventosMiercoles() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            cal.setTime(formatter.parse("2019/01/16 06:00"));
            String eventdate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
            Log.e("event date", eventdate);
            Cursor cur = this.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
            if (cur.moveToFirst()) {
                long calendarID = cur.getLong(cur.getColumnIndex(CalendarContract.Calendars._ID));
                ContentValues eventValues = new ContentValues();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    eventValues.put(CalendarContract.Events.CALENDAR_ID, 3);
                } else{
                    eventValues.put(CalendarContract.Events.CALENDAR_ID, calendarID);
                }
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

                ContentValues remainders = new ContentValues();
                remainders.put(CalendarContract.Reminders.EVENT_ID, eventID);
                remainders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                remainders.put(CalendarContract.Reminders.MINUTES, 10);

                Uri uri2 = this.getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, remainders);
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarEventosViernes(String fecha) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            cal.setTime(formatter.parse(fecha + " 06:00"));
            String eventdate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
            Log.e("event date", eventdate);
            Cursor cur = this.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
            if (cur.moveToFirst()) {
                long calendarID = cur.getLong(cur.getColumnIndex(CalendarContract.Calendars._ID));
                ContentValues eventValues = new ContentValues();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    eventValues.put(CalendarContract.Events.CALENDAR_ID, 3);
                } else{
                    eventValues.put(CalendarContract.Events.CALENDAR_ID, calendarID);
                }
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

                ContentValues remainders = new ContentValues();
                remainders.put(CalendarContract.Reminders.EVENT_ID, eventID);
                remainders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                remainders.put(CalendarContract.Reminders.MINUTES, 10);

                Uri uri2 = this.getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, remainders);
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
