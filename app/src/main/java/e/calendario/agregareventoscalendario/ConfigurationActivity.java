package e.calendario.agregareventoscalendario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.nio.charset.Charset;


public class ConfigurationActivity extends AppCompatActivity {

    public Switch switchNotif, switchSonido;
    private Spinner spinnerHoras, spinnerMinutos;
    private DB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("Configuración");
        }
        switchNotif = findViewById(R.id.switchNotif);
        switchSonido = findViewById(R.id.switchSonido);

        spinnerHoras = (Spinner) findViewById(R.id.spinnerHora);
        spinnerMinutos = findViewById(R.id.spinnerMinutos);

        database = new DB(this);


        SharedPreferences sharedPrefs = getSharedPreferences("configuracion_notificacion", MODE_PRIVATE);
        switchNotif.setChecked(sharedPrefs.getBoolean("conf_notificacion", true));

        SharedPreferences sharedPrefSonido = getSharedPreferences("conf_sonido", MODE_PRIVATE);
        switchSonido.setChecked(sharedPrefSonido.getBoolean("conf_sonido", true));

        boolean allowNot = sharedPrefs.getBoolean("conf_notificacion", true);
        if(!allowNot){
            switchSonido.setEnabled(false);
        }

        switchNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchNotif.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("configuracion_notificacion", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.putBoolean("conf_notificacion", true);
                    editor.commit();
                    switchSonido.setEnabled(true);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("configuracion_notificacion", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.putBoolean("conf_notificacion", false);
                    editor.commit();
                    switchSonido.setEnabled(false);
                }
            }
        });

        switchSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchSonido.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("conf_sonido", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.putBoolean("conf_sonido", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("conf_sonido", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.putBoolean("conf_sonido", false);
                    editor.commit();
                }
            }
        });


        fillSpinners();
        spinnerHoras.setSelection(getSpinnerHoras());
        spinnerMinutos.setSelection(getSpinnerMinutos());

        spinnerHoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSpinnerHoras(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setSpinnerHoras(9);
            }
        });

        spinnerMinutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSpinnerMinutos(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setSpinnerMinutos(0);
            }
        });
    }

    public void fillSpinners(){
        String[] horas = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
        spinnerHoras.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, horas));
        spinnerHoras.setSelection(9);

        String[] minutos = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23",
                "24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47",
                "48","49","50","51","52","53","54","55","56","57","58","59"};
        spinnerMinutos.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minutos));
    }

    public void setSpinnerHoras(int pos){
        SharedPreferences.Editor editor = getSharedPreferences("hora", MODE_PRIVATE).edit();
        editor.clear();
        editor.putInt("hora", pos);
        editor.commit();
        spinnerHoras.setSelection(pos);
        database.setHora(pos);
    }

    public void setSpinnerMinutos(int pos){
        SharedPreferences.Editor editor = getSharedPreferences("minutos", MODE_PRIVATE).edit();
        editor.clear();
        editor.putInt("minutos", pos);
        editor.commit();
        spinnerMinutos.setSelection(pos);
        database.setMinuto(pos);
    }

    public int getSpinnerHoras(){
        SharedPreferences sharedPreferences = getSharedPreferences("hora", MODE_PRIVATE);
        if(sharedPreferences.getInt("hora", -1) == -1){
            return 9;
        } else {
            return sharedPreferences.getInt("hora", -1);
        }
    }

    public int getSpinnerMinutos(){
        SharedPreferences sharedPreferences = getSharedPreferences("minutos", MODE_PRIVATE);
        if(sharedPreferences.getInt("minutos", -1) == -1){
            return 0;
        } else {
            return sharedPreferences.getInt("minutos", -1);
        }
    }

}
