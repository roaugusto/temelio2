package com.temelio.nonprofit.email.service.impl;

import com.temelio.nonprofit.email.dto.ConfigChangeDTO;
import com.temelio.nonprofit.email.dto.ConfigDTO;
import com.temelio.nonprofit.email.exception.BadRequestException;
import com.temelio.nonprofit.email.model.ConfigEntity;
import com.temelio.nonprofit.email.model.FoundationEntity;
import com.temelio.nonprofit.email.repository.ConfigRepository;
import com.temelio.nonprofit.email.repository.FoundationRepository;
import com.temelio.nonprofit.email.service.ConfigService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;
    private final FoundationRepository foundationRepository;

    ModelMapper modelMapper = new ModelMapper();

    public ConfigServiceImpl(ConfigRepository configRepository, FoundationRepository foundationRepository) {
        this.configRepository = configRepository;
        this.foundationRepository = foundationRepository;
    }

    @Override
    public ConfigDTO save(ConfigChangeDTO configDTO) {
        ConfigEntity config = modelMapper.map(configDTO, ConfigEntity.class);
        return  mapToDTO(configRepository.save(config));
    }

    @Override
    public ConfigDTO findByFoundationId(UUID foundationId) {
        Optional<ConfigEntity> configEntityOptional = configRepository.findByFoundationId(foundationId);
        return configEntityOptional.map(this::mapToDTO).orElse(null);
    }

    @Override
    public ConfigDTO update(ConfigChangeDTO configDTO) {
        UUID foundationId = configDTO.getFoundationId();
        FoundationEntity foundation = foundationRepository.findById(foundationId)
                .orElseThrow(() -> new BadRequestException(String.format("Foundation with id %s not found!", foundationId)));

        ConfigEntity config = configRepository.findByFoundationId(foundationId)
                .orElseThrow(() -> new BadRequestException("Config not found!"));

        config.setName(configDTO.getName());
        config.setTemplate(configDTO.getTemplate());
        config.setSubject(configDTO.getSubject());
        config.setFoundation(foundation);
        return mapToDTO(configRepository.save(config));
    }

    private ConfigDTO mapToDTO(ConfigEntity configEntity) {
        return modelMapper.map(configEntity, ConfigDTO.class);
    }

}
