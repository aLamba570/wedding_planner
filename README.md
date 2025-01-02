# Wedding Planner Platform

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![Version](https://img.shields.io/badge/version-1.0.0-blue)]()

A comprehensive wedding planning platform that connects couples with vendors and simplifies the wedding planning process. Built with Spring Boot, React.js, and MongoDB.

## 🚀 Features

### For Couples

- Wedding planning dashboard with timeline and checklist
- Vendor discovery and booking management
- Guest list and RSVP management
- Budget tracking tools
- Real-time planning progress tracking

### For Vendors

- Business profile management
- Booking and calendar management
- Analytics dashboard with revenue tracking
- Service portfolio showcase
- Customer review management

## 🛠️ Tech Stack

### Backend

- Spring Boot
- MongoDB
- JWT Authentication
- Razorpay Integration

### Frontend

- React.js
- Redux Toolkit
- Tailwind CSS
- Recharts for analytics
- React Hook Form

## 🔧 Installation

### Prerequisites

- Node.js
- Java 17
- MongoDB
- Maven/Gradle

### Backend Setup

```bash
# Clone the repository
git clone <repository-url>

# Navigate to backend directory
cd wedding-planner-backend

# Install dependencies
./mvnw install

# Run the application
./mvnw spring-boot:run
```

### Frontend Setup

```bash
# Navigate to frontend directory
cd wedding-planner-frontend

# Install dependencies
npm install

# Start the application
npm run dev
```

## 📝 Environment Variables

### Backend

```properties
spring.data.mongodb.uri=your_mongodb_uri
jwt.secret=your_jwt_secret
razorpay.key.id=your_razorpay_key
razorpay.key.secret=your_razorpay_secret
```

### Frontend

```env
VITE_API_URL=http://localhost:8080/api
VITE_RAZORPAY_KEY_ID=your_razorpay_key_id
```

## 🔐 API Authentication

The API uses JWT tokens for authentication. Include the token in your requests:

```javascript
headers: {
  'Authorization': 'Bearer your_jwt_token'
}
```

## 📱 Screenshots

![image](https://github.com/user-attachments/assets/4059ce0a-cc7f-4d28-82de-c75424a845b7)
![image](https://github.com/user-attachments/assets/04889bce-38d2-4a0f-9127-42eb6fb9deb2)
![image](https://github.com/user-attachments/assets/2e7c2c72-0aad-4723-aace-2194491cf358)




## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 👥 Contact

Your Name - [@your_twitter](https://twitter.com/your_username)

Project Link: [https://github.com/your_username/wedding-planner](https://github.com/your_username/wedding-planner)

## Acknowledgments

* Spring Boot Documentation
* React.js Documentation
* MongoDB Documentation
* Razorpay API Documentation
