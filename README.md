# 🛒 Online Shopping Application

A modern JavaFX-based e-commerce application with user authentication, product browsing, shopping cart functionality, and order management. Features a beautiful dark theme UI with glassmorphism effects.

![Java](https://img.shields.io/badge/Java-25.0.1-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-25.0.1-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Maven](https://img.shields.io/badge/Maven-3.9.5-red)

## ✨ Features

- 🔐 **User Authentication** - Secure login and registration system
- 📦 **Product Catalog** - Browse products with real-time stock information
- 🛍️ **Shopping Cart** - Add/remove items with live total calculation
- 💳 **Order Management** - Complete checkout with database persistence
- 🎨 **Modern UI** - Dark theme with glassmorphism and smooth animations
- 💾 **Database Integration** - Full MySQL integration with order tracking

## 🎨 UI Features

- **Premium Dark Theme** - Gradient backgrounds (#1a1a2e → #16213e)
- **Glassmorphism Effects** - Semi-transparent elements with blur
- **Vibrant Accents** - Purple highlights (#533483)
- **Smooth Animations** - Hover effects and transitions
- **Responsive Design** - Adaptive layouts with proper spacing
- **Emoji Icons** - Modern visual indicators (🛒📦🛍️)

## 📋 Prerequisites

- **Java 21+** (tested with Java 25.0.1 ✓)
- **MySQL 8.0+** (for database)
- **Maven 3.9+** (included via wrapper)
- **Python 3.x** (optional, for dependency downloader)

## 🚀 Quick Start

### 1. Setup Database

First, create the database and import the schema:

```bash
# Login to MySQL
mysql -u root -p

# Create database and import schema
source sql/sample-data.sql
```

Or manually:
```sql
CREATE DATABASE IF NOT EXISTS amazonapp;
USE amazonapp;
-- Then run the contents of sql/sample-data.sql
```

### 2. Configure Database Connection

Update your MySQL credentials in `src/main/java/app/db/Database.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/amazonapp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASS = "YOUR_PASSWORD_HERE"; // ⚠️ Update this!
```

### 3. Run the Application

**Option A: Using Maven (Recommended)**
```powershell
.\mvnw.cmd clean javafx:run
```

**Option B: Using Build Script**
```powershell
.\build-and-run.cmd
```

The application will compile and launch with the modern dark theme UI! 🎉

## 📁 Project Structure

```
online-shopping/
├── src/main/
│   ├── java/app/
│   │   ├── Main.java                    # Application entry point
│   │   ├── controllers/                 # JavaFX controllers
│   │   │   ├── LoginController.java     # Login page logic
│   │   │   ├── RegisterController.java  # Registration logic
│   │   │   ├── ProductController.java   # Product list & cart
│   │   │   └── CartController.java      # Shopping cart & checkout
│   │   ├── dao/                         # Database access layer
│   │   │   ├── UserDAO.java            # User operations
│   │   │   ├── ProductDAO.java         # Product queries
│   │   │   └── OrderDAO.java           # Order management
│   │   ├── db/                          # Database connection
│   │   │   └── Database.java           # Connection manager
│   │   └── models/                      # Data models
│   │       ├── User.java               # User entity
│   │       ├── Product.java            # Product entity
│   │       ├── Order.java              # Order entity
│   │       └── OrderItem.java          # Order item entity
│   └── resources/
│       ├── fxml/                        # JavaFX layouts
│       │   ├── login.fxml              # Login page
│       │   ├── register.fxml           # Registration page
│       │   ├── product_list.fxml       # Product catalog
│       │   └── cart.fxml               # Shopping cart
│       └── styles/
│           └── app.css                 # Modern dark theme styles
├── sql/
│   └── sample-data.sql                 # Database schema & sample data
├── javafx-sdk-25.0.1/                  # JavaFX SDK
├── lib/                                # External libraries
├── pom.xml                             # Maven configuration
├── mvnw.cmd                            # Maven wrapper (Windows)
└── build-and-run.cmd                   # Build & run script

```

## 🗄️ Database Schema

### Tables

- **Users** - User accounts with authentication
- **Products** - Product catalog with stock management
- **Orders** - Customer orders with status tracking
- **Order_Items** - Individual items in each order

### Sample Queries

**View Recent Orders:**
```sql
SELECT * FROM Orders ORDER BY order_id DESC LIMIT 5;
```

**View Order Details:**
```sql
SELECT 
    o.order_id,
    o.order_date,
    u.username,
    p.name as product_name,
    oi.quantity,
    oi.price_at_purchase,
    o.total,
    o.status
FROM Orders o
JOIN Users u ON o.user_id = u.user_id
JOIN Order_Items oi ON o.order_id = oi.order_id
JOIN Products p ON oi.product_id = p.product_id
ORDER BY o.order_id DESC;
```

## 🎯 Usage

1. **Login** - Use credentials from the Users table (default password hash in sample data)
2. **Browse Products** - View available products with prices and stock
3. **Add to Cart** - Select products and add them to your shopping cart
4. **Checkout** - Complete purchase and save order to database
5. **View Orders** - Check MySQL database for order history

### Default Test Users

The sample data includes test users with hashed passwords. For testing, you can add a simple user:

```sql
INSERT INTO Users (username, password, email, role) 
VALUES ('test', 'test123', 'test@example.com', 'customer');
```

## 🛠️ Development

### Build with Maven
```bash
.\mvnw.cmd clean compile
```

### Run Tests
```bash
.\mvnw.cmd test
```

### Package Application
```bash
.\mvnw.cmd clean package
```

## 🐛 Troubleshooting

### Database Connection Issues

**Error: Access Denied**
```
Solution: Update password in src/main/java/app/db/Database.java
```

**Error: Database not found**
```bash
# Create database
mysql -u root -p
CREATE DATABASE amazonapp;
source sql/sample-data.sql
```

### Maven Wrapper Issues

**Error: mvnw.cmd not working**
```bash
# The wrapper is included and fixed - just run:
.\mvnw.cmd clean javafx:run
```

### JavaFX Issues

**Error: JavaFX modules not found**
```
Solution: The project uses Maven dependencies - no manual JavaFX setup needed
```

### Order Items Not Saving

Check console output for debug messages:
```
=== CHECKOUT DEBUG ===
User ID: 1
Total: ₹2598.00
Items in cart: 3
...
=== CHECKOUT SUCCESS ===
```

See `ORDER_TROUBLESHOOTING.md` for detailed debugging steps.

## 📚 Additional Documentation

- `UI_MODERNIZATION_SUMMARY.md` - Details on UI improvements
- `CART_FIX_GUIDE.md` - Cart display fixes
- `ORDER_TROUBLESHOOTING.md` - Order tracking debugging

## 🎨 UI Screenshots

The application features:
- Modern dark gradient backgrounds
- Glassmorphism card effects
- Smooth hover animations
- Purple accent colors
- Emoji-enhanced UI elements
- Responsive table views
- Custom styled buttons and inputs

## 🔧 Technology Stack

- **Frontend**: JavaFX 25.0.1
- **Backend**: Java 25.0.1
- **Database**: MySQL 8.0
- **Build Tool**: Maven 3.9.5
- **Styling**: CSS with custom dark theme

## 📝 License

This is a sample e-commerce project for educational purposes.

## 🤝 Contributing

This is an educational project. Feel free to fork and modify for your learning!

---

**Made with ❤️ using JavaFX and MySQL**
