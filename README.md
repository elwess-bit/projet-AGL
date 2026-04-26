# projet-AGL
# Smart Daily Planner :
Application de planification intelligente pour étudiants tunisiens.

## 📌 Pitch :
Smart Daily Planner aide les étudiants à organiser leurs tâches quotidiennes en générant automatiquement un emploi du temps optimisé. L'application prend en compte les créneaux de disponibilité, les priorités des tâches, le niveau d'énergie et les contraintes de transport. Ajustement dynamique en cas d'imprévu.

## 👥 Équipe :
| Samar Dhouib | Product Owner + Dev | Inscription & Profil Utilisateur Intelligent | `feature/auth-profile` |

| Maamoun Khedher | Scrum Master + Dev | Gestion des Tâches (CRUD) et priorités | `feature/task-manager` |

| Ghofrane Fitouri | Développeur | Moteur d'Analyse Comportementale & Contraintes | `feature/behavior-engine` |

| **Wissem Amiri** | **Développeur** | **Planificateur Intelligent** | **`feature/smart-scheduler`** |

| Alae Benelmekki | Développeur | Tableau de Bord & Statistiques | `feature/dashboard-stats` |


## 🏗️ Architecture du projet :

smart-daily-planner/

├── src/

│ └── com/

│ └── smartplanner/

│ ├── models/ # Classes métier

│ │ ├── Utilisateur.java

│ │ ├── Preference.java

│ │ ├── Tache.java

│ │ ├── Disponibilite.java

│ │ ├── Contrainte.java

│ │ ├── Planning.java

│ │ ├── ElementPlanning.java

│ │ └── Notification.java

│ ├── services/ # Logique métier

│ │ ├── UserService.java

│ │ ├── PreferenceService.java

│ │ ├── TaskService.java

│ │ ├── AvailabilityService.java

│ │ ├── PlanningService.java

│ │ └── NotificationService.java

│ ├── enums/ # Énumérations

│ │ ├── EnergyLevel.java

│ │ ├── PriorityLevel.java

│ │ ├── TaskType.java

│ │ └── TimeSlotType.java

│ └── Main.java # Point d'entrée

├── docs/

│ │ └── Rapport-de-projet.pdf

├── consolidé/

│ ├── captures/ # Captures d'écran

│ │ └── kanban/ # Captures Jira/Trello

│ │ └── maquettes/ # Exports Figma

│ │ └── reviews


├── diagrammes/

│ └── diagrammes/ # UML (PlantUML)

├── README.md

└── .gitignore


## 📋 User Stories
US#1 |	Création de compte utilisateur |	Must |	Samar

US#2 |	Profil énergétique |	Should |	Samar

US#3 |	Ajouter une tâche |	Must |	Maamoun

US#4 |	Priorité d'une tâche |	Must |	Maamoun

US#5 |	Créneaux de disponibilité |	Must |	Ghofrane

US#6 |	Contraintes de transport |	Should |	Ghofrane

US#7 |	Génération automatique du planning |	Must |	Wissem

US#8 |	Ajustement dynamique du planning |	Could |	Wissem

US#9 |	Visualisation du planning	Must |	Alae

US#10 |	Statistiques de productivité	Should |	Alae


##📸 Captures d'écran
  Maquettes Figma
    <img width="1642" height="877" alt="image" src="https://github.com/user-attachments/assets/c6c4d6b7-1add-484e-a8ca-92e45a95ae2a" />
    <img width="1548" height="855" alt="image" src="https://github.com/user-attachments/assets/2dfe8e57-4f2f-4947-a28c-d348d6c1995c" />


  
  Tableau Kanban

##🔗 Liens

  https://www.figma.com/make/y529Uz7jfAbSexyelnA4B6/G%C3%A9n%C3%A9rer-planning-optimis%C3%A9?t=UHYAbdlyFUSm5jur-1
  
  https://www.figma.com/make/nI2APRi7keTStrn2zhImoz/Ajustement-dynamique-du-planning?t=yNKn5RapGWn0AyUn-1
