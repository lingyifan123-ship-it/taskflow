# TaskFlow

这是我根据丢失的本地工程重新整理的一套任务协作后端。重点不是页面，而是把注册登录、项目、任务、权限、缓存和部署这一整条链路跑通。

## 技术栈

Java 26、Spring Boot 4.1、Spring Security、JWT、MyBatis-Plus、MySQL、Redis、JUnit 6、MockMvc、Docker Compose。

## 快速启动

需要 JDK 26、Maven 3.9+；完整环境另需 Docker。

```bash
git clone https://github.com/lingyifan123-ship-it/taskflow.git
cd taskflow
docker compose up --build -d
curl http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"username":"admin","password":"Admin123!"}'
```

不使用 Docker 时直接 `mvn spring-boot:run`，默认使用内存 H2 和本地缓存。

## 接口例子

```bash
curl -X POST localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"username":"student","password":"Password1!"}'
curl -X POST localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"username":"student","password":"Password1!"}'
curl -X POST localhost:8080/api/projects -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d '{"name":"课程项目","description":"后端接口"}'
curl -X POST localhost:8080/api/tasks -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d '{"projectId":1,"title":"写登录接口","priority":"HIGH"}'
curl -X PATCH localhost:8080/api/tasks/1/status -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d '{"status":"DOING"}'
curl localhost:8080/api/tasks/1 -H "Authorization: Bearer $TOKEN"
```

## 测试

运行 `mvn test`。目前集成测试覆盖注册、JWT 鉴权、项目创建、任务创建、状态流转、查询、非法参数和管理员权限拦截。覆盖率数字暂不手写，后续接 JaCoCo 后以生成报告为准。

## 目录

- `api`：接口、DTO、统一异常
- `domain`：用户、项目、任务、审计记录
- `mapper`：MyBatis-Plus 数据访问
- `security`：JWT 生成与过滤
- `service`：业务和缓存更新
- `docs/api.md`：接口说明

## 已知问题

- Docker 实机验证需要本机安装 Docker Desktop；当前重建电脑未检测到 Docker。
- 默认管理员密码仅供本地启动，部署时必须更换并使用环境变量保存密钥。

## 重建说明

原项目曾在本地完成但因系统恢复丢失，本仓库依据留存的项目说明重新实现；Git 提交时间代表本次重建时间。
