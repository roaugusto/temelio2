package com.temelio.nonprofit.email.service;

import com.temelio.nonprofit.email.dto.FoundationChangeDTO;
import com.temelio.nonprofit.email.dto.FoundationDTO;
import com.temelio.nonprofit.email.dto.OrganizationChangeDTO;
import com.temelio.nonprofit.email.dto.OrganizationDTO;

import java.util.List;
import java.util.UUID;

public interface FoundationService {

    FoundationDTO save(FoundationChangeDTO foundationDTO);

    FoundationDTO findById(UUID id);

    FoundationDTO findByEmail(String email);

    List<FoundationDTO> findAll();

    FoundationDTO update(UUID id, FoundationChangeDTO foundationDTO);

    void deleteById(UUID id);

}
