# API de Gestion de Maintenance Assistée par Ordinateur (GMAO)

Ce projet est une API RESTful développée avec Spring Boot pour la gestion de maintenance assistée par ordinateur. Elle permet de gérer les matériels, les demandes d'intervention et les utilisateurs de manière sécurisée.

## Fonctionnalités

- **Gestion des matériaux** : CRUD complet pour les matériaux avec suivi des statuts
- **Authentification** : Sécurisation des endpoints avec JWT
- **Documentation** : API documentée avec Swagger/OpenAPI
- **Validation** : Validation des données d'entrée
- **Gestion des erreurs** : Gestion centralisée des exceptions
- **Sécurité** : Configuration CORS et protection CSRF

## Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- PostgreSQL 12 ou supérieur
- Un IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## Installation

1. Cloner le dépôt :
   ```bash
   git clone [URL_DU_DEPOT]
   cd MFA-api
   ```

2. Configurer la base de données :
   - Créer une base de données PostgreSQL nommée `mfa_db`
   - Mettre à jour les informations de connexion dans `src/main/resources/application.yml` si nécessaire

3. Construire et exécuter l'application :
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Accès à la documentation

Une fois l'application démarrée, vous pouvez accéder à :

- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **Documentation OpenAPI** : http://localhost:8080/v3/api-docs

## Structure du projet

```
src/main/java/com/school/security/
├── config/               # Configurations de l'application
├── controllers/          # Contrôleurs REST
│   ├── api/             # Contrôleurs des fonctionnalités métier
│   └── auth/            # Contrôleurs d'authentification
├── dtos/                # Objets de transfert de données
│   ├── requests/        # DTOs pour les requêtes
│   └── responses/       # DTOs pour les réponses
├── entities/            # Entités JPA
├── enums/               # Énumérations
├── exceptions/          # Exceptions personnalisées
├── mappers/             # Mappers entre entités et DTOs
├── repositories/        # Interfaces de repository JPA
├── securities/          # Configuration de sécurité
│   ├── config/         # Configuration Spring Security
│   └── services/       # Services de sécurité
└── services/            # Couche service
    ├── contracts/      # Interfaces de service
    └── implementations/ # Implémentations des services
```

## Développement

### Technologies utilisées

- **Backend** : Spring Boot 3.x, Spring Security, JPA/Hibernate
- **Base de données** : PostgreSQL
- **Documentation** : SpringDoc OpenAPI 3
- **Outils** : Maven, Lombok, MapStruct

### Variables d'environnement

Les variables suivantes peuvent être définies dans `application.yml` ou en tant que variables d'environnement :

- `SPRING_DATASOURCE_URL` : URL de la base de données
- `SPRING_DATASOURCE_USERNAME` : Nom d'utilisateur de la base de données
- `SPRING_DATASOURCE_PASSWORD` : Mot de passe de la base de données
- `JWT_SECRET` : Clé secrète pour la génération des JWT
- `JWT_EXPIRATION_MS` : Durée de validité des JWT en millisecondes

## Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.
