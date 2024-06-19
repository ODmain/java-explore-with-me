package ru.practicum.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInputDto {
    @NotBlank
    @Email
    @Size(min = 6, max = 254)
    String email;
    @NotBlank
    @Size(min = 2, max = 250)
    String name;
}