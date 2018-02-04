package forcellato.francesco.giocotris;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    private int LUNGHEZZA = 3;
    private Button[][] matrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
        findViewById(R.id.btnStart).setOnClickListener(view -> {
            start();
        });
    }

    public void start() {
        Tris t = new Tris();
        //Toast.makeText(getApplicationContext(), t.getGiocatore(), Toast.LENGTH_SHORT).show();
        t.addObserver(this);
        matrice = new Button[LUNGHEZZA][LUNGHEZZA];
        Resources r = getResources();
        String name = getPackageName();
        for (int i = 0; i < LUNGHEZZA; i++) {
            for (int j = 0; j < LUNGHEZZA; j++) {
                matrice[i][j] = findViewById(r.getIdentifier("btn" + i + "" + j, "id", name));
                int riga = i;
                int colonna = j;
                matrice[i][j].setText("");
                matrice[i][j].setOnClickListener(view -> {
                    //Toast.makeText(getApplicationContext(), riga + " " + colonna, Toast.LENGTH_SHORT).show();
                    t.setMossa(riga, colonna);
                });
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Mossa m = (Mossa) arg;
        //Toast.makeText(getApplicationContext(), m.toString(), Toast.LENGTH_SHORT).show();
        if (!m.haVinto()) {
            //non ha vinto
            matrice[m.getRiga()][m.getColonna()].setText(m.getGiocatore() == giocatore.giocatore1 ? "X" : "O");
        } else {
            //ha vinto!!!
            matrice[m.getRiga()][m.getColonna()].setText(m.getGiocatore() == giocatore.giocatore1 ? "X" : "O");
            if (m.getGiocatore()==giocatore.giocatore1){
                Toast.makeText(getApplicationContext(), "Ha vinto il giocatore 1", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Ha vinto il giocatore 2", Toast.LENGTH_LONG).show();
            }
        }
    }
}
