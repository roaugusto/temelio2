package com.temelio.nonprofit.email.repository;

import com.temelio.nonprofit.email.model.FoundationEntity;
import com.temelio.nonprofit.email.model.OrganizationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoundationRepository extends CrudRepository<FoundationEntity, UUID> {

    Optional<FoundationEntity> findByEmail(String email);

}
