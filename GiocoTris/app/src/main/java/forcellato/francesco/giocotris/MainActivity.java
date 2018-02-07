package forcellato.francesco.giocotris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int GIOCO_ACTIVITY = 2;
    private EditText g1;
    private EditText g2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btnAct);
        g1 = findViewById(R.id.giocatore1);
        g2 = findViewById(R.id.giocatore2);
        btn.setOnClickListener((view) -> {
            if (g1.getText() != null && g2.getText() != null && g1.getText().toString().compareTo("") != 0 && g1.getText().toString().compareTo("") != 0) {
                if (g1.length() <= 10 && g2.length() <= 10) {
                    Intent i = new Intent(this, GiocoActivity.class);
                    i.putExtra("g1", g1.getText().toString());
                    i.putExtra("g2", g2.getText().toString());
                    startActivityForResult(i, GIOCO_ACTIVITY);
                }else{
                    Toast.makeText(getApplicationContext(), "Nomi dei giocatori troppo lunghi", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Inserire i nomi dei giocatori", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        if (requestCode == GIOCO_ACTIVITY) {
            g1.setText("");
            g2.setText("");
        }
    }
}
