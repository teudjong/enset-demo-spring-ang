package net.raissa.students.models.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiErrorResponse {
    private String code;
    private String message;
}
