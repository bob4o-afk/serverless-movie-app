# Azure Serverless Movie Rating App

This Azure serverless application allows users to manage movie information, ratings, and reviews. It consists of several functions designed to handle different aspects of the movie database.

## Functions:

1. **Function 1: Add Movie**
   - **Trigger Type:** HTTP Trigger
   - **Description:** Accepts movie information (Title, Year, Genre, Description, Director, Actors) via HTTP request and saves it in the SQL database.

2. **Function 2: Add Rating**
   - **Trigger Type:** HTTP Trigger
   - **Description:** Accepts rating information for a movie (Title, Opinion, Rating, Date and time, Author) via HTTP request and stores it in a separate table associated with the movies.

3. **Function 3: Calculate Average Ratings**
   - **Trigger Type:** Periodic Trigger (Every day at 11:30 am)
   - **Description:** Calculates the average rating for each movie and saves it in the database as a new column (e.g., Average Rating).

4. **Function 4: Search Movies**
   - **Trigger Type:** HTTP Trigger
   - **Description:** Allows users to search for movies by name. Returns all movies matching the search criteria along with their information, ratings, average ratings, and reviews. If no search string is provided, returns all results.

## Setting Up:

1. **Azure Functions:**
   - Deploy each function to Azure Functions using the Azure Portal or Azure CLI.
   - Configure the function bindings and triggers accordingly.

2. **Azure SQL Database:**
   - Create a SQL database in Azure to store movie information, ratings, and reviews.
   - Set up appropriate tables to store movies, ratings, and reviews.

3. **Database Connection:**
   - Configure the connection string for the SQL database in each function's configuration.

4. **Dependencies:**
   - Ensure that any required dependencies/libraries are installed for each function.

## Usage:

1. **Add Movie:**
   - Send an HTTP POST request to Function 1 endpoint with movie information in the query parameters.

2. **Add Rating:**
   - Send an HTTP POST request to Function 2 endpoint with rating information in the query parameters.

3. **Calculate Average Ratings:**
   - This function runs automatically every day at 11:30 am.

4. **Search Movies:**
   - Send an HTTP GET request to Function 4 endpoint with optional search parameters in the query string.

## Example Requests:

1. **Add Movie:**
   ```http
   POST /api/addmovie?Title=Movie%20Title&Year=2022&Genre=Action&Description=A%20thrilling%20action-packed%20movie&Director=John%20Doe&Actors=Actor%201,Actor%202 HTTP/1.1
   Host: your-function-app.azurewebsites.net
   ```

2. **Add Rating:**
   ```http
   POST /api/addrating?Title=Movie%20Title&Opinion=Great%20movie!&Rating=9&DateTime=2024-03-23T12:00:00&Author=User123 HTTP/1.1
   Host: your-function-app.azurewebsites.net
   ```

3. **Search Movies:**
   ```http
   GET /api/searchmovies?query=Movie%20Title HTTP/1.1
   Host: your-function-app.azurewebsites.net
   ```

## Contributors:

- [bob4o-afk](https://github.com/bob4o-afk)

---

Feel free to adjust the examples or any other part of the README as needed.
