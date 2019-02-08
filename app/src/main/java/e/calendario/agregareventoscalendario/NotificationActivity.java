package e.calendario.agregareventoscalendario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {
    private TextView textConsejo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        textConsejo = (TextView) findViewById(R.id.consejo_textView);
        String c = " ";
        try {
            Bundle b = getIntent().getBundleExtra("bundles");
            c = AlarmReceiver.getConsejo();
        } catch (Exception e){}
        textConsejo.setText(c);
    }
}
