package pe.edu.vallegrande.vg_ms_grade_management.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.GradeManagementLogService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.GradeManagementLog;
import pe.edu.vallegrande.vg_ms_grade_management.domain.repository.GradeManagementLogRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del servicio para la gestión de logs de calificaciones.
 */
@Service
@RequiredArgsConstructor
public class GradeManagementLogServiceImpl implements GradeManagementLogService {

    private final GradeManagementLogRepository gradeManagementLogRepository;

    /**
     * Guarda un nuevo registro de log de calificación.
     */
    @Override
    public Mono<GradeManagementLog> save(GradeManagementLog gradeManagementLog) {

        return gradeManagementLogRepository.save(gradeManagementLog);
    }

    /**
     * Obtiene todos los registros de log de calificaciones.
     */
    @Override
    public Flux<GradeManagementLog> getAll() {
        return gradeManagementLogRepository.findAll();
    }

    /**
     * Obtiene un registro de log de calificación por su ID.
     */
    @Override
    public Mono<GradeManagementLog> getById(String id) {
        return gradeManagementLogRepository.findById(id);
    }

    /**
     * Obtiene todos los registros de log de calificaciones para un estudiante específico.
     */
    @Override
    public Flux<GradeManagementLog> getByStudentId(Long studentId) {
        return gradeManagementLogRepository.findByStudentId(studentId);
    }

    /**
     * Obtiene todos los registros de log de calificaciones para una calificación específica.
     */
    @Override
    public Flux<GradeManagementLog> getByGradeId(Long gradeId) {
        return gradeManagementLogRepository.findByGradeId(gradeId);
    }

    /**
     * Obtiene todos los registros de log de calificaciones para un período específico.
     */
    @Override
    public Flux<GradeManagementLog> getByPeriodId(Long periodId) {
        return gradeManagementLogRepository.findByPeriodId(periodId);
    }

    /**
     * Obtiene todos los registros de log de calificaciones para una combinación específica de profesor, curso y aula.
     */
    @Override
    public Flux<GradeManagementLog> getByTeacherCoursesClassroom(Long teacherCoursesClassroom) {
        return gradeManagementLogRepository.findByTeacherCoursesClassroom(teacherCoursesClassroom);
    }

    /**
     * Elimina un registro de log de calificación por su ID.
     */
    @Override
    public Mono<Void> delete(String id) {
        return gradeManagementLogRepository.deleteById(id);
    }
}