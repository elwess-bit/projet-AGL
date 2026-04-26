# 🎯 Planificateur Intélligent – Wissem Amiri

## 👤 Mon rôle dans l’équipe

- **Développeur** – responsable de la fonctionnalité Planificateur Intelligent (US#7 et US#8).
- Participation active à toutes les cérémonies Scrum (Daily, Sprint Planning, Review, Rétrospective).
- Collaboration avec le Product Owner et le Scrum Master pour affiner le backlog et assurer l'intégration de mon module avec les autres fonctionnalités.

---

## 🧱 Ce que j’ai fait (travail complet)

### 1. 📋 Organisation du projet (dès le début)

- Écriture de mes **2 User Stories** (US#7 et US#8) avec :
  - Priorité MoSCoW
  - Critères d'acceptation (3 par US)
  - Estimation en Story Points (Fibonacci)

### 2. 🎨 Conception (Figma)

- Création de **2 écrans Figma** pour ma fonctionnalité :
  - `Ajustement automatique en cas d'imprévu.png` – écran de génération automatique du planning (formulaire de saisie des tâches + tableau horaire 8h-20h).
  - `Générez automatiquement votre planning .png` – écran d'ajustement dynamique (planning réorganisé après imprévu avec résumé 7/7 tâches planifiées).
  - Conception d'interfaces claires et fonctionnelles montrant les priorités par couleur et les créneaux optimisés.
- Lien Figma partagé avec l'équipe et intégré dans le README
- Création de **Diagrammes use case global , de classes et de séquences** pour ma fonctionnalité

### 3. 💻 Développement (Java)

- Création des classes :
  - `PlanningService.java ` – cœur de l'algorithme.C'est un service qui implémente toute la logique de génération et d'optimisation du planning avec 12 méthodes
  - `Planning.java` – Modèle principal – Classe qui représente le planning généré avec sa liste d'éléments (ElementPlanning), ses dates de début/fin, son statut et sa date de génération. Elle est produite par PlanningService et affichée à l'utilisateur.
  - `ElementPlanning.java` – gestion des éléments du planning
  - `NotificationService.java`Gestion des alertes – Service qui envoie les notifications à l'utilisateur en cas d'imprévu : envoyerNotificationRetard(), envoyerNotificationChangement() et envoyerRappelTache(). Essentiel pour l'US#8 (ajustement dynamique).
    
  - Implémentation de l'algorithme de placement intelligent : tri par priorité, scoring des créneaux selon le niveau d'énergie (méthode scoreForSlot()), construction des disponibilités (buildAvailableSlots()), recherche       du meilleur créneau (findBestSlot()). Implémentation de l'ajustement dynamique : recalcul automatique du planning et envoi de notifications en cas d'imprévu. Documentation des prompts IA utilisés dans le rapport.
- Documentation des **prompts IA** utilisés (dans le rapport).

### 4. 🗃️ Gestion de version (Git & GitHub)
- Création du dépot de GitHub
- Création du branche principale : `Smart-Planner`
- Création de la structure du dépot de GitHub
- Création de ma branche : `feature/smart-scheduler`
- Commits réguliers (tous les commits sont visibles sur GitHub)
- Pull Request vers `Smart-Planner` (fusionnée après review)
- Ajout des maquettes dans `consolidé/captures/maquettes/`
- README spécifique à ma branche

### 5. 🧪 Tests & qualité

- Test manuel de l'algorithme de génération avec différents scénarios (tâches avec/sans concentration, créneaux variés, contraintes de transport)
- Vérification de l'ajustement dynamique après imprévu
- Gestion des cas limites (aucune tâche, aucun créneau disponible, tâche plus longue que le créneau).

---

## 📊 Résumé des User Stories implémentées

| ID | Description | Priorité | Story Points | Statut |
|----|-------------|----------|--------------|--------|
| **US#7** | Génération automatique du planning | Must | 8 | ✅ **Fait** |
| **US#8** | Ajustement dynamique du planning | Could | 8 | ✅ **Fait** |

---

## 📁 Fichiers livrés (dans ma branche)
-PlanningService.java, Planning.java, ElementPlanning.java, Notification.java, NotificationService.java
---

## ✅ Ce que j’ai appris

- Concevoir et implémenter un algorithme d'optimisation de planning. Travailler en équipe avec Git (branches, PR, revues croisées)
- Utiliser les diagrammes UML (DCU, DC, DS) pour structurer ma fonctionnalité
- Concevoir des maquettes Figma interactives. Coder en Java avec une approche hybride (AGL + IA)
- Coder en Java avec une approche hybride (AGL + IA)

---

> 🚀 *Grâce à ce travail, l'utilisateur peut générer un planning optimisé en un clic et voir son emploi du temps s'adapter automatiquement aux imprévus pour une journée toujours sous contrôle.*
