# Dev Container ARES

Environnement de développement unique pour tous les lots du projet.

## Contenu

| Lot | Outils fournis |
|---|---|
| Runtime | gcc, g++, cmake, ninja, gdb, sanitizers, `ps` (procps) |
| Moteur, Ligue | Java 21 (Temurin), Maven |
| Spectateur | Node.js 20, TypeScript, ts-node |
| Bots | Python 3 |
| Commun | git |

## Utilisation

1. Ouvrir le dépôt dans VS Code avec l'extension **Dev Containers**.
2. « Reopen in Container ». La première construction prend quelques minutes.
3. À la fin, `postCreate.sh` affiche la version de chaque outil : tout doit répondre.

En ligne de commande, sans VS Code :

```bash
devcontainer up --workspace-folder .
```

## Ouvrir dans IntelliJ IDEA

IntelliJ gère les Dev Containers nativement depuis la version 2023.2 (Community
et Ultimate). Utile pour les lots Java.

1. Écran d'accueil → **Remote Development** → **Dev Containers**, ou clic droit
   sur `.devcontainer/devcontainer.json` → **Create Dev Container**.
2. IntelliJ construit le conteneur et s'y installe en backend. JDK 21, Maven et
   les autres outils sont pris dans le conteneur, pas sur la machine hôte.

Le bloc `customizations.vscode` du `devcontainer.json` est **ignoré** par
IntelliJ (il ne concerne que VS Code) ; sans effet, IntelliJ apporte son propre
support Java. Le Dockerfile, le `build` et le `postCreateCommand` sont honorés
à l'identique : la chaîne d'outils obtenue est la même quel que soit l'éditeur.

> Le support Dev Containers d'IntelliJ est plus récent que celui de VS Code.
> En cas de soucis, utiliser VSCode aussi pour Java.

## Placement dans le dépôt

Placer le dossier `.devcontainer/` à la racine du dépôt :

```
ARES/
├── .devcontainer/
│   ├── devcontainer.json
│   ├── Dockerfile
│   ├── postCreate.sh
│   └── README.md
├── runtime/       (C/C++)
├── engine/        (Java)
├── league/        (Java)
└── spectator/     (TypeScript)
```