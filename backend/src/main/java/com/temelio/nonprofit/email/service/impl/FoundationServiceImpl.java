package com.temelio.nonprofit.email.service.impl;

import com.temelio.nonprofit.email.dto.FoundationChangeDTO;
import com.temelio.nonprofit.email.dto.FoundationDTO;
import com.temelio.nonprofit.email.exception.BadRequestException;
import com.temelio.nonprofit.email.model.FoundationEntity;
import com.temelio.nonprofit.email.repository.FoundationRepository;
import com.temelio.nonprofit.email.service.FoundationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FoundationServiceImpl implements FoundationService {

    private final FoundationRepository foundationRepository;

    ModelMapper modelMapper = new ModelMapper();

    public FoundationServiceImpl(FoundationRepository foundationRepository) {
        this.foundationRepository = foundationRepository;
    }

    @Override
    public FoundationDTO save(FoundationChangeDTO foundationDTO) {
        FoundationEntity foundation = modelMapper.map(foundationDTO, FoundationEntity.class);
        return  mapToDTO(foundationRepository.save(foundation));
    }

    @Override
    public FoundationDTO findById(UUID id) {
        Optional<FoundationEntity> foundation = foundationRepository.findById(id);
        return foundation.map(this::mapToDTO).orElse(null);
    }

    @Override
    public FoundationDTO findByEmail(String email) {
        Optional<FoundationEntity> foundation = foundationRepository.findByEmail(email);
        return foundation.map(this::mapToDTO).orElse(null);
    }

    @Override
    public List<FoundationDTO> findAll() {
        List<FoundationEntity> users = (List<FoundationEntity>) foundationRepository.findAll();
        return users.stream().map(user ->
                modelMapper.map(user, FoundationDTO.class)).toList();
    }

    @Override
    public FoundationDTO update(UUID id, FoundationChangeDTO foundationDTO) {
        Optional<FoundationEntity> existingFoundationOptional = foundationRepository.findById(id);
        if (existingFoundationOptional.isPresent()) {
            FoundationEntity existingFoundation = existingFoundationOptional.get();
            existingFoundation.setName(foundationDTO.getName());
            existingFoundation.setEmail(foundationDTO.getEmail());
            return mapToDTO(foundationRepository.save(existingFoundation));
        } else {
            throw new BadRequestException(String.format("Foundation with id %s not found!", id));
        }
    }

    @Override
    public void deleteById(UUID id) {
        Optional<FoundationEntity> existingFoundationOptional = foundationRepository.findById(id);
        if (existingFoundationOptional.isEmpty()) {
            throw new BadRequestException(String.format("Foundation with id %s not found!", id));
        }
        foundationRepository.deleteById(id);
    }

    private FoundationDTO mapToDTO(FoundationEntity foundationEntity) {
        return modelMapper.map(foundationEntity, FoundationDTO.class);
    }

}
