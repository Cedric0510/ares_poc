# POC1 - Moteur


Le fichier poc1-init.json contient l'arène de 100 X 100, la vitesse de déplacement et deux robots. Chaque robot commence avec 5 points d'énergie et 5 points de vie.

Main.main charge ce fichier. readActions lit les actions et recherche le robot indiqué par robot_id. Les autres classes représentent les données du JSON : World pour l'arène, Robot pour un robot, GameState pour l'état initial et Action pour une action reçue.

- WAIT remet l'énergie du robot à 5. Cette action reste autorisée même si le robot n'a plus de points de vie.
- MOVE avance le robot selon l'angle indiqué et la vitesse de déplacement. Sa position s'arrête au bord de l'arène.
- MINE consomme un point d'énergie et produit NO_CRYSTAL, car ce POC ne contient pas de cristaux.
- SHOOT consomme un point d'énergie. Si le point situé à 10 unités dans la direction du tir correspond exactement à la position d'un autre robot vivant, celui ci perd un point de vie.
- TELEPORT déplace le robot aux coordonnées x et y indiquées si elles sont dans l'arène, puis consomme un point d'énergie. Une destination invalide est refusée.

MOVE, MINE, SHOOT et TELEPORT sont refusées si le robot n'a plus de points de vie ou d'énergie. Les angles de déplacement et de tir sont utilisés en radians.



J'ai eu du mal avec SHOOT qiu n'est pas encore parfait.
Après relecture a tête reposé, j'ai utilisé import com.google.gson.Gson alors que ce n'est pas demandé dans le POC.
