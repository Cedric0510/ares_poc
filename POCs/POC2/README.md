Fonctions:

- `main` : reçoit le chemin d'un bot, lance ce bot dans un "process", affiche `OK` ou `TIMEOUT`, puis termine et récupère le process enfant.

Le programme a été compilé sous Linux avec :
- gcc -Wall -Wextra runtime.c -o /tmp/runtime

Impossible de faire fonctionner les bot slow et loop
 `bot_ok.py` = `OK` 
 `bot_slow.py` = `TIMEOUT` 
 `bot_loop.py` = `TIMEOUT` 

Après le test de `bot_loop.py`, la commande `ps -ef` a donné :

UID          PID    PPID  C STIME TTY          TIME CMD
root           1       0  0 09:25 pts/0    00:00:00 bash
root          26       1 95 09:33 pts/0    00:00:00 ps -ef


Aucun bot ne restait actif.

## Difficultés rencontrées

La plus grande difficulté à été la compréhension du POC et sa mise en oeuvre en C.
Je ne suis pas arrivé a tester avec tous les Bots.
