package com.temelio.nonprofit.email.service.impl;

import com.temelio.nonprofit.email.dto.EmailSentChangeDTO;
import com.temelio.nonprofit.email.dto.EmailSentDTO;
import com.temelio.nonprofit.email.exception.BadRequestException;
import com.temelio.nonprofit.email.model.EmailSentEntity;
import com.temelio.nonprofit.email.model.FoundationEntity;
import com.temelio.nonprofit.email.repository.EmailSentRepository;
import com.temelio.nonprofit.email.repository.FoundationRepository;
import com.temelio.nonprofit.email.service.EmailSentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailSentServiceImpl implements EmailSentService {

    private final EmailSentRepository emailSentRepository;
    private final FoundationRepository foundationRepository;

    ModelMapper modelMapper = new ModelMapper();

    public EmailSentServiceImpl(EmailSentRepository emailSentRepository, FoundationRepository foundationRepository) {
        this.emailSentRepository = emailSentRepository;
        this.foundationRepository = foundationRepository;
    }

    @Override
    public EmailSentDTO save(EmailSentChangeDTO emailSentChange) {
        UUID foundationId = emailSentChange.getFoundationId();
        FoundationEntity foundation = foundationRepository.findById(foundationId)
                .orElseThrow(() -> new BadRequestException(String.format("Foundation with id %s not found!", foundationId)));

        EmailSentEntity emailSent = new EmailSentEntity();
        emailSent.setOrganizationId(emailSentChange.getOrganizationId());
        emailSent.setOrganizationName(emailSentChange.getOrganizationName());
        emailSent.setOrganizationEmail(emailSentChange.getOrganizationEmail());
        emailSent.setSubject(emailSentChange.getSubject());
        emailSent.setContent(emailSentChange.getContent());
        emailSent.setSendDate(emailSentChange.getSendDate());
        emailSent.setFoundation(foundation);

        return mapToDTO(emailSentRepository.save(emailSent));
    }

    @Override
    public EmailSentDTO findById(UUID id) {
        Optional<EmailSentEntity> emailSentEntityOptional = emailSentRepository.findById(id);
        return emailSentEntityOptional.map(this::mapToDTO).orElse(null);
    }

    @Override
    public List<EmailSentDTO> findAll(UUID foundationId) {
        List<EmailSentEntity> users = emailSentRepository.findAllByFoundationId(foundationId);
        return users.stream().map(user ->
                modelMapper.map(user, EmailSentDTO.class)).toList();
    }

    private EmailSentDTO mapToDTO(EmailSentEntity emailSentEntity) {
        return modelMapper.map(emailSentEntity, EmailSentDTO.class);
    }

}
