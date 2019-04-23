package e.calendario.agregareventoscalendario;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ConsejoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejo);
        regresar();
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("Consejo del día");
        }

        TextView consejoTV = findViewById(R.id.consejoDiarioTextView);
        SharedPreferences preferences = getSharedPreferences("consejo", MODE_PRIVATE);
        final String consejo = preferences.getString("consejo", null); //0 es por defecto
        consejoTV.setText(consejo);
    }

    public void regresar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
