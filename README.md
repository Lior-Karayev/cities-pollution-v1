# Cities Pollution Analytics Dashboard

A professional, JavaFX-based desktop application designed to analyze and visualize global air and water pollution data.

This project evolved from a command-line parser into a robust **Model-View-Controller (MVC)** application. It features a responsive graphical user interface, dynamic SQL querying, and heavily utilizes modern Java multi-threading techniques to ensure smooth performance when handling large datasets.

## 🚀 Key Features

* 📊 **Interactive Dashboard:** Visualizes the relationship between Air Quality and Water Pollution using JavaFX `ScatterChart`s. Includes dynamic views to group data by City, Region, or Country Average.
* 🔍 **Smart Omni-Search:** A highly optimized search interface featuring a **300ms debounce timer**. It constructs dynamic SQL queries on the fly, allowing users to instantly filter by text (City, Country, Region) and numerical ranges without freezing the application.
* ⚡ **Multi-threaded Performance:** * **Non-Blocking UI:** Uses `CompletableFuture` to run heavy database queries on background threads, safely merging results back to the JavaFX Application Thread via `Platform.runLater()`.
    * **Parallel Processing:** Utilizes `parallelStream()` across multiple CPU cores to rapidly calculate regional and national pollution averages.
* 🏗️ **Strict MVC Architecture:** A clean separation of concerns dividing the User Interface (View), Business Rules (Logic), Data Access Objects (Data), and Data Transfer Objects (Model).

## 📂 Project Structure

The project is organized into strictly separated packages:

* **`app`**: The application entry point (`Main.java`, `Launcher.java`).
* **`view`**: The JavaFX Controllers (`MainController`, `DashboardController`, `AllReportsController`) handling UI events and thread-safe updates.
* **`resources/view`**: The FXML layout files defining the visual structure.
* **`logic`**: The Service Layer. Contains `PollutionManager` (routing) and `StatisticsAggregator` (parallel data processing).
* **`data`**: The Persistence Layer. Contains `MySQLPollutionDAO` for secure, prepared SQL execution, and `DatabaseConnector`. *(Note: Includes `CsvDataReader` to fulfill raw File I/O processing requirements).*
* **`model`**: Contains the `PollutionReportModel` and the `PollutionFilter` builder.

---

## 🛠️ Setup & Usage

### 1. Prerequisites
* Java JDK 21+
* JavaFX SDK (or configured via Maven/Gradle dependencies)
* MySQL Server (Version 8.0+)

### 2. Database Setup (Required)
The graphical interface relies on the MySQL database to function.

1.  **Database Creation:** Execute your `database_setup.sql` script (or create a schema named `cities_pollution_db` and import your `data.csv` into a `cities_pollution` table). Ensure headers map correctly to `City`, `"Region"`, `"Country"`, `"AirQuality"`, and `"WaterPollution"`.
2.  **Configuration:** Create a file named `config.properties` in the **root directory** of the project and add your MySQL credentials:
    ```properties
    db.url=jdbc:mysql://localhost:3306/cities_pollution_db
    db.user=your_username
    db.password=your_password
    ```

### 3. Running the Application
1.  Run the `main` method in `app.Launcher` (using a separate Launcher class prevents JavaFX module-path startup errors).
2.  Click **"Connect MySQL"** on the sidebar to establish the connection and load the initial dashboard.
3.  Navigate between the **Dashboard** (for visual analytics) and **All Reports** (for the searchable data table).

---

## 🛡️ Design & Security Choices

* **SQL Injection Prevention:** All dynamic queries in the `MySQLPollutionDAO` use `PreparedStatement` with object mapping to strictly sanitize user input.
* **UI Debouncing:** Network and database spam are prevented by utilizing a JavaFX `PauseTransition` timer on the search inputs.
* **Credential Security:** Database URLs, usernames, and passwords are loaded from an external `config.properties` file which should be added to `.gitignore`.