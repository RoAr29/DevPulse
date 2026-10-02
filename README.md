# DevPulse 🚀

DevPulse is a project management and progress-tracking platform built for developers.

The idea is pretty simple: when you're working on a project, your work is usually spread across GitHub, notes, task lists, and your own head. DevPulse brings these things together so you can see what you're working on, what you've completed, and how your project is progressing.

It supports both **GitHub-connected projects** and **manual projects**, so you can use it even when your project isn't hosted on GitHub.

## What can you do with DevPulse?

- Create and manage your projects
- Break projects into phases and tasks
- Track tasks and update progress manually
- Connect a project to a GitHub repository when you want to
- Sync development activity from GitHub
- Get AI-generated suggestions for breaking large tasks into smaller ones
- Get an AI-generated summary of project progress
- Maintain a public developer profile to showcase your projects

## How it works

A project in DevPulse is organized like this:

**Project → Phases → Tasks**

For a GitHub-connected project, GitHub activity can provide additional information about the project's development.

For a manual project, you can simply manage the project and update your progress yourself.

The AI layer sits on top of this data and helps with things like task breakdown and understanding overall project progress.

## Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- Spring Security
- Maven
- MySQL

### Frontend
- React
- Vite

### AI & Integrations
- LLM APIs
- GitHub API

## Architecture

DevPulse is being built as a small microservices-based application:

```text
                    React Frontend
                          |
                          v
                    Core Service
                    /           \
                   /             \
                  v               v
        GitHub Sync Service    AI Service
                  |               |
                  v               v
             GitHub API        LLM API

```
## Author 
- Srushti Agrawal
- Venkatesh Paitwar
