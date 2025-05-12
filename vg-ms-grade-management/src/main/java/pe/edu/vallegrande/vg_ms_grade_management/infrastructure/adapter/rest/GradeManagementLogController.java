package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.adapter.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.GradeManagementLogService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.GradeManagementLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
     * Controlador REST para la gestión de logs de calificaciones.
     */
@RestController
@RequestMapping("/grade-logs")
@RequiredArgsConstructor
public class GradeManagementLogController {

    private final GradeManagementLogService gradeManagementLogService;

    /**
     * Crea un nuevo registro de log de calificación.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GradeManagementLog> createLog(@RequestBody GradeManagementLog gradeManagementLog) {
        return gradeManagementLogService.save(gradeManagementLog);
    }

    /**
     * Obtiene todos los registros de log de calificaciones.
     */
    @GetMapping
    public Flux<GradeManagementLog> getAllLogs() {
        return gradeManagementLogService.getAll();
    }

    /**
     * Obtiene un registro de log de calificación por su ID.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<GradeManagementLog>> getLogById(@PathVariable String id) {
        return gradeManagementLogService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene registros de log por ID de estudiante.
     */
    @GetMapping("/student/{studentId}")
    public Flux<GradeManagementLog> getLogsByStudentId(@PathVariable Long studentId) {
        return gradeManagementLogService.getByStudentId(studentId);
    }

    /**
     * Obtiene registros de log por ID de calificación.
     */
    @GetMapping("/grade/{gradeId}")
    public Flux<GradeManagementLog> getLogsByGradeId(@PathVariable Long gradeId) {
        return gradeManagementLogService.getByGradeId(gradeId);
    }

    /**
     * Obtiene registros de log por ID de período.
     */
    @GetMapping("/period/{periodId}")
    public Flux<GradeManagementLog> getLogsByPeriodId(@PathVariable Long periodId) {
        return gradeManagementLogService.getByPeriodId(periodId);
    }

    /**
     * Obtiene registros de log por ID de la combinación profesor-curso-aula.
     */
    @GetMapping("/teacher-courses-classroom/{teacherCoursesClassroom}")
    public Flux<GradeManagementLog> getLogsByTeacherCoursesClassroom(@PathVariable Long teacherCoursesClassroom) {
        return gradeManagementLogService.getByTeacherCoursesClassroom(teacherCoursesClassroom);
    }

    /**
     * Elimina un registro de log de calificación por su ID.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteLog(@PathVariable String id) {
        return gradeManagementLogService.getById(id)
                .flatMap(existingLog ->
                        gradeManagementLogService.delete(id)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}