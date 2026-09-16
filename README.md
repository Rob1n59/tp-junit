# Compte Rendu de TP : Gestion de Comptes Bancaires avec JUnit

## 1. Présentation
Ce projet est une application de gestion de comptes bancaires (dépôts, retraits, virements). Le but était de concevoir un code robuste et de le valider à travers une suite de tests unitaires automatisés sous JUnit 5.

## 2. Choix de conception
J'ai opté pour une approche classique : écrire le code métier d'abord, puis les tests de validation ensuite. Pour la gestion des erreurs, j'ai créé des exceptions spécifiques (comme `MontantInvalideException` ou `SoldeInsuffisantException`) qui héritent de `RuntimeException`. Cela simplifie grandement la lisibilité du code.

## 3. Lancement des tests
Le projet utilise Maven. Pour exécuter toute la suite de tests depuis le terminal :
`mvn test`

## 4. Récapitulatif des tests

| Classe de test | Nombre | Ce qui est testé |
| :--- | :--- | :--- |
| `CompteBancaireTest` | 6 tests | Les opérations courantes, le calcul des intérêts, le refus de montants négatifs/nuls, et la limite stricte du découvert autorisé. |
| `GestionnaireComptesTest` | 4 tests | La recherche de comptes, la gestion des doublons d'IBAN, et surtout l'atomicité du virement (annulation complète si le solde est insuffisant). |

## 5. Difficultés rencontrées
Ma principale difficulté a concerné la création de mes exceptions personnalisées. Mon code était rempli d'erreurs rouges sur Eclipse car la classe n'était pas reconnue comme une vraie exception. Le problème venait de l'héritage de `RuntimeException` qui attend un identifiant de sérialisation. J'ai pu régler ça facilement grâce à l'ampoule jaune d'Eclipse : en cliquant dessus, elle m'a généré automatiquement un `serialVersionUID` par défaut (`private static final long serialVersionUID = 1L;`), ce qui a corrigé toutes mes erreurs d'un coup.

J'ai aussi eu un bug avec JUnit qui n'était pas reconnu par Eclipse au début, mais un simple `Maven > Update Project` a forcé la synchronisation avec le `pom.xml`.

## 6. Bilan
Ce TP m'a permis de voir l'utilité des tests unitaires dans un code où plusieurs fonctions fonctionnent ensemble et donc nécessite une exécution parfaite, surtout dans cet exemple de solution qui concerne de la gestion d'argent.
