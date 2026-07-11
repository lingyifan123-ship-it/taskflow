# TaskFlow API

Base URL: `http://localhost:8080`. Except registration and login, requests require `Authorization: Bearer <token>`.

| Method | Path | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Register a normal user |
| POST | `/api/auth/login` | Login and receive JWT |
| POST | `/api/projects` | Create project |
| POST | `/api/tasks` | Create task |
| GET | `/api/tasks?projectId=1` | Query tasks |
| GET | `/api/tasks/{id}` | Query task, cacheable |
| PATCH | `/api/tasks/{id}/status` | Move TODO/DOING/DONE state |
| DELETE | `/api/tasks/{id}` | Owner or admin deletes task |
| GET | `/api/admin/logs` | Admin reads audit log |

Invalid fields return HTTP 400 with `message` and `fields`; insufficient privileges return HTTP 403.
