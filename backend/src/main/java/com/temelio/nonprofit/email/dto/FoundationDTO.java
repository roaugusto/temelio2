package com.temelio.nonprofit.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoundationDTO {

    private UUID id;
    private String name;
    private String email;

}
