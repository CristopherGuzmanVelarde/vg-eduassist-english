package pe.edu.vallegrande.vg_ms_grade_management.application.service;

import pe.edu.vallegrande.vg_ms_grade_management.domain.model.GradeManagementLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interfaz para el servicio de gestión de logs de calificaciones.
 */
public interface GradeManagementLogService {

    /**
     * Guarda un nuevo registro de log de calificación.
     */
    Mono<GradeManagementLog> save(GradeManagementLog gradeManagementLog);

    /**
     * Obtiene todos los registros de log de calificaciones.
     */
    Flux<GradeManagementLog> getAll();

    /**
     * Obtiene un registro de log de calificación por su ID.
     */
    Mono<GradeManagementLog> getById(String id);

    /**
     * Obtiene todos los registros de log de calificaciones para un estudiante específico.
     */
    Flux<GradeManagementLog> getByStudentId(Long studentId);

    /**
     * Obtiene todos los registros de log de calificaciones para una calificación específica.
     */
    Flux<GradeManagementLog> getByGradeId(Long gradeId);

    /**
     * Obtiene todos los registros de log de calificaciones para un período específico.
     */
    Flux<GradeManagementLog> getByPeriodId(Long periodId);

    /**
     * Obtiene todos los registros de log de calificaciones para una combinación específica de profesor, curso y aula.
     */
    Flux<GradeManagementLog> getByTeacherCoursesClassroom(Long teacherCoursesClassroom);

    /**
     * Elimina un registro de log de calificación por su ID.
     */
    Mono<Void> delete(String id);
}