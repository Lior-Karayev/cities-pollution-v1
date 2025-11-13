# 🏙️ Cities Pollution (V1)
Welcome to the **Cities Pollution** project! This is a Java application for a "Systems Programming" course, designed to analyze data on air pollution and water quality in cities around the world.

## 🎯 Project Goal

The ultimate goal of this application is to read, process, and display pollution data from a MySQL database. This will allow for complex queries and analysis.

## 📈 Current Status

This is the very first version of the project. The current functionality is focused on the initial data-handling phase: reading the data directly from a CSV file.

### The application currently:

Boots up from a Main class.

Utilizes a utility class to read all lines from a CSV file into a ```List<String>.```

## 📦 Project Structure

The project is organized into the following packages:

* ```com.pollution.app```

  * ```Main.java```: The main entry point for the application. It coordinates the data reading and will eventually orchestrate the database interactions.

* ```com.pollution.data```

    * ```CsvDataReader.java```: A utility class responsible for all file I/O.
        * It provides a static method ```getCsvFileLines()``` to read the entire CSV file.
        * The constructor is private to prevent instantiation, as it's a static utility class.
* **data.csv**: CSV file containing air pollution and water quality in cities around the world.

## 🚀 Future Goals

### The next major steps in this project are:
1. **Parse Data**: Add logic to parse the List<String> into meaningful Java objects (e.g., PollutionRecord).
2. **Database Integration**: Connect the application to a MySQL database.
3. **Analysis**: Build features to query and analyze the data from the database.

## ⚙️ Getting Started

### To run the project:
1. Clone the repository.
2. Ensure you have Java 21 (or as specified in pom.xml) installed.
3. Open the project in your favorite IDE (like IntelliJ).
4. Run the Main class located in com.pollution.app.