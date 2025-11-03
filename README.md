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
```
git clone https://github.com/seu-usuario/url-shortener.git
cd url-shortener
```

2. Create PostgreSQL database
```
CREATE DATABASE urlshortener;
```

3. Configure database connection in `application.properties`:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortener
spring.datasource.username=postgres
spring.datasource.password=your_password
```

4. Run the application
```
mvn spring-boot:run
```
Server starts at `http://localhost:8080`

## API Endpoints

### Create Short URL
POST /api/shorten
Content-Type: application/json

{
"url": "https://www.example.com/very/long/url/path"
}

Response (201):
{
"shortUrl": "http://localhost:8080/1",
"originalUrl": "https://www.example.com/very/long/url/path",
"clickCount": 0
}


### Access Short URL
GET /{key}

Redirects to original URL (302 Found)


### Get URL Statistics
GET /api/stats/{key}

Response (200):
{
"shortUrl": "http://localhost:8080/1",
"originalUrl": "https://www.example.com/very/long/url/path",
"clickCount": 5
}


## Project Structure
src/main/java/com/urlshortener/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
├── exception/
└── util/