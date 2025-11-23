# Online Shopping Application

A JavaFX-based e-commerce application with user authentication, product browsing, and shopping cart functionality.

## Prerequisites

- Java 21+ (you have Java 25.0.1 ✓)
- Python 3.x (for dependency downloader)
- JavaFX SDK 25.0.1 (included in project)

## Quick Start

### 1. Download Dependencies (First Time Only)

```powershell
python download-deps.py
```

This downloads the MySQL JDBC connector to the `lib/` directory.

### 2. Build & Run Application

```powershell
.\build-and-run.cmd
```

The application will compile and launch the JavaFX GUI.

## Project Structure

```
online-shopping/
├── src/main/java/app/
│   ├── Main.java                    # Application entry point
│   ├── controllers/                 # JavaFX controllers
│   │   ├── LoginController.java
│   │   ├── RegisterController.java
│   │   ├── ProductController.java
│   │   └── CartController.java
│   ├── dao/                         # Database access objects
│   │   ├── UserDAO.java
│   │   ├── ProductDAO.java
│   │   └── OrderDAO.java
│   ├── db/                          # Database connection
│   │   └── Database.java
│   └── models/                      # Data models
│       ├── User.java
│       ├── Product.java
│       ├── Order.java
│       └── OrderItem.java
├── src/main/resources/fxml/         # JavaFX FXML layouts
│   ├── login.fxml
│   ├── register.fxml
│   ├── product_list.fxml
│   └── cart.fxml
├── javafx-sdk-25.0.1/               # JavaFX SDK
├── lib/                             # External libraries (MySQL connector)
├── out/                             # Build output
├── pom.xml                          # Maven configuration
├── build-and-run.cmd                # Build & run script
└── download-deps.py                 # Dependency downloader

```

## Build Details

The `build-and-run.cmd` script:
1. Compiles all Java source files with JavaFX modules
2. Copies FXML resources to the output directory
3. Runs the application with proper module path configuration

## Database Setup

Before running, ensure your MySQL database is configured in `src/main/java/app/db/Database.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";
```

## IDE Integration (VS Code)

If you see "Missing mandatory Classpath entries" warnings in VS Code, it's safe to ignore—these are IDE warnings only. The application compiles and runs correctly.

To configure VS Code for better intellisense, install the Extension Pack for Java.

## Troubleshooting

### "The system cannot find the path specified"
- Ensure you're running from the project root directory
- Check that `javafx-sdk-25.0.1/` folder exists

### "Cannot find MySQL connector"
- Run `python download-deps.py` to download the MySQL JDBC JAR

### "Connection refused" when running
- Start your MySQL server
- Update database credentials in `Database.java`

## License

This is a sample e-commerce project for educational purposes.
