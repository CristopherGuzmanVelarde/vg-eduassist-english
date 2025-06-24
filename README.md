-----

# EduAssist Grade Microservice (vg-eduassist-english)

This microservice is designed to log and query changes made to student grades. It uses a hexagonal architecture, reactive programming with Spring WebFlux, and MongoDB as its database.

## Technologies Used

  * Java 17
  * Spring Boot 3.x
  * Spring WebFlux (for reactive programming)
  * Spring Data MongoDB Reactive
  * Lombok
  * Maven (for dependency management)
  * MongoDB

-----

## Hexagonal Architecture

The project follows the principles of hexagonal architecture (also known as Ports and Adapters) to separate concerns and improve code maintainability and testability.

  * **Domain (`domain`):** Contains the core business logic and entity models (POJOs).
      * `model`: Classes representing domain entities like `GradeManagementLog`, `ModifiedData`, `ModifiedField`, `ContextMetadata`.
      * `repository`: Interfaces defining persistence operations for domain entities (e.g., `GradeManagementLogRepository`).
  * **Application (`application`):** Orchestrates use cases and application logic.
      * `service`: Interfaces defining application services (e.g., `GradeManagementLogService`).
      * `service/impl`: Implementations of application services (e.g., `GradeManagementLogServiceImpl`).
  * **Infrastructure (`infrastructure`):** Contains adapters for external technologies.
      * `adapter/rest`: REST controllers exposing the API (e.g., `GradeManagementLogController`).
      * Database configuration (MongoDB) is managed via `application.yml` and Spring Data MongoDB Reactive.

-----

## Project Structure

```
.vg-ms-grade-management
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── pe
    │   │       └── edu
    │   │           └── vallegrande
    │   │               └── vg_ms_grade_management
    │   │                   ├── VgMsGradeManagementApplication.java (Main Spring Boot class)
    │   │                   ├── application
    │   │                   │   ├── service
    │   │                   │   │   ├── GradeManagementLogService.java
    │   │                   │   │   └── impl
    │   │                   │   │       └── GradeManagementLogServiceImpl.java
    │   │                   ├── domain
    │   │                   │   ├── model
    │   │                   │   │   ├── ContextMetadata.java
    │   │                   │   │   ├── GradeManagementLog.java
    │   │                   │   │   ├── ModifiedData.java
    │   │                   │   │   └── ModifiedField.java
    │   │                   │   └── repository
    │   │                   │       └── GradeManagementLogRepository.java
    │   │                   └── infrastructure
    │   │                       └── adapter
    │   │                           └── rest
    │   │                               └── GradeManagementLogController.java
    │   └── resources
    │       └── application.yml (Application and MongoDB configuration)
    └── test
        └── java
            └── pe
                └── edu
                    └── vallegrande
                        └── vg_ms_grade_management
                            └── VgMsGradeManagementApplicationTests.java
```

-----

## Configuration

1.  **MongoDB:**
    Ensure you have a MongoDB instance running.
    Connection configuration is located in `src/main/resources/application.yml`.
    By default, it connects to `mongodb+srv://cristopherguzman:orTIWL10NmFAo3S5@cluster0.o8sc9.mongodb.net/AS231S5_PRS2?retryWrites=true&w=majority&appName=Cluster0` and uses the database `AS231S5_PRS2`.

    ```yaml
    spring:
      application:
        name: vg-ms-grade-management
      data:
        mongodb:
          uri: mongodb+srv://cristopherguzman:orTIWL10NmFAo3S5@cluster0.o8sc9.mongodb.net/AS231S5_PRS2?retryWrites=true&w=majority&appName=Cluster0
          database: AS231S5_PRS2
          # If you have different credentials for this URI, configure them here:
          # username: your_username
          # password: your_password
          # authentication-database: admin
    ```

    You can modify the connection URI according to your MongoDB setup. If your database requires authentication, ensure credentials are included in the URI or configure the `spring.data.mongodb.username`, `spring.data.mongodb.password`, and `spring.data.mongodb.authentication-database` properties.

-----

## How to Run

1.  **Clone the repository (if applicable).**

2.  **Ensure MongoDB is running and accessible.**

3.  **Build the project using Maven:**

    ```bash
    mvn clean install
    ```

4.  **Run the application:**
    You can run the application from your IDE (e.g., IntelliJ IDEA, Eclipse) by importing the Maven project and executing the `VgMsGradeManagementApplication.java` class.
    Alternatively, you can run the generated JAR file (after `mvn clean install`):

    ```bash
    java -jar target/vg-ms-grade-management-0.0.1-SNAPSHOT.jar
    ```

    By default, the microservice will start on port `8080` (configurable in `application.yml` with `server.port`).

-----

## API Endpoints

The API is exposed under the base path `/grade-logs`.

### `POST /grade-logs`

Creates a new grade log entry.

  * **Request Body:** `GradeManagementLog` (JSON)

    ```json
    {
        "gradeId": 456,
        "studentId": 1,
        "teacherCoursesClassroom": 1,
        "periodId": 1,
        "actionType": "ACTUALIZACIÓN",
        "modifiedData": {
            "previousGrade": 16.00,
            "newGrade": 16.50,
            "modifiedFields": [
                {
                    "field": "participación",
                    "oldValue": 18.00,
                    "newValue": 18.50,
                    "impact": "Aumento del 2.78% en evaluación continua"
                },
                {
                    "field": "feedback",
                    "oldValue": "Reforzar multiplicación",
                    "newValue": "Dominio de multiplicación básica",
                    "impact": "Mejora en objetivos de aprendizaje MEP-2025"
                }
            ],
            "contextMetadata": {
                "curricularComponent": "Matemáticas",
                "learningUnit": "Operaciones básicas",
                "evaluationCriteria": "R.M.2.1.1"
            }
        },
        "changeReason": "Revisión de criterios de evaluación participativa (Orden Directiva 045-2025-MINEDU)",
        "modifiedBy": 3
    }
    ```

  * **Response:** `201 Created` with the created `GradeManagementLog` (including the `id` generated by MongoDB and `modifiedAt`).

### `GET /grade-logs`

Retrieves all grade log entries.

  * **Response:** `200 OK` with an array of `GradeManagementLog`.

### `GET /grade-logs/{id}`

Retrieves a grade log entry by its ID (the `id` generated by MongoDB).

  * **Path Variable:** `id` (String) - The log ID.
  * **Response:**
      * `200 OK` with the found `GradeManagementLog`.
      * `404 Not Found` if the log does not exist.

### `GET /grade-logs/student/{studentId}`

Retrieves all log entries for a specific student.

  * **Path Variable:** `studentId` (Long) - The student's ID.
  * **Response:** `200 OK` with an array of `GradeManagementLog`.

### `GET /grade-logs/grade/{gradeId}`

Retrieves all log entries for a specific grade.

  * **Path Variable:** `gradeId` (Long) - The grade's ID.
  * **Response:** `200 OK` with an array of `GradeManagementLog`.

### `GET /grade-logs/period/{periodId}`

Retrieves all log entries for a specific academic period.

  * **Path Variable:** `periodId` (Long) - The period's ID.
  * **Response:** `200 OK` with an array of `GradeManagementLog`.

### `GET /grade-logs/teacher-courses-classroom/{teacherCoursesClassroom}`

Retrieves all log entries for a specific teacher, course, and classroom combination.

  * **Path Variable:** `teacherCoursesClassroom` (Long) - The combination ID.
  * **Response:** `200 OK` with an array of `GradeManagementLog`.

### `DELETE /grade-logs/{id}`

Deletes a grade log entry by its ID.

  * **Path Variable:** `id` (String) - The ID of the log to delete.
  * **Response:**
      * `204 No Content` if the deletion is successful.
      * `404 Not Found` if the log does not exist.

-----

## Example Data Model (`GradeManagementLog`)

The main structure of the stored data is as follows:

  * `id` (String, generated by MongoDB): Unique log identifier.
  * `gradeId` (Long): ID of the affected grade.
  * `studentId` (Long): Student's ID.
  * `teacherCoursesClassroom` (Long): ID of the teacher-course-classroom combination.
  * `periodId` (Long): ID of the academic period.
  * `actionType` (String): Type of action (e.g., "CREATION", "UPDATE", "DELETION").
  * `modifiedData` (Object): Object containing modification details.
      * `previousGrade` (Double): Previous grade (if applicable).
      * `newGrade` (Double): New grade (if applicable).
      * `modifiedFields` (List\<Object\>): List of specific fields modified.
          * `field` (String): Field name.
          * `oldValue` (Object): Old value.
          * `newValue` (Object): New value.
          * `impact` (String): Description of the change's impact.
      * `contextMetadata` (Object): Metadata about the modification context.
          * `curricularComponent` (String): Curricular component.
          * `learningUnit` (String): Learning unit.
          * `evaluationCriteria` (String): Evaluation criteria.
  * `changeReason` (String): Reason for the change.
  * `modifiedBy` (Long): ID of the user who made the modification.
  * `modifiedAt` (LocalDateTime): Date and time of modification (automatically generated).

-----

## Additional Considerations

  * **Error Handling:** The controller implements basic error handling (e.g., 404 Not Found). This could be extended with a global `ControllerAdvice` for more robust handling.
  * **Security:** Security has not been implemented in this version. For a production environment, authentication and authorization mechanisms (e.g., Spring Security with JWT) should be added.
  * **Validation:** Input data validations could be added using Bean Validation (`javax.validation` or `jakarta.validation`).
  * **Testing:** It is recommended to add unit and integration tests to ensure code quality.

-----
