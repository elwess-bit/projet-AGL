# projet-AGL
#🎯 Planificateur Intelligent – Wissem Amiri

##👤 Mon rôle dans l'équipe

Développeur – responsable de la fonctionnalité Planificateur Intelligent (US#7 et US#8). Participation active à toutes les cérémonies Scrum (Daily, Sprint Planning, Review, Rétrospective). Collaboration avec le Product Owner et le Scrum Master pour affiner le backlog et assurer l'intégration de mon module avec les autres fonctionnalités.

##🧱 Ce que j'ai fait (travail complet)

##1. 📋 Organisation du projet (dès le début)

Création et rédaction du Product Backlog (avec toute l'équipe) – 10 User Stories au total. Écriture de mes 2 User Stories (US#7 et US#8) avec priorité MoSCoW, critères d'acceptation (6 pour US#7, 5 pour US#8) et estimation en Story Points (Fibonacci : 8 points chacune). Participation à la priorisation globale du backlog et à la création des Sprint Backlogs (Sprint 1 et Sprint 2). Rédaction de ma partie dans le rapport final (bilan individuel, documentation des prompts IA, captures d'écran).

##2. 🎨 Conception (Figma)

Création de 2 écrans Figma pour ma fonctionnalité : écran de génération automatique du planning (formulaire de saisie des tâches + tableau horaire 8h-20h) et écran d'ajustement dynamique (planning réorganisé après imprévu avec résumé 7/7 tâches planifiées). Conception d'interfaces claires et fonctionnelles montrant les priorités par couleur et les créneaux optimisés. Lien Figma partagé avec l'équipe et intégré dans le README.

##3. 💻 Développement (Java)

Création des classes PlanningService.java (cœur de l'algorithme avec 12 méthodes), Planning.java, Notification.java, NotificationService.java et ElementPlanning.java. Implémentation de l'algorithme de placement intelligent : tri par priorité, scoring des créneaux selon le niveau d'énergie (méthode scoreForSlot()), construction des disponibilités (buildAvailableSlots()), recherche du meilleur créneau (findBestSlot()). Implémentation de l'ajustement dynamique : recalcul automatique du planning et envoi de notifications en cas d'imprévu. Documentation des prompts IA utilisés dans le rapport.

##4. 🗃️ Gestion de version (Git & GitHub)

Création et maintien de ma branche : feature/smart-scheduler. Commits réguliers tout au long des deux sprints. Pull Request vers main avec description détaillée et revue de code. Intégration de mes classes dans Main.java pour la démonstration finale.

##5. 🧪 Tests & qualité

Test manuel de l'algorithme de génération avec différents scénarios (tâches avec/sans concentration, créneaux variés, contraintes de transport). Vérification de l'ajustement dynamique après imprévu. Gestion des cas limites (aucune tâche, aucun créneau disponible, tâche plus longue que le créneau).

##📊 Résumé des User Stories implémentées

ID	Description	Priorité	Story Points	Statut
US#7	Génération automatique du planning	Must	8	✅ Fait
US#8	Ajustement dynamique du planning	Could	8	✅ Fait

##📁 Fichiers livrés (dans ma branche)

PlanningService.java, Planning.java, ElementPlanning.java, Notification.java, NotificationService.java

##✅ Ce que j'ai appris

Concevoir et implémenter un algorithme d'optimisation de planning. Travailler en équipe avec Git (branches, PR, revues croisées). Utiliser les diagrammes UML (DCU, DC, DS) pour structurer ma fonctionnalité. Concevoir des maquettes Figma interactives. Coder en Java avec une approche hybride (AGL + IA). Rédiger un rapport professionnel et présenter ma fonctionnalité en soutenance.

🚀 Grâce à ce travail, l'utilisateur peut générer un planning optimisé en un clic et voir son emploi du temps s'adapter automatiquement aux imprévus pour une journée toujours sous contrôle.


