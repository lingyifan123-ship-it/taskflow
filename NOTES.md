# 重建验收说明

- Java: 26.0.2.1（IDEA 当前配置）
- Spring Boot: 4.1.2
- 已验证: `mvn test`，2 tests / 0 failures / 0 errors
- Docker 配置: MySQL 8.4 + Redis 7 + application
- Docker 实机状态: 当前电脑未安装 Docker Desktop，待安装后执行 `docker compose up --build -d`
- 安全: JWT 密钥仅通过环境变量注入，仓库不保存 DeepSeek 或 GitHub 密钥
