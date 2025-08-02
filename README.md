# Social Backend

This is the backend service for the [Social](https://github.com/dhruv-15-03/social) web application, a modern social media platform. The backend is built using Java and Spring Boot, providing robust APIs and real-time features for the frontend, which is live and visually rich.

## Features
- **User Authentication & Authorization**: Secure login, registration, and JWT-based session management.
- **Stories**: Users can post stories that automatically expire after 24 hours, similar to Instagram. Expired stories are cleaned up by a scheduled background task.
- **Posts, Comments, Likes**: Standard social media interactions, including posting, commenting, and liking.
- **Real-Time Chat**: WebSocket-powered messaging for instant communication.
- **Follow System**: Users can follow/unfollow others and view stories/posts from people they follow.
- **Notifications**: Real-time notifications for likes, comments, and messages.
- **Error Handling**: Custom exceptions and error responses for robust API behavior.
- **Prometheus Monitoring**: Integrated for metrics and health monitoring.
- **Docker Support**: Easily deployable using Docker and Docker Compose.

## Technologies Used
- **Java 17+**
- **Spring Boot**
- **Spring Data JPA** (with Hibernate)
- **Spring Security**
- **WebSocket** (Spring)
- **Maven**
- **Docker & Docker Compose**
- **Prometheus** (for monitoring)

## Project Structure
- `src/main/java/com/example/demo/`
  - `Classes/` - Core domain models (User, Story, Post, etc.)
  - `Controllers/` - REST API endpoints
  - `Implementation/` - Service logic (e.g., story expiration, chat, etc.)
  - `DataBase/` - JPA repositories
  - `Exception/` - Custom exception classes
  - `config/` - App configuration (JWT, WebSocket, etc.)
  - `Response/` - API response models
- `src/main/resources/` - Properties, templates, static files
- `Dockerfile`, `docker-compose.yml` - Containerization
- `prometheus.yml` - Monitoring config

## How It Works
- **Story Expiration**: When a user posts a story, it is saved with a timestamp. A scheduled background task runs every hour to delete stories older than 24 hours, ensuring stories are ephemeral.
- **Authentication**: Uses JWT tokens for stateless authentication. All sensitive endpoints are protected.
- **Real-Time Chat**: WebSocket endpoints allow users to send and receive messages instantly.
- **Database**: Uses Spring Data JPA for ORM and database operations.
- **Monitoring**: Prometheus scrapes metrics for health and performance.

## Setup & Running
1. **Clone the repository**
   ```sh
   git clone https://github.com/dhruv-15-03/Social-Backend.git
   cd Social-Backend/demo
   ```
2. **Configure environment**
   - Edit `data.env` and `src/main/resources/application.properties` as needed.
   - For Render deployment, set `KEEP_ALIVE_URL` to your deployed app URL (e.g., `https://your-app.onrender.com`)
3. **Build and run (Maven)**
   ```sh
   ./mvnw spring-boot:run
   ```
4. **Run with Docker**
   ```sh
   docker-compose up --build
   ```
5. **Access Prometheus**
   - Prometheus metrics available at `/actuator/prometheus` (if enabled)

## Anti-Sleep Configuration (For Free Hosting)
To prevent your app from sleeping on free hosting platforms like Render:

### Built-in Keep-Alive
The application includes an automatic keep-alive service that:
- Pings `/api/health` every 10 minutes
- Prevents the server from going idle
- Logs activity for monitoring

### External Monitoring (Recommended)
For additional reliability, set up external monitoring:

1. **UptimeRobot** (Free - recommended)
   - Sign up at [uptimerobot.com](https://uptimerobot.com)
   - Add monitor: `https://your-app.onrender.com/api/ping`
   - Set interval: 10 minutes
   - This ensures your app stays awake even if internal keep-alive fails

2. **Alternative Services**
   - Pingdom
   - StatusCake
   - Any cron job service

### Environment Variables for Deployment
```env
KEEP_ALIVE_URL=https://your-app.onrender.com
PORT=8080
DB_URL=your_database_url
DB_USER=your_database_user
DB_PASS=your_database_password
```

## API Documentation
- RESTful endpoints for all core features (users, stories, posts, chat, etc.)
- Error responses are standardized for easy frontend integration.

## Frontend
- The frontend is available at [dhruv-15-03/social](https://github.com/dhruv-15-03/social) and is live with images, UI, and all user-facing features.

## Contribution
Feel free to open issues or submit pull requests for improvements or new features!

## License
This project is licensed under the MIT License.

---
For more details, see the frontend repo and explore the live demo!
