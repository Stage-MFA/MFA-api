
-- Enum pour le statut d’intervention ou de maintenance :
CREATE TYPE StatusType AS ENUM ('ENCOURS', 'EN_ATTENTE', 'TERMINER');

-- Enum pour les rôles des utilisateurs :
CREATE TYPE RoleType AS ENUM ('ADMIN', 'USER', 'TECHNICIEN');

-- Enum pour la priorité des demandes :
CREATE TYPE PriorityType AS ENUM ('URGENT', 'ATTENDRE');

-- 1. Table des rôles des utilisateurs
CREATE TABLE Role (
   role_id INT PRIMARY KEY,
   nom_role RoleType NOT NULL
);

-- 2. Table des directions
CREATE TABLE Direction (
   direction_id INT PRIMARY KEY,
   nom VARCHAR(50) NOT NULL
);

-- 3. Table des spécialités techniques
CREATE TABLE Specialite (
   specialite_id INT PRIMARY KEY,
   nom_specialite VARCHAR(50) NOT NULL
);

-- 4. Table des utilisateurs
CREATE TABLE Utilisateur (
   utilisateur_id INT PRIMARY KEY,
   prenom VARCHAR(50) NOT NULL,
   nom VARCHAR(50) NOT NULL,
   email VARCHAR(100) NOT NULL,
   mot_de_passe VARCHAR(100) NOT NULL,
   genre VARCHAR(20) NOT NULL,
   role_id INT REFERENCES Role(role_id),
   direction_id INT REFERENCES Direction(direction_id),
   specialite_id INT REFERENCES Specialite(specialite_id)
);

-- 5. Table des matériels
CREATE TABLE Materiel (
   materiel_id INT PRIMARY KEY,
   nom VARCHAR(50) NOT NULL,
   type VARCHAR(50) NOT NULL,
   marque VARCHAR(50) NOT NULL,
   modele VARCHAR(50),
   numero_serie VARCHAR(50) NOT NULL,
   date_acquisition TIMESTAMP NOT NULL,
   garantie VARCHAR(50) NOT NULL
);

-- 6. Table des demandes d'intervention (à déclarer avant Intervention qui la référence)
CREATE TABLE Demande (
   demande_id INT PRIMARY KEY,
   date_demande TIMESTAMP,
   statut_demande StatusType,
   priorite PriorityType,
   materiel_id INT REFERENCES Materiel(materiel_id),
   utilisateur_id INT REFERENCES Utilisateur(utilisateur_id)
);

-- 7. Table des interventions
CREATE TABLE Intervention (
   intervention_id INT PRIMARY KEY,
   date_intervention TIMESTAMP NOT NULL,
   statut StatusType,
   description VARCHAR(255) NOT NULL,
   demande_id INT REFERENCES Demande(demande_id),
   utilisateur_id INT REFERENCES Utilisateur(utilisateur_id)
);

-- 8. Table des maintenances
CREATE TABLE Maintenance (
   maintenance_id INT PRIMARY KEY,
   date_debut TIMESTAMP NOT NULL,
   description VARCHAR(255),
   date_fin TIMESTAMP,
   statut StatusType,
   intervention_id INT REFERENCES Intervention(intervention_id)
);

-- 9. Table des alertes liées aux interventions
CREATE TABLE Alerte (
   alerte_id INT PRIMARY KEY,
   description VARCHAR(255) NOT NULL,
   date_creation TIMESTAMP NOT NULL,
   intervention_id INT REFERENCES Intervention(intervention_id)
);

-- 10. Journal des actions d’intervention
CREATE TABLE Journal (
   journal_id INT PRIMARY KEY,
   action VARCHAR(255) NOT NULL,
   duree_minutes INT NOT NULL,
   maintenance_id INT REFERENCES Maintenance(maintenance_id)
);

-- 11. Table des services liés à une direction
CREATE TABLE Service (
   service_id INT PRIMARY KEY,
   nom_service VARCHAR(50) NOT NULL,
   direction_id INT REFERENCES Direction(direction_id)
);
