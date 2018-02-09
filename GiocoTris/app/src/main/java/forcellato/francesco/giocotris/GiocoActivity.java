package forcellato.francesco.giocotris;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class GiocoActivity extends AppCompatActivity implements Observer {
    private int LUNGHEZZA = 3;
    private Button[][] matrice;
    private TextView txtG1;
    private TextView txtG2;
    private TextView txtP1;
    private TextView txtP2;
    private Tris t;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);
        String g1 = (getIntent().getStringExtra("g1"));
        String g2 = (getIntent().getStringExtra("g2"));
        txtG1 = findViewById(R.id.txtG1);
        txtG2 = findViewById(R.id.txtG2);
        txtP1 = findViewById(R.id.txtP1);
        txtP2 = findViewById(R.id.txtP2);
        txtP1.setText(0 + "");
        txtP2.setText(0 + "");
        txtG1.setText(g1);
        txtG2.setText(g2);
        t = new Tris(g1, g2, getIntent().getBooleanExtra("singolo", false));
        t.addObserver(this);
        play();
        findViewById(R.id.btnStart).setOnClickListener(view -> {
            play();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void play() {
        matrice = new Button[LUNGHEZZA][LUNGHEZZA];
        Resources r = getResources();
        String name = getPackageName();
        for (int i = 0; i < LUNGHEZZA; i++) {
            for (int j = 0; j < LUNGHEZZA; j++) {
                matrice[i][j] = findViewById(r.getIdentifier("btn" + i + "" + j, "id", name));
                Context c = matrice[0][0].getContext();
                int riga = i;
                int colonna = j;
                matrice[i][j].setText("");
                matrice[i][j].setBackgroundTintList(c.getResources().getColorStateList(R.color.bluscuro));
                matrice[i][j].setOnClickListener(view -> {
                    t.setMossa(riga, colonna);
                });
            }
        }
        t.reset();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void update(Observable o, Object arg) {
        Mossa m = (Mossa) arg;
        if (m.getSimbolo2() == null) {
            matrice[m.getRiga()][m.getColonna()].setText(m.getSimbolo());
            txtG1.setTextColor(txtG1.getText().toString().contains(m.getGiocatore()) ? getResources().getColor(R.color.nero) : getResources().getColor(R.color.bluscuro));
            txtG2.setTextColor(txtG2.getText().toString().contains(m.getGiocatore()) ? getResources().getColor(R.color.nero) : getResources().getColor(R.color.bluscuro));
        } else {
            txtG1.setText(txtG1.getText().toString().contains(m.getGiocatore()) ? m.getGiocatore() + ": " + m.getSimbolo() : m.getGiocatore2() + ": " + m.getSimbolo());
            txtG2.setText(txtG2.getText().toString().contains(m.getGiocatore()) ? m.getGiocatore() + ": " + m.getSimbolo2() : m.getGiocatore2() + ": " + m.getSimbolo2());
            txtG1.setTextColor(txtG1.getText().toString().contains(m.getGiocatore()) ? getResources().getColor(R.color.bluscuro) : getResources().getColor(R.color.nero));
            txtG2.setTextColor(txtG2.getText().toString().contains(m.getGiocatore()) ? getResources().getColor(R.color.bluscuro) : getResources().getColor(R.color.nero));
        }
        if (m.haVinto()) {
            Context c = matrice[0][0].getContext();
            int[] h = m.getVincente();
            for (int i = 1; i < h.length; i = i + 2) {
                matrice[h[i - 1]][h[i]].setBackgroundTintList(c.getResources().getColorStateList(R.color.bluchiaro));
            }
            txtP1.setText(m.getP1() + "");
            txtP2.setText(m.getP2() + "");
        }
    }
}
