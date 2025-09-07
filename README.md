# 📚 Book Catalog Management System  

## 📌 Overview  
This project is an implementation of **Assignment 2** for COMP 249 – Object-Oriented Programming II at Concordia University (Fall 2024).  
It simulates a **Book Catalog Management System** that processes, validates, and organizes book records from multiple input files.  

The program is structured into **three sequential parts**:  
1. **Syntax Validation & File Partitioning** – Reads CSV files, checks for syntax errors, and partitions valid records by genre.  
2. **Semantic Validation & Serialization** – Validates book records further (ISBN, price, year) and serializes valid objects into binary files.  
3. **Interactive Navigation** – Deserializes binary files and provides an interactive menu for navigating book records.  

---

## ⚙️ Features  
- Reads multiple **CSV-formatted book files**  
- Detects and reports **syntax errors** (too many fields, missing fields, unknown genre, etc.)  
- Detects and reports **semantic errors** (invalid ISBN, invalid price, invalid year)  
- Uses **custom exception classes** for error handling  
- Creates **Book objects** and stores them in arrays  
- Serializes arrays of valid books into **binary files**  
- Provides an **interactive console menu** to navigate book catalogs by genre  

---

## 📂 Project Structure  
- `Main.java` – Entry point with `main()` method  
- `do_part1()` – Handles syntax validation and partitions books by genre  
- `do_part2()` – Handles semantic validation, creates `Book` objects, and serializes them  
- `do_part3()` – Loads serialized files and allows interactive book navigation  
- `Book.java` – Defines the `Book` class with attributes, constructors, getters/setters, `equals()`, and `toString()`  
- **Custom exception classes**:  
  - `TooManyFieldsException`  
  - `TooFewFieldsException`  
  - `MissingFieldException`  
  - `UnknownGenreException`  
  - `BadIsbn10Exception`  
  - `BadIsbn13Exception`  
  - `BadPriceException`  
  - `BadYearException`  

---

## 📥 Input Files  
- **Part1_input_file_names.txt** – Contains the list of input CSV files  
