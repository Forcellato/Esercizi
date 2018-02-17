package forcellato.francesco.dado_fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnInizio).setOnClickListener(view->{
            Intent i = new Intent(this, DadoActivity.class);
            startActivity(i);
        });
    }
}
