package pe.edu.vallegrande.vg_ms_grade_management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Log para la gestión de calificaciones. Mapeado a 'grade_management_logs' en MongoDB.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "grade_management_logs")
public class GradeManagementLog {

    @Id
    private String id;

    private Long gradeId;

    private Long studentId;

    private Long teacherCoursesClassroom;

    private Long periodId;

    private String actionType;

    private ModifiedData modifiedData;

    private String changeReason;

    private Long modifiedBy;

    /**
     * Fecha y hora del registro de modificación (auto-establecida).
     */
    private LocalDateTime modifiedAt = LocalDateTime.now();

}