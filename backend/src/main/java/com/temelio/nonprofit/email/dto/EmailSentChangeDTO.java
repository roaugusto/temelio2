package com.temelio.nonprofit.email.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSentChangeDTO {

    @JsonIgnore
    private UUID id;
    private UUID organizationId;
    private String organizationEmail;
    private String organizationName;
    private String subject;
    private String content;
    private LocalDateTime sendDate;
    private UUID foundationId;
}
