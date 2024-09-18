CREATE TABLE Projet (
  id VARCHAR(255) PRIMARY KEY,
  nomProjet VARCHAR(255) NOT NULL,
  surface REAL NOT NULL,
  margeBeneficiaire REAL NOT NULL,
  coutTotal REAL NOT NULL,
  etatProjet TEXT CHECK (etatProjet IN ('ENCOURS', 'TERMINE', 'ANNULE')) NOT NULL
);


CREATE TABLE Composant (
  id VARCHAR(255) PRIMARY KEY,
  nom VARCHAR(255) NOT NULL,
  typeComposant TEXT CHECK (typeComposant IN ('MATERIEL', 'MAINDOEUVRE')) NOT NULL,
  tauxTVA REAL NOT NULL
);

CREATE TABLE MainDoeuvre (
  tauxHoraire REAL NOT NULL,
  heuresTravail REAL NOT NULL,
  productiviteOuvrier REAL NOT NULL
) INHERITS (Composant);


CREATE TABLE Materiel (
  coutUnitaire REAL NOT NULL,
  quantite REAL NOT NULL,
  coutTransport REAL NOT NULL,
  coefficientQualite REAL NOT NULL
) INHERITS (Composant);


CREATE TABLE Client (
  id VARCHAR(255) PRIMARY KEY,
  nom VARCHAR(255) NOT NULL,
  adresse VARCHAR(255) NOT NULL,
  telephone VARCHAR(15) NOT NULL,
  estProfessionnel BOOLEAN NOT NULL
);

CREATE TABLE Devi (
  id VARCHAR(255) PRIMARY KEY,
  montantEstime REAL NOT NULL,
  dateEmission DATE NOT NULL,
  dateValidite DATE NOT NULL,
  TVA REAL NOT NULL,
  accepte BOOLEAN NOT NULL,
  projetId VARCHAR(255),
  FOREIGN KEY (projetId) REFERENCES Projet(id)
);
