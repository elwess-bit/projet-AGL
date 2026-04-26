# projet-AGL
<<<<<<< Updated upstream
# Smart Daily Planner :
Application de planification intelligente pour étudiants tunisiens.

## 📌 Pitch :
Smart Daily Planner aide les étudiants à organiser leurs tâches quotidiennes en générant automatiquement un emploi du temps optimisé. L'application prend en compte les créneaux de disponibilité, les priorités des tâches, le niveau d'énergie et les contraintes de transport. Ajustement dynamique en cas d'imprévu.

## 👥 Équipe :
| Samar Dhouib | Product Owner + Dev | Inscription & Profil Utilisateur Intelligent | `feature/auth-profile` |

| **Maamoun Khedher** | **Scrum Master + Dev** | **Gestion des Tâches (CRUD) et priorités** | **`feature/task-manager`** |

| Ghofrane Fitouri | Développeur | Moteur d'Analyse Comportementale & Contraintes | `feature/behavior-engine` |

| **Wissem Amiri** | **Développeur** | **Planificateur Intelligent** | **`feature/smart-scheduler`** |

| **Alae Benelmekki** | **Développeur** | **Tableau de Bord & Statistiques** | **`feature/dashboard-stats`** |


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
    <img width="1148" height="743" alt="image" src="https://github.com/user-attachments/assets/4154fddd-5c53-43ec-9cb7-59557829d4a8" />
    <img width="1106" height="709" alt="image" src="https://github.com/user-attachments/assets/f3bcc7a1-79ea-4d24-9847-29065798d2fe" />
    <img width="1163" height="744" alt="image" src="https://github.com/user-attachments/assets/d28a7cd9-fb24-4b17-b6aa-083e43458651" />
 




    
  
  **Tableau Kanban au debut du sprint** *(avril 2026 – état initial des tâches)*
   ![Tableau Kanban - Smart Daily Planner](https://github.com/elwess-bit/projet-AGL-Smart-Planner/blob/feature/dashboard-stats-Alae-Benelmekki-v3/consolid%C3%A9/captures/kanban/Screenshot%202026-04-19%20135658.png?raw=true)
   **Tableau Kanban en fin de sprint**  *(avril 2026 – après avancement des US)*
   ![Tableau Kanban - Smart Daily Planner](https://github.com/elwess-bit/projet-AGL-Smart-Planner/blob/feature/dashboard-stats-Alae-Benelmekki-v3/consolid%C3%A9/captures/kanban/Screenshot%202026-04-26%20171511.png?raw=true)
    <img width="847" height="788" alt="tableau kanban" src="https://github.com/user-attachments/assets/ff98f13e-fb23-48b7-a8d2-2af90014c951" />

##🔗 Liens
 **maquettes US7+8**
 
  https://www.figma.com/make/y529Uz7jfAbSexyelnA4B6/G%C3%A9n%C3%A9rer-planning-optimis%C3%A9?t=UHYAbdlyFUSm5jur-1
  
  https://www.figma.com/make/nI2APRi7keTStrn2zhImoz/Ajustement-dynamique-du-planning?t=yNKn5RapGWn0AyUn-1
  
  **maquettes US9+10**
  
  https://www.figma.com/make/Zk8oGlATWbgHgDEjkL7xkO/Visualisation-et-Statistiques-Planning?fullscreen=1&t=eDfW57i1KVFjYqZb-1

 **maquettes US3**

https://www.figma.com/make/LqNhF2YKXSY8fF7GVXGwif/Ajouter-une-nouvelle-t%C3%A2che?t=biWX0apFaDepjPCv-1

**maquettes US4**

https://www.figma.com/make/ZXqKvkEUiTOvyCK2qE5vKA/Blog-app-with-character-limits?t=0RFmEJleV3ukPd3f-1

