# CoFoundr
Skill-based teammate matcher for hackathons and college projects — recommends compatible teammates using collaborative filtering instead of random group chats.

## Problem

Most hackathon and project teams form through random group chats or whoever you already know. This leads to mismatched skill sets, uneven workloads, and teams that don't actually complement each other. The platform matches students based on skills, interests, and what they want to build.

## How it works

Students create a profile listing their skills, interests, and project preferences. A matching engine (adapted from collaborative-filtering recommendation logic) suggests compatible teammates — people whose skills fill your gaps and whose interests align with your project goals.

## Architecture

Two services:

- **core-service** (Spring Boot) — user profiles, authentication, team creation/management
- **matching-service** (Python) — collaborative-filtering matching engine, exposed as a REST API

## Tech Stack

- **Backend:** Java, Spring Boot, Spring Security, JWT
- **Matching Engine:** Python, FastAPI, scikit-learn / pandas
- **Database:** MySQL
- **API testing:** Postman

## Features

- [ ] User registration & login (JWT auth)
- [ ] Profile creation (skills, interests, project goals)
- [ ] Team creation & invites
- [ ] Skill-based teammate recommendations
- [ ] Match explanation ("why this teammate")

## Project Structure

## Setup

### core-service
```bash
cd core-service
mvn clean install
mvn spring-boot:run
```

### matching-service
```bash
cd matching-service
pip install -r requirements.txt
uvicorn main:app --reload --port 8000
```

## Status

🚧 In progress — built as a learning project for backend + ML integration.

## Contributors

- Srushti Agrawal
- Venkatesh Paitwar
