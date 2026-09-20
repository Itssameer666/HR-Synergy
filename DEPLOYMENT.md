# HRMS Project Deployment Guide

This project is built using **Spring Boot 4.x / 3.x (Java 17+)**, **Thymeleaf**, and **MySQL**. It is configured to run smoothly in multiple deployment environments.

---

## 1. Deploying with Docker Compose (Recommended)

Docker Compose bundles both the **Spring Boot application** and **MySQL 8.0** together. No manual database setup is required.

### Prerequisites
- Install [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Windows/Mac) or Docker Engine + Docker Compose plugin (Linux).

### Quick Start
Run in the project root directory:

```bash
# Build and start both database and application in the background
docker compose up -d --build
```

- **HRMS Web App:** `http://localhost:8181/`
- **Admin Login:** `http://localhost:8181/adminlogin`
- **Database Container:** `localhost:3307` (mapped from container 3306)

### Useful Docker Commands
```bash
# View live application logs
docker compose logs -f hrms-app

# Stop all containers
docker compose down

# Stop all containers and delete database volume (fresh reset)
docker compose down -v
```

---

## 2. Deploying Standalone Executable JAR (VPS / AWS EC2 / Linux / Windows Server)

### Step 1: Package the Application
Run Maven wrapper to generate the production executable `.jar`:
```bash
# Windows
.\mvnw.cmd clean package -DskipTests

# Linux / macOS
./mvnw clean package -DskipTests
```
The output artifact is generated at:
```
target/hrms-0.0.1-SNAPSHOT.jar
```

### Step 2: Run the JAR
You can pass custom environment variables for your production database:
```bash
# Windows PowerShell
$env:DB_HOST="localhost"
$env:DB_PORT="3306"
$env:DB_NAME="hrmsdb"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
$env:PORT="8181"
java -jar target/hrms-0.0.1-SNAPSHOT.jar

# Linux / macOS
DB_HOST=localhost DB_PORT=3306 DB_NAME=hrmsdb DB_USERNAME=root DB_PASSWORD=your_password PORT=8181 java -jar target/hrms-0.0.1-SNAPSHOT.jar
```

---

## 3. Deploying to Free Cloud Platforms (Render / Railway)

### Option A: Railway (One-click)
1. Push your repository to **GitHub**.
2. Go to [Railway.app](https://railway.app/) and create a **New Project**.
3. Add a **MySQL** database service on Railway.
4. Add a **GitHub Repo** service selecting this repository (Railway detects the `Dockerfile` automatically).
5. In the Web service **Variables**, link Railway's MySQL credentials:
   - `DB_HOST`: `${{MySQL.MYSQLHOST}}`
   - `DB_PORT`: `${{MySQL.MYSQLPORT}}`
   - `DB_NAME`: `${{MySQL.MYSQLDATABASE}}`
   - `DB_USERNAME`: `${{MySQL.MYSQLUSER}}`
   - `DB_PASSWORD`: `${{MySQL.MYSQLPASSWORD}}`
   - `PORT`: `8181`

### Option B: Render.com
1. Create a free MySQL database on [Aiven.io](https://aiven.io/) or [TiDB Cloud](https://tidbcloud.com/).
2. Push this repo to GitHub.
3. On Render, select **New Web Service** -> Connect your GitHub repo.
4. Select Environment: **Docker**.
5. Under **Environment Variables**, provide:
   - `DB_HOST`: (your cloud mysql host)
   - `DB_PORT`: (your cloud mysql port)
   - `DB_NAME`: (your cloud database name)
   - `DB_USERNAME`: (your cloud username)
   - `DB_PASSWORD`: (your cloud password)

---

## 4. Environment Variables Reference

| Variable | Default Value | Description |
| :--- | :--- | :--- |
| `DB_HOST` | `localhost` | Hostname or IP of the MySQL server |
| `DB_PORT` | `3306` | MySQL port |
| `DB_NAME` | `hrmsdb` | MySQL database name |
| `DB_USERNAME` | `root` | MySQL user |
| `DB_PASSWORD` | `sameer@1234` | MySQL password |
| `PORT` | `8181` | Web server listening port |
