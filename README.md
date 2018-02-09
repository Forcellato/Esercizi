# Gioco del tris
***
Il gioco è composto di 3 Activity:
- la prima viene utilizzata per selezionare la modalità di gioco tra singolo e multigiocatore
- la seconda permette di inserire il nome del giocatore / dei giocatori
- la terza permette di giocare a seconda della modalità precedentemente selezionata.

Per poter sviluppare l'intelligenza artificiale è stato sviluppato un algoritmo che, analizzata la mossa compiuta dal giocatore, esegue la mossa per poterlo ostacolare. Se non dovesse esserci mossa in grado di poterlo ostacolare, allora esegue la mossa su un punto accanto ad una casella contenente la propria pedina. In caso sia la macchina a dover iniziare allora si posiziona su una posizione casuale.