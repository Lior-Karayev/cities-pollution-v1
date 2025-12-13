# Cities Pollution Analysis System

A Java-based Systems Programming project designed to analyze global air and water pollution data.

This project implements a **hybrid data analysis architecture**, capable of processing data from two sources:
1.  **CSV Analysis (Active):** Uses Java NIO and Streams to parse and aggregate data directly from raw files.
2.  **Database Analysis (Ready):** A fully implemented DAO layer using JDBC and MySQL for advanced querying (prepared for future integration).

## 🚀 Features

* **Data Parsing:** Efficiently reads and processes `data.csv` using `java.nio`.
* **Aggregation Logic:** Algorithms to identify cities with **Maximum** and **Minimum** pollution levels for both Air and Water categories.
* **Layered Architecture:** Follows a strict **MVC-based design** (Model, Data, Logic, View) to ensure separation of concerns.
* **Scalability:** Includes a complete SQL data access layer (`MySQLPollutionDAO`) capable of complex `UNION` queries.

## 📂 Project Structure

The project is organized into strictly separated packages:

### `app`
* **`Main`**: The entry point. Currently configured to execute **CSV-based analysis** and print the results to the console.

### `data` (Persistence Layer)
* **`CsvDataReader`**: **(Core for Assignment 1)** Handles file I/O, parsing CSV lines, and calculating min/max pollution metrics using efficient loops and string splitting.
* **`MySQLPollutionDAO`**: Implements `IPollutionDAO` using JDBC. Handles complex SQL queries and secure database connections.
* **`DatabaseConnector`**: Manages DB credentials via a secure `config.properties` file.

### `logic` (Service Layer)
* **`PollutionManager`**: The bridge between the raw data and the application. It validates input and routes requests to either the CSV reader or the SQL DAO.

### `model`
* **`PollutionReportModel`**: An immutable Data Transfer Object (DTO) representing a single pollution record.

---

## 🛠️ Setup & Usage

### 1. Prerequisites
* Java JDK 21
* Maven

### 2. Running the Analysis (CSV Mode)
The application is currently set to **CSV Mode** by default.

1.  Ensure the file `data.csv` is present in the **root directory** of the project.
2.  Run the `main` method in `app.Main`.
3.  The application will output:
    * Cities with Max/Min Water Pollution.
    * Cities with Max/Min Air Pollution.

### 3. Advanced Setup (SQL Mode - Optional)
The project contains a fully functional Database layer. To enable it:

1.  **Database Creation:** Run the included `database_setup.sql` script to create the `cities_pollution_db` and the table schema (with quoted identifiers).
2.  **Import Data:** Import `data.csv` into the `cities_pollution` table using your SQL IDE (ensure headers map to `City`, `"Region"`, `"Country"`, etc.).
3.  **Configuration:** Rename `config.properties.example` to `config.properties` and add your MySQL credentials.
4.  **Enable Code:** Uncomment the SQL methods in `app.Main`.

---

## 🛡️ Design Choices

* **Exception Handling:** The CSV reader handles `FileNotFoundException` gracefully, logging errors without crashing the main thread.
* **Security:** Database credentials are never hardcoded; they are loaded from an external file ignored by Git.
* **Interface Segregation:** `IPollutionDAO` defines a contract, allowing the app to switch between data sources (Mock, SQL, File) easily.