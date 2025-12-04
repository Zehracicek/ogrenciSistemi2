# Student Automation API

Simple Spring Boot REST API with roles (ADMIN/TEACHER/STUDENT), token-based auth, and CRUD for students.

Run:

```powershell
mvn spring-boot:run
```

Default seeded admin: username `admin`, password `adminpass`.

Login:
POST /api/auth/login { "username": "admin", "password": "adminpass" }

Use Authorization: Bearer <token> for protected endpoints.

