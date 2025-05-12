package pe.edu.vallegrande.vg_ms_grade_management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Campo modificado en una calificación, con valor anterior, nuevo e impacto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifiedField {

    private String field;

    private Object oldValue;

    private Object newValue;

    private String impact;

}