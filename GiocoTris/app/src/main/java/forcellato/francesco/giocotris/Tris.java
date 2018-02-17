package forcellato.francesco.giocotris;

import java.util.Observable;
import java.util.Random;

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
    private boolean singolo;
    private int[] vincente;
    private int mosse;

    public Tris(String nome1, String nome2, boolean singolo) {
        matrix = new giocatore[DEFAULT][DEFAULT];
        this.nome1 = nome1;
        this.nome2 = nome2;
        this.singolo = singolo;
        vincente = new int[DEFAULT * 2];
        mosse = 0;
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
        mosse = 0;
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
        if (singolo && g == giocatore.giocatore2) {
            giocaMossa();
        }
    }

    public void setMossa(int riga, int colonna) {
        if (!vinto && matrix[riga][colonna] == giocatore.vuoto) {
            matrix[riga][colonna] = g;
            mosse++;
            setVittoria(riga, colonna);
            if (!vinto && singolo) {
                giocaMossa();
            }
        }
    }

    private int[] getRigaColonna(giocatore b){
        int[] ris = new int[2];
        ris[0] = -1;
        ris[1] = -1;
        int h = 0;
        //per riga
        for (int i = 0; i < DEFAULT && ris[0] == -1; i++) {
            h = 0;
            for (int j = 0; j < DEFAULT && ris[0] == -1; j++) {
                if (matrix[i][j] == b) {
                    h++;
                }
            }
            if (h == 2) {
                for (int j = 0; j < DEFAULT && ris[0] == -1; j++) {
                    if (matrix[i][j] == giocatore.vuoto) {
                        ris[0] = i;
                        ris[1] = j;
                    }
                }
            }
        }
        //per colonna
        for (int i = 0; i < DEFAULT && ris[0] == -1; i++) {
            h = 0;
            for (int j = 0; j < DEFAULT && ris[0] == -1; j++) {
                if (matrix[j][i] == b) {
                    h++;
                }
            }
            if (h == 2) {
                for (int j = 0; j < DEFAULT; j++) {
                    if (matrix[j][i] == giocatore.vuoto) {
                        ris[0] = j;
                        ris[1] = i;
                    }
                }
            }
        }
        //per diagonale 1
        h = 0;
        for (int i = 0; i < DEFAULT && ris[0] == -1; i++) {
            if (matrix[i][i] == b) {
                h++;
            }
        }
        if (h == 2) {
            for (int i = 0; i < DEFAULT && ris[0] == -1; i++) {
                if (matrix[i][i] == giocatore.vuoto) {
                    ris[0] = i;
                    ris[1] = i;
                }
            }
        }
        //per diagonale 2
        h = 0;
        for (int i = DEFAULT - 1; i >= 0 && ris[0] == -1; i--) {
            if (matrix[(DEFAULT - 1) - i][i] == b) {
                h++;
            }
        }
        if (h == 2) {
            for (int i = DEFAULT - 1; i >= 0 && ris[0] == -1; i--) {
                if (matrix[(DEFAULT - 1) - i][i] == giocatore.vuoto) {
                    ris[0] = (DEFAULT - 1) - i;
                    ris[1] = i;
                }
            }
        }
        return ris;
    }

    private void giocaMossa() {
        if (mosse < 9) {
            int riga = -1, colonna = -1;
            giocatore avversario = g == giocatore.giocatore1 ? giocatore.giocatore2 : giocatore.giocatore1;
            //per riga
            int[] h= getRigaColonna(g);
            riga=h[0];
            colonna=h[1];
            if(riga == -1){
                h= getRigaColonna(avversario);
                riga=h[0];
                colonna=h[1];
            }
            if (riga == -1) {

                Random r = new Random();
                do {
                    riga = r.nextInt(3);
                    colonna = r.nextInt(3);
                } while (matrix[riga][colonna] != giocatore.vuoto);
            }
            matrix[riga][colonna] = g;
            mosse++;
            setVittoria(riga, colonna);
        }
    }

    private void setVittoria(int riga, int colonna) {
        vinto = vinto();
        if (vinto) {
            p1 = g == giocatore.giocatore1 ? p1 + 1 : p1;
            p2 = g == giocatore.giocatore2 ? p2 + 1 : p2;
        }
        setChanged();
        notifyObservers(new Mossa(riga, colonna, g == giocatore.giocatore1 ? nome1 : nome2, vinto, g == giocatore.giocatore1 ? simbolo1 : simbolo2, p1, p2, vincente));
        if (g == giocatore.giocatore1) {
            g = giocatore.giocatore2;
        } else {
            g = giocatore.giocatore1;
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
                if (ris) {
                    vincente = new int[]{i, 0, i, 1, i, 2};
                }
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
                    if (ris) {
                        vincente = new int[]{0, i, 1, i, 2, i};
                    }
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
                if (ris) {
                    vincente = new int[]{0, 0, 1, 1, 2, 2};
                }
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
                if (ris) {
                    vincente = new int[]{0, 2, 1, 1, 2, 0};
                }
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
    private int[] vincente;

    public Mossa(int riga, int colonna, String g, boolean vinto, String simbolo, int p1, int p2, int[] vincente) {
        this.riga = riga;
        this.colonna = colonna;
        this.g = g;
        this.g2 = null;
        this.vinto = vinto;
        this.simbolo = simbolo;
        this.simbolo2 = null;
        this.p1 = p1;
        this.p2 = p2;
        this.vincente = vincente;
    }

    public Mossa(String g1, String g2, String simbolo1, String simbolo2, int p1, int p2) {
        vinto = false;
        this.g = g1;
        this.g2 = g2;
        this.simbolo = simbolo1;
        this.simbolo2 = simbolo2;
        this.p1 = p1;
        this.p2 = p2;
        vincente = null;
    }

    public int[] getVincente() {
        return vincente;
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
