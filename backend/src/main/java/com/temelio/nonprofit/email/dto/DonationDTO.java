package com.temelio.nonprofit.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationDTO {

    private UUID id;
    private Double amount;
    private OrganizationDTO organization;
    private Boolean isDonationNotified;
    private FoundationDTO foundation;
}
