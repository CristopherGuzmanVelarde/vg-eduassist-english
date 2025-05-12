package pe.edu.vallegrande.vg_ms_grade_management.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.GradeManagementLog;
import reactor.core.publisher.Flux;

public interface GradeManagementLogRepository extends ReactiveMongoRepository<GradeManagementLog, String> {

    /**
     * Busca registros de log por ID de estudiante.
     * @param studentId ID del estudiante.
     * @return Un Flux de GradeManagementLog.
     */
    Flux<GradeManagementLog> findByStudentId(Long studentId);

    /**
     * Busca registros de log por ID de calificación.
     * @param gradeId ID de la calificación.
     * @return Un Flux de GradeManagementLog.
     */
    Flux<GradeManagementLog> findByGradeId(Long gradeId);

    /**
     * Busca registros de log por ID de período.
     * @param periodId ID del período.
     * @return Un Flux de GradeManagementLog.
     */
    Flux<GradeManagementLog> findByPeriodId(Long periodId);

    /**
     * Busca registros de log por el ID de la combinación de profesor, curso y aula.
     * @param teacherCoursesClassroom ID de la combinación profesor-curso-aula.
     * @return Un Flux de GradeManagementLog.
     */
    Flux<GradeManagementLog> findByTeacherCoursesClassroom(Long teacherCoursesClassroom);

}