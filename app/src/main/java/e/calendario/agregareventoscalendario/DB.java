package e.calendario.agregareventoscalendario;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.sql.Types.VARCHAR;

public class DB extends SQLiteOpenHelper {

    public static final String TAG = "DB";
    public static final String TABLE_NAME = "horaNotificaciones";
    public static final String COL1 = "hora";
    public static final String COL2 = "minutos";

    public DB(Context context) {
        super(context, TABLE_NAME, null,1 );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("+ COL1 +" int, "+ COL2 +" int);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void setHora(int hora){
        try (SQLiteDatabase db = this.getWritableDatabase()) {

            db.execSQL("UPDATE " + TABLE_NAME + " set " + COL1 + "= " + hora);
        }
    }

    public void setMinuto(int minuto){
        try (SQLiteDatabase db = this.getWritableDatabase()) {

            db.execSQL("UPDATE " + TABLE_NAME + " set " + COL2 + "= " + minuto);
        }

    }
}
