# CU Grades

CU Grades is a full-stack course grade explorer for Carleton University.

The repository contains:
- `cugrades-backend` — Spring Boot API with PostgreSQL and Flyway migrations
- `cugrades-frontend` — React + Vite frontend

## Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven

### Frontend
- React 18
- Vite
- React Router

## Project Structure

```text
cugrades/
├── cugrades-backend/
│   ├── src/
│   ├── pom.xml
│   └── mvnw
├── cugrades-frontend/
│   ├── src/
│   ├── package.json
│   └── vite.config.js
├── .gitignore
├── .env.example
└── README.md
```

## Prerequisites

Install these before running the project:
- Java 21
- Maven or use the included Maven wrapper
- Node.js 18+
- npm
- PostgreSQL

## Environment Variables

The backend reads database settings from environment variables.

Create your own local environment values based on `.env.example`.

### Required backend variables
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

Example:

```env
DB_URL=jdbc:postgresql://localhost:5432/carleton_grades
DB_USERNAME=postgres
DB_PASSWORD=your_password_here
```

## Running the Backend

From the repo root:

### Windows
```bash
cd cugrades-backend
mvnw.cmd spring-boot:run
```

### macOS / Linux
```bash
cd cugrades-backend
./mvnw spring-boot:run
```

The backend runs on:
- `http://localhost:8080`

Flyway migrations will run automatically on startup.

## Running the Frontend

From the repo root:

```bash
cd cugrades-frontend
npm install
npm run dev
```

The frontend will usually run on:
- `http://localhost:5173`

