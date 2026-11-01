# tasbolatov-temirlan-student-api

Author: Tasbolatov Temirlan

Spring Boot CRUD application for managing students, with PostgreSQL, Docker Compose and a Jenkins pipeline.

Project status: stable version 1.0, documentation updated by Tasbolatov Temirlan

## Stack

- Java 17, Spring Boot 3, Gradle
- PostgreSQL 16
- Docker, Docker Compose
- Jenkins (declarative pipeline)

## Run

```bash
docker compose up -d --build
curl http://localhost:8080/
```

## API

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/` | Project info (author, version) |
| POST | `/api/students` | Create a student |
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get one student |
| PUT | `/api/students/{id}` | Update a student |
| DELETE | `/api/students/{id}` | Delete a student |

Example body:

```json
{"firstName":"Temirlan","lastName":"Tasbolatov","email":"temirlan@example.com","course":2}
```

## Docker image

`tasbolatov-temirlan-student-api:v1.0-tasbolatov-temirlan`

## Changes by Tasbolatov Temirlan

- Added endpoint `GET /api/students/search?lastName=...` to find students by last name (case-insensitive).

CI auto-trigger test by Tasbolatov Temirlan
Auto-trigger check by Tasbolatov Temirlan
