
La fonction getFinalState de spectator.ts reçoit un tableau d'événements. La fonction les parcourt dans l'ordre du tableau et renvoie l'état final de chaque robot.

L'événement MATCH_STARTED crée les robots avec une position initiale, ses points de vie et son étât. ROBOT_MOVED remplace la position du robot concerné. ROBOT_DAMAGED remplace ses points de vie et la valeur de alive. ROBOT_SHOT ne modifie pas directement ces informations.

Pas de grosse difficulté sur ce POC.