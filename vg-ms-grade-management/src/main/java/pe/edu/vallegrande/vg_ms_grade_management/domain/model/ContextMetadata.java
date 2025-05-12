package pe.edu.vallegrande.vg_ms_grade_management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Metadatos de contexto para una modificación de calificación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContextMetadata {

    private String curricularComponent;

    private String learningUnit;

    private String evaluationCriteria;

}