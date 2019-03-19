package e.calendario.agregareventoscalendario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        final String[] consejos = alarmReceiver.getArrayConsejos();
        final ListView listView = (ListView) findViewById(R.id.lista);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, consejos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String texto = consejos[position];
                DialogWindow dialogWindow = new DialogWindow();
                dialogWindow.setConsejo(texto);
                dialogWindow.show(getSupportFragmentManager(), "Consejo");
            }
        });
    }
}
