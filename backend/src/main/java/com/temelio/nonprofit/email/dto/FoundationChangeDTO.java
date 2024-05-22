package com.temelio.nonprofit.email.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class FoundationChangeDTO {

    @JsonIgnore
    private UUID id;
    private String name;
    private String email;
}
