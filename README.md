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
Adding new features (e.g. loyalty system, live chat, map view)
See CONTRIBUTING.md for how to get started.

📬 Contact & Support
📧 Email: alisherdaminov135@gmail
👥 Telegram: t.me/mr_daminov
