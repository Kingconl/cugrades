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

## Git Setup

Initialize Git from the **root `cugrades` folder** so both frontend and backend stay in one repository.

```bash
git init
git add .
git commit -m "Initial commit"
```

Then connect your GitHub repo:

```bash
git branch -M main
git remote add origin YOUR_GITHUB_REPO_URL
git push -u origin main
```

## What should not be pushed

This repo is set up to ignore:
- `node_modules/`
- frontend build output
- backend `target/`
- IDE files like `.idea/`
- local environment files like `.env`
- OS files like `.DS_Store`

## Notes

- Do **not** commit real database passwords.
- Keep `.env.example` in Git, but keep your real `.env` file out of Git.
- If `node_modules` or `target` were already added before `.gitignore`, remove them from Git tracking with:

```bash
git rm -r --cached cugrades-frontend/node_modules
git rm -r --cached cugrades-backend/target
git rm -r --cached cugrades-backend/.idea
```
