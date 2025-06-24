-----

# EduAssist-Grade Microservice (vg-grade-english)

This microservice is designed to log and query changes made to student grades. It uses a hexagonal architecture, reactive programming with Spring WebFlux, and MongoDB as its database.

-----

## Project Purpose

This microservice acts as a **centralized logging system** for all modifications made to student grades. Its primary goal is to provide an immutable record of changes, enabling **auditing, historical tracking, and accountability** for grade adjustments within the educational platform. By meticulously logging each alteration, the system ensures data integrity and supports transparent grade management.

-----

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

## Setup Instructions

1.  **Ensure MongoDB is running** and accessible.
2.  **Verify the MongoDB connection string** in `src/main/resources/application.yml`. Update it if your MongoDB instance requires different credentials or a different URI.
3.  **Build the project** using Maven:
    ```bash
    mvn clean install
    ```
4.  **Run the application.** You can execute the `VgMsGradeManagementApplication.java` class from your IDE, or run the generated JAR file:
    ```bash
    java -jar target/vg-ms-grade-management-0.0.1-SNAPSHOT.jar
    ```
    The microservice will start on port `8080` by default.

-----

## How to Use the App

This microservice provides API endpoints to manage grade log entries.

  * To **create a new log entry**, you should send a `POST` request to `/grade-logs` with a JSON body representing the `GradeManagementLog`.
  * To **retrieve all log entries**, you should send a `GET` request to `/grade-logs`.
  * To **get a specific log entry by ID**, you should use the `GET` endpoint `/grade-logs/{id}`.
  * To **query logs for a specific student, grade, period, or teacher-course-classroom combination**, you should use the respective `GET` endpoints:
      * `/grade-logs/student/{studentId}`
      * `/grade-logs/grade/{gradeId}`
      * `/grade-logs/period/{periodId}`
      * `/grade-logs/teacher-courses-classroom/{teacherCoursesClassroom}`
  * To **delete a log entry**, you should send a `DELETE` request to `/grade-logs/{id}`.

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

## Future Plans

Several enhancements should be considered for future development:

  * **Implement comprehensive security:** You should integrate Spring Security with JWT for robust authentication and authorization.
  * **Add input validation:** You should use Bean Validation (`jakarta.validation`) to ensure the integrity and correctness of incoming data.
  * **Implement advanced querying capabilities:** Consider adding endpoints for filtering logs by date range, specific modified fields, or action types.
  * **Integrate with a notification system:** You should explore sending notifications (e.g., to an auditing dashboard or a separate service) when critical grade changes occur.
  * **Introduce caching:** For frequently accessed log data, you could implement a caching layer to improve performance.

-----

## Contributing

We welcome contributions to improve this microservice\!

  * **Fork the repository.**
  * **Create a new branch** for your feature or bug fix.
  * **Write clear, concise code** that adheres to the existing coding style.
  * **Ensure your changes are thoroughly tested.** You should add unit and integration tests for new features and bug fixes.
  * **Update the documentation** (this README) if your changes affect functionality or setup.
  * **Submit a pull request.** Provide a detailed description of your changes and why they are necessary.

-----

## Deployment Requirements

To deploy this microservice, you must have the following:

  * A **running MongoDB instance** that is accessible from the deployment environment. You need to ensure the connection string in `application.yml` is correctly configured for your production environment.
  * A **Java 17 Runtime Environment (JRE)** installed on the deployment server.
  * Enough **CPU and memory resources** to handle the expected load.
  * Access to **Maven** to build the project.

You need to ensure that **network configurations allow traffic** to and from the microservice's exposed port (default 8080).

-----

## Best Practices & Tips

  * **Error Handling:** While basic error handling is present, you should implement a global `ControllerAdvice` for consistent and robust error responses across the API. This will centralize error logic and improve maintainability.
  * **Logging:** You should configure a robust logging framework (e.g., Logback or Log4j2) to capture detailed application logs. This is crucial for debugging and monitoring in a production environment.
  * **Performance Tuning:** You should profile the application under load to identify and address any performance bottlenecks, especially concerning database interactions.
  * **Configuration Management:** You should externalize sensitive configurations (like database credentials) using environment variables or a dedicated configuration server (e.g., Spring Cloud Config) for production deployments.
  * **Security Best Practices:** Beyond authentication/authorization, you should consider protecting against common web vulnerabilities like SQL injection (though less relevant with MongoDB, still good practice), XSS, and CSRF.
  * **API Versioning:** For future changes, you should consider implementing API versioning (e.g., via URI paths like `/v1/grade-logs`) to manage breaking changes gracefully.

-----
