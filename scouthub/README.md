
# ScoutHub

ScoutHub is a web application designed to connect football players with scouts. The platform allows players to showcase their performance through match statistics, while scouts can discover and manage potential recruits using advanced filtering, target lists, and contract offer features.

## Features

### For Players
- Register and create a personal profile
- Add and manage match history with detailed statistics
- Track which scouts have shown interest
- Receive, review, and respond to contract offers

### For Scouts
- Register and create a scouting profile
- Search for players using filters (position, nationality, age, club, etc.)
- Add players to a personal Target List
- Send contract offers with optional contact information

## Data Model Highlights
- Users are divided into two roles: Player and Scout
- Match statistics include general and goalkeeper-specific metrics
- Offers are managed as formal contracts sent by scouts and responded to by players
- User privacy is respected — email and contact details are private by default and only shared upon offer acceptance

## Tech Stack
- Java 17
- Spring Boot (MVC, Security, Data JPA)
- PostgreSQL
- Hibernate ORM
- Maven / Gradle
- Git + GitHub Issues for task tracking

## Database Design
- ER diagram available in the `/docs` folder
- Entities: User, Player, Scout, Match, MatchStats, Offer, TargetList
- Relationships: One-to-many, many-to-many, weak and associative entities

## Testing & Quality
- JUnit and Mockito for unit/integration testing
- Security best practices (password hashing, role-based access control)
- Clean layered architecture: Controller → Service → Repository

## Project Structure
```
scouthub/
├── src/
│   ├── main/
│   │   ├── java/com/example/scouthub/
│   │   ├── resources/
│   └── test/
├── docs/
│   └── er_diagram.png
├── README.md
├── pom.xml / build.gradle
└── .gitignore
```

## Contributors
- Adilzhan Bekbolat – Project Creator
- Andrey Ivshin - Tutor
