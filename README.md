🆘 HELP.md – Get Started with Hotels.uz

Welcome to the official help guide for Hotels.uz – a full-featured hotel management and booking 
platform designed for both travelers and hotel owners.

🏨 What Is Hotels.uz?

Hotels.uz is an all-in-one digital hospitality system where:

🧑‍💼 Hotel Owners can:
Register and log in securely 🔐
Create, update, and manage hotel listings 🏨
Post announcements and advertisements 📢
Customize their profiles and service details ✏️
🧍‍♂️ Users/Travelers can:
Browse hotels and view details 📱
Book hotels with real-time availability 📆
Leave comments, and give likes/dislikes ❤️👎
Manage their profiles, bookings, and preferences 👤
🚕 Taxi Booking: Integrated transportation service allows users to book a taxi directly from the hotel page – for the full trip experience.
🛡️ Security & Roles

Hotels.uz supports JWT-based authentication for two distinct roles:

Role	Capabilities
User	Register, log in, book hotels, like/dislike, comment, manage personal profile
Hotel Owner	Register, log in, add/manage hotels, publish adverts, edit profile, view bookings
All endpoints are secured with role-based access control, managed via JWT tokens and secure session handling.

📚 How the System Works

🧩 Modules Overview
Module	Description
Auth Module	Manages login, registration, and JWT token handling
User Module	Manages user profiles, comments, likes/dislikes, bookings
Hotel Owner Module	Allows owners to create and manage hotel info, ads, profile
Hotel Module	Handles hotel search, details, availability, ratings
Taxi Module	Booking service for arranging hotel pickup/drop-off
Each module is independent, well-structured, and follows RESTful architecture principles.

⚙️ API Access Guide

🔑 Authentication Flow
Register via /api/auth/register (role = USER / OWNER)
Login via /api/auth/login → Receive JWT token
Use the JWT in Authorization: Bearer <token> header for all secured endpoints.
🔍 Sample Use Cases
📌 A User Wants to Book a Hotel:

Register/Login as a user
Search for hotels via /api/hotels/search?city=Tashkent
View hotel details /api/hotels/{id}
Book a room /api/bookings
Leave a comment /api/comments
Like/dislike /api/likes/{hotelId}
📢 A Hotel Owner Posts an Advert:

Register/Login as a hotel owner
Create a hotel /api/owners/hotels
Add an advert /api/owners/hotels/{id}/adverts
Update hotel details /api/owners/hotels/{id}
🚖 A User Books a Taxi:

Choose a hotel
Request taxi /api/taxi/request?hotelId=123&to=Airport
📦 Profile Management

Both users and hotel owners can:

Update personal information
Upload photos, contact details, and preferences
Delete account or profile data if needed
All through secure and authorized endpoints.

🧪 Testing the API

You can test the APIs using:

✅ Postman collections (available in /docs/postman/)
🌐 Swagger UI (if enabled at /swagger-ui.html)

📌 Troubleshooting
Problem	Solution
❌ 401 Unauthorized	Make sure you're sending the correct JWT token
🧑‍🤝‍🧑 Role not recognized	Confirm correct role during registration (USER / OWNER)
🏨 Cannot book hotel	Hotel may be fully booked or unavailable on selected dates
🚕 Taxi request failed	Taxi service may be disabled or missing destination
Still stuck? Open an issue on GitHub or check the logs for more info.

👥 Want to Contribute?

We welcome contributions! You can help by:

Improving API design
Refactoring or optimizing logic
Enhancing documentation

📬 Contact & Support
📧 Email: alisherdaminov135@gmail
👥 Telegram: t.me/mr_daminov



![Image](https://github.com/user-attachments/assets/1d3b9d59-dad0-4766-af90-d1c99599433f)
![Image](https://github.com/user-attachments/assets/af7c9cd7-dcf6-45b0-b38d-03a7b5f9561f)
![Image](https://github.com/user-attachments/assets/9767f1d7-b9af-4344-a494-9d9294bbf6cb)
![Image](https://github.com/user-attachments/assets/1ed0f8fb-2ecb-479d-a4fc-9469df7450f5)
![Image](https://github.com/user-attachments/assets/498e8e61-fa4c-4d85-8cbd-388aa0990140)
![Image](https://github.com/user-attachments/assets/54ad3eae-8372-4528-a9b0-5327efdc37ed)
![Image](https://github.com/user-attachments/assets/1092dff4-9ee2-4f85-bcbf-ccce8ba433ea)
![Image](https://github.com/user-attachments/assets/1a4465d2-9314-45f6-badf-6e981073b80d)
![Image](https://github.com/user-attachments/assets/c23be90d-b271-4013-b833-0e035d18829c)
![Image](https://github.com/user-attachments/assets/4cada7a3-eaec-4936-af77-64b5e25715cb)
![Image](https://github.com/user-attachments/assets/586209c0-14f1-4430-b364-9af144a0aede)
![Image](https://github.com/user-attachments/assets/fdce8e38-b9e1-4258-bc5a-30f3fa8ca130)
![Image](https://github.com/user-attachments/assets/0d590747-75fb-4865-a6f8-8517c01c4793)
![Image](https://github.com/user-attachments/assets/46b08dfe-fd0f-4ff5-91ac-49c1d2d14913)
![Image](https://github.com/user-attachments/assets/b92ebe78-abdc-4b80-9ccb-847e03e292cc)
![Image](https://github.com/user-attachments/assets/2c257f9f-ce42-4825-9082-39b9949bcfb7)
