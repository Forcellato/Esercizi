/**
 * Author:  francesco.forcellato
 * Created: 12-mar-2018
 */ 
PRAGMA foreign_keys = 1;

DROP TABLE IF EXISTS tbrAppassionato ;
DROP TABLE IF EXISTS tbrAppartiene ;
DROP TABLE IF EXISTS tblM_Utente ;
DROP TABLE IF EXISTS tblM_Gruppo ;
DROP TABLE IF EXISTS tblMessaggio ;
DROP TABLE IF EXISTS tblGruppo ;
DROP TABLE IF EXISTS tblAmministratore ;
DROP TABLE IF EXISTS tblUtente ;
DROP TABLE IF EXISTS tblHobby ;

CREATE TABLE tblUtente (
	idUtente INTEGER PRIMARY KEY AUTOINCREMENT,
	nick VARCHAR(30) ,
	email VARCHAR(30) ,
	cognomeNome VARCHAR(30) ,
	password VARCHAR(150) ,
	tel VARCHAR(30) ,
	foto TEXT,
	UNIQUE(Email),
	UNIQUE(Nick)
);

CREATE TABLE tblHobby (
	idHobby INTEGER PRIMARY KEY AUTOINCREMENT,
	nomeHobby VARCHAR(30) UNIQUE,
        descrizione TEXT
);

CREATE TABLE tbrAppassionato (
    idUtente INTEGER,
    idHobby INTEGER,
    PRIMARY KEY (idUtente, idHobby),
    FOREIGN KEY (idUtente) REFERENCES tblUtente(idUtente) ON DELETE CASCADE,
    FOREIGN KEY (idHobby) REFERENCES tblHobby(idHobby) ON DELETE CASCADE
);

CREATE TABLE tblGruppo (
    idGruppo INTEGER PRIMARY KEY AUTOINCREMENT,
    nomeGruppo VARCHAR (30) UNIQUE
);

CREATE TABLE tbrAppartiene (
    idUtente INTEGER,
    idGruppo INTEGER,
    isAmministratore BOOLEAN,
    FOREIGN KEY (idUtente) REFERENCES tblUtente(idUtente) ON DELETE CASCADE,
    FOREIGN KEY (idGruppo) REFERENCES tblGruppo(idGruppo) ON DELETE CASCADE,
    PRIMARY KEY (idUtente, idGruppo)
);

CREATE TABLE tblMessaggio (
    idMessaggio INTEGER PRIMARY KEY AUTOINCREMENT,
    oggetto TEXT,
    dataOra VARCHAR(30),
    idUtente INTEGER,
    FOREIGN KEY (idUtente) REFERENCES tblUtente(idUtente) ON DELETE CASCADE
);

CREATE TABLE tblM_Utente (
    idMessaggio INTEGER PRIMARY KEY,
    idUtente INTEGER,
    FOREIGN KEY (idMessaggio) REFERENCES tblMessaggio(idMessaggio) ON DELETE CASCADE ,
    FOREIGN KEY (idUtente) REFERENCES tblUtente(idUtente) ON DELETE CASCADE
);

CREATE TABLE tblM_Gruppo (
    idMessaggio INTEGER PRIMARY KEY,
    idGruppo INTEGER,
    FOREIGN KEY (idMessaggio) REFERENCES tblMessaggio(idMessaggio) ON DELETE CASCADE,
    FOREIGN KEY (idGruppo) REFERENCES tblGruppo(idGruppo) ON DELETE CASCADE
);

CREATE TABLE tblAmministratore (
    idUtente INTEGER PRIMARY KEY,
    FOREIGN KEY (idUtente) REFERENCES tblUtente(idUtente) ON DELETE CASCADE
);