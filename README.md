# Projet-Dungeon

Projet de fin de semestre pour l'UE Programmation, réalisé en Java.

Le but de ce petit projet est de programmer un jeu de donjon. Dans ce jeu, un personnage se déplacera d’un étage à l’autre et rencontrera monstres et trésors . Le personnage se déplacera selon les quatre directions cardinales (Nord, Sud, Est, Ouest). 

Chaque personnage et monstre auront deux caractéristiques qui seront la force et la vitalité. 

Le système de combat consistera en une succession de tour où le personnage retirera diminuera la vitalité du monstre de la valeur de sa force et si le monstre a encore une vitalité positive il diminuera également la vitalité du personnage de la valeur de sa force. 

Le combat continue jusqu’à ce que le personnage ou le monstre ait une vitalité négative. 

Les trésors pourront être dans un premier temps des armes et des potions de soins qui augmenteront respectivement la force et la vitalité lorsqu’elles seront utilisées.

Pour passer d'un étage à un autre, il faut que le personnage soit sur une échelle et que le joueur appuie sur "E"

Chaque étage est unique, grâce à une génération procédurale de carte.

## Touches
- Z, Q, S, D : Déplacement
- E : Changer d'étage
- P : Pause


