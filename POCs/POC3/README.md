
1. La méthode main de Main.java appelle createBots. Elle crée 16 bots, de B1 à B16. Le constructeur de Bot.java donne à chacun un score Elo initial de 1000.
2. main appelle generateMatches de RoundRobin.java.Cette méthode crée un match pour chaque paire de bots.
3. main parcourt les matchs. Pour chacun, getWinner de Match.java désigne le bot au plus petit numéro. updateScores de Elo.java calcule ensuite les nouveaux scores Elo des deux bots, puis main affiche le résultat du match.
4. printRanking de Main.java trie les bots par score Elo décroissant et affiche le classement du round-robin.
5. main appelle de nouveau createBots pour repartir avec 16 bots à 1000 points. Il appelle ensuite generateMatches de SingleElimination.java Cette méthode associe les bots deux par deux et utilise getWinner pour former le tour suivant, jusqu'à la finale.
6. main parcourt ces matchs et appelle updateScores après chacun. Il affiche les résultats, le vainqueur de la finale, puis appelle printRanking pour afficher le classement Elo de l'élimination directe.

Les méthodes de création des matchs décident des rencontres. Elo.updateScores calcule les scores utilisés pour les classements ; ces scores ne changent pas les rencontres.


Ce qui m’a causé des difficultés sur ce POC, c’est le calcul du score Elo.
J’ai eu un bug dans le calcul du score, car je modifiais le premier score avant de calculer le second. Je me retrouvais donc avec des résultats faussés.