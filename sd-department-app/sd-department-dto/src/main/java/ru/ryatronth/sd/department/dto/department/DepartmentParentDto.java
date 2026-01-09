package ru.ryatronth.sd.department.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DepartmentParentDto", description = "Родительское подразделение (кратко)")
public class DepartmentParentDto {

    @Schema(description = "ID родителя", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Название родителя", example = "Головной офис")
    private String name;

}
