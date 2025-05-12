package pe.edu.vallegrande.vg_ms_grade_management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Datos modificados de una calificación: calificación anterior/nueva, campos y metadatos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifiedData {

    private Double previousGrade;

    private Double newGrade;

    private List<ModifiedField> modifiedFields;

    private ContextMetadata contextMetadata;

}