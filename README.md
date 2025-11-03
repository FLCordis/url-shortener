# URL Shortener API

A RESTful API for creating and managing short URLs, built with Spring Boot and PostgreSQL.

## Features

- 🔗 **URL Shortening**: Convert long URLs to compact short links using Base62 encoding
- 📊 **Click Analytics**: Track the number of times each short URL is accessed
- 🔄 **Smart Deduplication**: Automatically detects and reuses short URLs for identical long links
- 🚀 **Fast Redirects**: Efficient HTTP 302 redirects to original URLs
- ✅ **Input Validation**: Comprehensive URL format and content validation
- 🛡️ **Centralized Error Handling**: Standardized JSON error responses

## Tech Stack

- **Backend**: Spring Boot 3.x
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Encoding**: Base62 custom implementation
- **Build Tool**: Maven

## Quick Start

### Prerequisites

- Java 17+
- PostgreSQL 12+
- Maven 3.8+

### Setup

1. Clone the repository

```bash
git clone https://github.com/FLCordis/url-shortener.git
cd url-shortener
```

2. Create PostgreSQL database

```sql
CREATE DATABASE urlshortener;
```

3. Configure database connection in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortener
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
app.base-url=http://localhost:8080
```

4. Run the application

```bash
mvn spring-boot:run
```

Server starts at `http://localhost:8080`

## API Endpoints

### Create Short URL

**Request:**
```bash
POST /api/shorten
Content-Type: application/json

{
  "url": "https://www.example.com/very/long/url/path"
}
```

**Response (201 Created):**
```json
{
  "shortUrl": "http://localhost:8080/1",
  "originalUrl": "https://www.example.com/very/long/url/path",
  "clickCount": 0
}
```

### Access Short URL

**Request:**
```bash
GET /{key}
```

**Response:** Redirects to original URL (HTTP 302 Found)

### Get URL Statistics

**Request:**
```bash
GET /api/stats/{key}
```

**Response (200 OK):**
```json
{
  "shortUrl": "http://localhost:8080/1",
  "originalUrl": "https://www.example.com/very/long/url/path",
  "clickCount": 5
}
```

## Project Structure

```
src/main/java/com/urlshortener/
├── controller/        # REST API endpoints
├── service/           # Business logic
├── repository/        # Database access layer
├── model/             # JPA entities
├── dto/               # Request/Response objects
├── exception/         # Error handling
├── util/              # Utility classes (Base62Encoder)
└── UrlShortenerApplication.java
```

## How Base62 Works

Base62 encoding converts sequential database IDs into compact alphanumeric strings using 62 characters (0-9, a-z, A-Z). This approach ensures:
- **No collisions**: Each URL gets a unique, deterministic code
- **Compact representation**: ID `1115710` becomes `2TX` in Base62
- **Scalability**: A 7-character Base62 string can represent 3.5 trillion unique values

## Example URL Generation Sequence

| Request # | ID | Base62 Code | Short URL |
|-----------|-----|------------|-----------|
| 1st | 1 | 1 | `http://localhost:8080/1` |
| 2nd | 2 | 2 | `http://localhost:8080/2` |
| 10th | 10 | a | `http://localhost:8080/a` |
| 62nd | 62 | 10 | `http://localhost:8080/10` |
| 1000th | 1000 | rs | `http://localhost:8080/rs` |

**Author**: Flavio Cordis  
**Created**: November 2025