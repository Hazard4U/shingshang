

# PROJET INFO ACDC 2020-2021: SHING SHANG

## Présentation générale du projet

L’objectif de ce projet est d’implémenter un jeu de plateau, appelé SHING SHANG, qui se joue sur un damier de 84 cellules avec 2 ∗ 12 pièces (Bushis).

Il s’agit d’un jeu à deux joueurs de pure stratégie. Une variante avec quatre joueurs existe aussi. Chaque joueur possède une armée de 12 Bushis. Cette armée est composée de 3 groupes : 2 dragons, 4 lions et 6 singes. Les différents groupes diffèrent par la taille de leur pièces (les singes étant les plus petits, suivi par les lions puis les Dragons). Chaque joueur place son armée dans les coins du damier (voir le schéma à l’URL : http://jeuxstrategieter.free.fr/Shing_shang_complet.php).

Des règles du jeu détaillées afin de lever toute ambiguïté sont proposées dans la Section 2. Dans la Section 3, nous vous décrivons les différentes étapes de votre travail d’implémentation du jeu, et les différents rendus attendus. Enfin, la Section 4 présente les extensions possibles.

## 2 Règles du jeu détaillées

Il existe plusieurs variantes du jeu de SHING SHANG. Dans ce projet, vous devez impérativement respecter les règles ci-dessous. Toute modification doit être explicitement indiquée.

Les joueurs exécutent chacun leur tour une action parmi les deux actions suivantes :

-   un joueur peut déplacer l’une de ses pièces présentes sur le plateau vers une autre case du plateau.
    
-   un joueur peut sauter par dessus une autre pièce si celle-ci est plus petite ou de même taille que la pièce du sauteur.
    
Pour déplacer une pièce sur le plateau, il est nécessaire que la case d’arrivée soit libre. On peut se déplacer horizontalement, verticalement ou en diagonale, aussi bien en avant qu’en arrière. Pour sauter, la pièce du sauteur doit se trouver sur une case contiguë à une case occupée par l’une de ses propres pièces ou par celle du joueur adverse. Le saut peut se faire verticalement, horizontalement ou en diagonale, à condition que la case suivante soit vide. On peut enchaîner plusieurs sauts au cours d’un même tour. Cet enchaînement de sauts s’appelle un SHING SHANG.
    
Les règles suivantes complètent celles indiquées plus haut :
    
-   Les singes peuvent se déplacer d’une ou deux cases dans n’importe quelle direction, horizontalement, verticalement ou en diagonale mais sans changer de direction au cours du tour.
        
-   Les lions peuvent se déplacer d’une case dans n’importe quelle direction, horizontalement, verticalement ou en diagonale.
        
-   Les dragons ne peuvent se déplacer qu’en sautant.
        
Si, lors d’un SHING SHANG ; on saute par dessus une pièce adverse, on doit s’arrêter et la pièce de l’adversaire est retirée du plateau. Toutefois, on gagne un tour de jeu supplémentaire avec une autre pièce (voir les exemples des Figures 1 et 2). Un joueur remporte la partie lorsqu’il parvient à amener l’un de ses dragons sur l’un des portails (i.e. deux cases spéciales) de son adversaire ou qu’il capture les deux dragons de son adversaire.
        

FIGURE 1 – Le grand Bushi saute par dessus 2 Bushis à soi.

FIGURE 2 – Le grand Bushi saute par dessus 2 Bushis à soi et d’un Bushi de l’adversaire. Le joueur a droit à un autre tour de jeu avec un autre Bushi.

## 3 Déroulement du projet  
### 3.1 Rendu d’une interface console

Dans un premier temps, nous vous demandons de réaliser une interface console pour jouer au jeu. Vous pouvez choisir la présentation du jeu selon votre préférence, sans nécessairement suivre l’exemple fourni. Le premier rendu doit permettre à un joueur de pouvoir jouer certaines catégories de pièces sur le plateau et vérifier si les déplacements associés sont valides ou pas. Durant cette phase, vous devez produire un code bien structuré, bien commenté et qui respecte l’architecture PAC.

**Ce répo concerne uniquement cette partie du projet.**

### 3.2 Interface graphique

La deuxième phase consiste à reprendre les fonctionnalités implémentées lors de la phase 1, les finaliser, puis à implémenter une interface graphique pour le jeu.

## 4 Extensions possibles

Nous vous invitons à faire ce qu’on appelle un bot, c’est à dire un programme qui se substitue au joueur. Idéalement, vous pourriez proposer une version où il est possible de régler la difficulté du bot. Nous vous demandons de réaliser un bot facile jouant presque au hasard. Attention, cette partie est facultative. Vous devez absolument terminer la phases 1 et 2 avant d’entamer toute extension du jeu.
