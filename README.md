# 🧱 Spring Boot Project: Job Board

---

### Project: **Job Board / Hiring Portal**

### Features:

- Employers can post jobs
- Job seekers can view and apply
- Admin can manage all data
- Authentication + roles (admin/employer/seeker)
- REST API (or GraphQL if you want to flex)
- PostgreSQL with JPA
- Email notifications (bonus)
- Docker + Docker Compose
- Swagger UI docs
- Unit + integration tests
- CI/CD to deploy to Fly.io or Render

---

## 🧱 High-Level Modules:

| Module | Entities | Features |
| --- | --- | --- |
| **User** | User, Role | Sign up, Login, Auth, RBAC |
| **Jobs** | Job, Employer | Post job, edit, delete, view |
| **Applications** | Application, Resume | Apply to job, upload file, status track |
| **Admin** | Dashboard, User Management | View all users, deactivate, logs |

---

## ⚙️ Tech Stack:

- **Java 21**
- **Spring Boot 3.5**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **MapStruct (for DTO mapping)**
- **JUnit 5 + Mockito**
- **Docker + Docker Compose**
- Optional: Swagger, Redis, Kafka
