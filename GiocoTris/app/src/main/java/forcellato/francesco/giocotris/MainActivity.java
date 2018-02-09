package forcellato.francesco.giocotris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnS = findViewById(R.id.btnSingolo);
        Button btnM = findViewById(R.id.btnMulti);
        btnS.setOnClickListener(view -> {
            Intent i = new Intent(this, NomiActivity.class);
            i.putExtra("singolo", true);
            startActivity(i);
        });
        btnM.setOnClickListener(view -> {
            Intent i = new Intent(this, NomiActivity.class);
            i.putExtra("singolo", false);
            startActivity(i);
        });
    }
}
