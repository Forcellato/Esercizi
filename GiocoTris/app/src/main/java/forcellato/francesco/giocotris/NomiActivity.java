package forcellato.francesco.giocotris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NomiActivity extends AppCompatActivity {

    private static final int GIOCO_ACTIVITY = 3;
    private EditText g1;
    private EditText g2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean singolo = getIntent().getBooleanExtra("singolo", false);
        setContentView(singolo ? R.layout.activity_nomi_singolo : R.layout.activity_nomi);

        g1 = findViewById(R.id.giocatore1);
        Button btn = findViewById(R.id.btnAct);
        if (!singolo) {
            g2 = findViewById(R.id.giocatore2);
            btn.setOnClickListener((view) -> {
                if (g1.getText() != null && g2.getText() != null && g1.getText().toString().compareTo("") != 0 && g1.getText().toString().compareTo("") != 0) {
                    if (g1.length() <= 10 && g2.length() <= 10) {
                        Intent i = new Intent(this, GiocoActivity.class);
                        i.putExtra("g1", g1.getText().toString());
                        i.putExtra("g2", g2.getText().toString());
                        i.putExtra("singolo", false);
                        startActivityForResult(i, GIOCO_ACTIVITY);
                    } else {
                        Toast.makeText(getApplicationContext(), "Nomi dei giocatori troppo lunghi", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Inserire i nomi dei giocatori", Toast.LENGTH_LONG).show();
                }
            });
        } else {

            btn.setOnClickListener(view -> {
                if (g1.getText() != null && g1.getText().toString().compareTo("") != 0) {
                    if (g1.length() <= 10) {
                        Intent i = new Intent(this, GiocoActivity.class);
                        i.putExtra("g1", g1.getText().toString());
                        i.putExtra("g2", "AI");
                        i.putExtra("singolo", true);
                        startActivityForResult(i, GIOCO_ACTIVITY);
                    } else {
                        Toast.makeText(getApplicationContext(), "Nome del giocatore troppo lungo", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Inserire il nome del giocatore", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        if (requestCode == GIOCO_ACTIVITY) {
            g1.setText("");
            if (g2 != null) {
                g2.setText("");
            }
        }
    }
}
