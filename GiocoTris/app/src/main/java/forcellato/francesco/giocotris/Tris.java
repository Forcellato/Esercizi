package forcellato.francesco.giocotris;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Francesco Forcellato on 03/02/18.
 */

enum giocatore {
    giocatore1,
    giocatore2,
    vuoto
};

public class Tris extends Observable {
    private final int DEFAULT = 3;
    private giocatore g;
    private giocatore[][] matrix;
    private boolean vinto;

    public Tris() {
        matrix = new giocatore[DEFAULT][DEFAULT];
        inizializza();
    }

    private void inizializza() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = giocatore.vuoto;
            }
        }
        g = giocatore.giocatore1;
        vinto = false;
    }

    public void setMossa(int riga, int colonna) {
        if (!vinto && matrix[riga][colonna]==giocatore.vuoto) {
            matrix[riga][colonna] = g;
            vinto = vinto();
            setChanged();
            notifyObservers(new Mossa(riga, colonna, g, vinto)); // bisogna modificarlo a seconda se ha effettivamente vinto o no

            if (g == giocatore.giocatore1) {
                g = giocatore.giocatore2;
            } else {
                g = giocatore.giocatore1;
            }
        }
    }

    private boolean vinto() {
        boolean ris = false;
        boolean uguale = true;
        giocatore h;
        //Per riga
        for (int i = 0; i < DEFAULT && !ris; i++) {
            h = matrix[i][0];
            if (h != giocatore.vuoto) {
                uguale = true;
                for (int j = 0; j < DEFAULT && uguale; j++) {
                    if (matrix[i][j] != h) {
                        uguale = false;
                    }
                }
                ris = uguale;
            }
        }

        if (!ris) {
            //per colonna
            for (int i = 0; i < DEFAULT && !ris; i++) {
                h = matrix[0][i];
                if (h != giocatore.vuoto) {
                    uguale = true;
                    for (int j = 0; j < DEFAULT && uguale; j++) {
                        if (matrix[j][i] != h) {
                            uguale = false;
                        }
                    }
                    ris = uguale;
                }
            }
        }
        if (!ris) {
            //diagonale1
            h = matrix[0][0];
            if (h != giocatore.vuoto) {
                uguale = true;
                for (int i = 0; i < DEFAULT && uguale; i++) {
                    if (matrix[i][i] != h) {
                        uguale = false;
                    }
                }
                ris = uguale;
            }
        }
        if (!ris) {
            //diagonale2
            h = matrix[0][DEFAULT - 1];
            if (h != giocatore.vuoto) {
                uguale = true;
                for (int i = DEFAULT - 1; i >= 0 && uguale; i--) {
                    if (matrix[(DEFAULT-1)-i][i] != h) {
                        uguale = false;
                    }
                }
                ris = uguale;
            }
        }
        return ris;
    }

    public String getGiocatore() {
        return g.name();
    }
}

class Mossa {
    private int riga, colonna;
    private boolean vinto;
    private giocatore g;

    public Mossa(int riga, int colonna, giocatore g, boolean vinto) {
        this.riga = riga;
        this.colonna = colonna;
        this.g = g;
        this.vinto = vinto;
    }

    public int getRiga() {
        return riga;
    }

    public int getColonna() {
        return colonna;
    }

    public boolean haVinto() {
        return vinto;
    }

    public giocatore getGiocatore() {
        return g;
    }

    @Override
    public String toString() {
        return "[" + riga + ", " + colonna + "; " + g.name() + " " + (vinto ? "ha vinto" : "non ha vinto") + "]";
    }
}
