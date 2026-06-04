# Cryptio — Cryptocurrency Wallet REST API

> MSc Thesis Project · University of Piraeus, Department of Informatics

A production-style **RESTful API backend** for managing cryptocurrency portfolios. Built with Java 17 and Spring Boot 3.3.4, it handles everything from JWT-secured authentication to live BUY/SELL transactions with real market prices from CoinMarketCap.

The React frontend for this project lives here → **[Cryptio Frontend](https://github.com/christossiap/Crypto_Wallet_ThesisAssignment_FrontEnd)**

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.3.4 |
| Security | Spring Security · JWT (HMAC-HS512) · BCrypt |
| Persistence | JPA / Hibernate · Spring Data JPA · MySQL 8 |
| Validation | Jakarta Bean Validation · Custom Constraint Validators |
| Email | JavaMailSender · Gmail SMTP |
| External API | CoinMarketCap REST API |
| Documentation | Swagger / OpenAPI (`/swagger-ui.html`) |
| Build | Maven |

---

## Features

**Authentication & Account Management**
- Registration with email verification (unique username + email enforced via custom validators)
- JWT login — tokens signed with HMAC-HS512, 24-hour expiry
- Change username, email, password; forgot username reminder; password reset via email code

**Portfolio & Transactions**
- BUY / SELL engine: validates available balance and coin quantity before executing
- Portfolio summary: current balance, all held coins, total portfolio value in €
- Transaction history with filtering by date range and type, pagination and sorting

**Cryptocurrency Data**
- Live price updates pulled from CoinMarketCap API
- Coin price history tracking over time
- Dynamic search with up to 9 simultaneous filter criteria (name, symbol, price range, 24h % change, date range, market cap) — built with Spring Data Specifications

**Watchlist & Notifications**
- Add / remove / clear a personal watchlist of coins
- Automatic TRANSACTION notifications on every BUY/SELL
- Notification types: TRANSACTION, ALERT, SYSTEM, PROMO
- Mark as read, delete individual notifications

---

## Architecture

The application follows a **3-tier layered architecture**:

```
REST Controllers  (webservices/)   ← thin, delegate to services, return DTOs
      ↓
Services          (services/)      ← all business logic
      ↓
Repositories      (repositories/)  ← Spring Data JPA + custom Specifications
      ↓
Domain Model      (models/)        ← 11 JPA entities
```

**Domain model (11 entities):**
`User` · `UserProfile` · `Portfolio` · `PortfolioItem` · `Transaction` · `CryptoCoin` · `CryptoCoinHistory` · `WatchList` · `Notification` · `Role`

Key relationships: `Portfolio`, `UserProfile`, and `WatchList` share their primary key with `User` via `@MapsId` — they are created atomically on registration.

---

## API Overview

| Controller | Base Path | Highlights |
|---|---|---|
| Auth | `/api` | Register, login, password/email/username flows |
| Profile | `/api/profile` | View and edit user profile |
| Portfolio | `/api/portfolio` | Summary, assets, add balance |
| Transactions | `/api/transactions` | BUY/SELL, history, filters, delete |
| Coins | `/api/coin` | Search (9 filters), pagination, coin detail |
| Coin History | `/api/coin-history` | Price history by coin name |
| Watchlist | `/api/watchlist` | Add, remove, clear |
| Notifications | `/api/notifications` | List, mark read, delete |
| Crypto API | `/api/crypto-api` | Trigger CoinMarketCap price refresh |

Full interactive documentation available at `http://localhost:8080/swagger-ui.html` when running locally.

---

## Running Locally

### Prerequisites
- Java 17+
- Maven
- MySQL 8 running on `localhost:3306`
- A CoinMarketCap API key (free tier works)
- A Gmail account with an [App Password] enabled

### 1. Clone and set up the database

```bash
git clone https://github.com/christossiap/Crypto_Wallet_ThesisAssignment_Backend.git
cd Crypto_Wallet_ThesisAssignment_Backend
```

Create the MySQL database and user:
```sql
CREATE DATABASE CryptoApplication;
CREATE USER 'your_username'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON CryptoApplication.* TO 'your_username'@'localhost';
```

### 2. Configure secrets

Create `src/main/resources/application-local.properties` (this file is gitignored):

```properties
db.username=your_db_username
db.password=your_db_password

jwt.secret=your_hs512_base64_key
jwt.exp=[XXXXX]ms

api.url=https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest
api.origins=http://localhost:3000

mail.username=your_gmail@gmail.com
mail.password=your_gmail_app_password
```

### 3. Run

```bash
./mvnw spring-boot:run -spring-boot.run.profiles=local
```

The API starts at `http://localhost:8080`.
Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## Project Structure

```
src/main/java/com/unipi/christossiap/crypto_wallet_thesisassignment/
├── webservices/          # REST Controllers
├── services/             # Business logic (+ auth/, email/)
├── repositories/         # JPA Repositories (+ specifications/)
├── models/               # JPA Entities (+ auth/, validators/)
├── DTOs/                 # Request/Response objects
├── enums/                # UserStatus, TransactionType, NotificationType
├── configuration/        # Security, JWT, CORS, Cache, Validation
└── settings/             # GlobalExceptionHandler + custom exceptions
```

---

## Known Limitations

- Role-based endpoint authorization is designed but not yet enforced (`WebSecurityConfig`)
- Financial fields use `Double` — a `BigDecimal` migration is planned for production accuracy
- Redis integration is a planned extension for current Spring Cache one.
- Docker also.

---

## Author

**Christos Siapatis** · [LinkedIn](https://www.linkedin.com/in/christos-siapatis/) · [GitHub](https://github.com/christossiap)

MSc in Informatics · University of Piraeus
