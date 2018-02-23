##### Francesco Forcellato 5IA 21/02/2018
# DadoFragment
***
Lo scopo di questo progetto è quello di utilizzare i fragment per poter simulare il lancio di un dado.

Quando viene aperta l'applicazione viene visualizzato un messaggio di benvenuto che spiega il funzionamento del lancio del dado: il dado infatti può venir lanciato o attraverso uno swipe, oppure attraverso un movimento rapido del dispositivo.
Per l'animazione del fragment iniziale di benvenuto, sono stati utilizzati degli xml animator, in modo da fare alcune animazioni semplici. Per le animazioni del dado, è stato implementato il codice presente in questo repository:
https://github.com/kakajika/FragmentAnimations
che permette infatti di poter eseguire delle animazioni grafiche tridimensionali, per poter simulare un cubo, rendendo il dado più verosimile possibile.


Attraverso l'accelerometro è stato possibile determinare il rapido movimento del dispositivo, permettendo di cambiare faccia del dado agitando il telefono.


Per la parte grafica invece, é stato creata un immagine per ogni faccia tramite il programma Inkscape, programma per il disegno in vettoriale. Il colore delle facce invece, viene definito dinamicamente: nel file xml é presente il colore che definisce quello con cui verrá dipinta la faccia del dado, ma tramite una funzione, questo colore viene generato casualmente, tramite la trasformazione del colore da rgb a hsv, e poi modificando il campo hue in maniera casuale; in questa maniera é possibile lasciare invariata la saturazione e la luminositá, modificando solamente il colore.


É stata inoltre aggiunta la possibilitá di poter tornare al fragment di presentazione attraverso il doppiotocco prolungato.
