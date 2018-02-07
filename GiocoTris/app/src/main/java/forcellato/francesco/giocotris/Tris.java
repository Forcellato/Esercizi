package forcellato.francesco.giocotris;

import java.util.Observable;

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
    private giocatore prec;
    private giocatore[][] matrix;
    private boolean vinto;
    private String simbolo1;
    private String simbolo2;
    private String nome1;
    private String nome2;
    private int p1;
    private int p2;

    public Tris(String nome1, String nome2) {
        matrix = new giocatore[DEFAULT][DEFAULT];
        this.nome1 = nome1;
        this.nome2 = nome2;
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
        simbolo1 = "X";
        simbolo2 = "O";
        prec = giocatore.vuoto;
        p1 = 0;
        p2 = 0;
    }

    public void reset() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = giocatore.vuoto;
            }
        }
        vinto = false;
        if (prec == g) {
            g = g == giocatore.giocatore1 ? giocatore.giocatore2 : giocatore.giocatore1;
            prec = g;
        } else {
            prec = g;
        }
        simbolo1 = g == giocatore.giocatore1 ? "X" : "O";
        simbolo2 = g == giocatore.giocatore2 ? "X" : "O";

        setChanged();
        notifyObservers(new Mossa(g == giocatore.giocatore1 ? nome1 : nome2, g == giocatore.giocatore2 ? nome1 : nome2, simbolo1, simbolo2, p1, p2));
    }

    public void setMossa(int riga, int colonna) {
        if (!vinto && matrix[riga][colonna] == giocatore.vuoto) {
            matrix[riga][colonna] = g;
            vinto = vinto();
            if (vinto) {
                p1 = g == giocatore.giocatore1 ? p1+1 : p1;
                p2 = g == giocatore.giocatore2 ? p2+1 : p2;
            }
            setChanged();
            notifyObservers(new Mossa(riga, colonna, g == giocatore.giocatore1 ? nome1 : nome2, vinto, g == giocatore.giocatore1 ? simbolo1 : simbolo2, p1, p2));
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
                    if (matrix[(DEFAULT - 1) - i][i] != h) {
                        uguale = false;
                    }
                }
                ris = uguale;
            }
        }
        return ris;
    }
}

class Mossa {
    private int riga, colonna;
    private boolean vinto;
    private String g;
    private String g2;
    private String simbolo;
    private String simbolo2;
    private int p1, p2;

    public Mossa(int riga, int colonna, String g, boolean vinto, String simbolo, int p1, int p2) {
        this.riga = riga;
        this.colonna = colonna;
        this.g = g;
        this.g2 = null;
        this.vinto = vinto;
        this.simbolo = simbolo;
        this.simbolo2 = null;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Mossa(String g1, String g2, String simbolo1, String simbolo2, int p1, int p2) {
        vinto = false;
        this.g = g1;
        this.g2 = g2;
        this.simbolo = simbolo1;
        this.simbolo2 = simbolo2;
        this.p1 = p1;
        this.p2 = p2;
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

    public String getSimbolo() {
        return simbolo;
    }

    public String getSimbolo2() {
        return simbolo2;
    }

    public String getGiocatore() {
        return g;
    }

    public String getGiocatore2() {
        return g2;
    }

    public int getP1() {
        return p1;
    }

    public int getP2() {
        return p2;
    }

    @Override
    public String toString() {
        return "[" + riga + ", " + colonna + "; " + g + " " + (vinto ? "ha vinto" : "non ha vinto") + "]";
    }
}
