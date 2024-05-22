package com.temelio.nonprofit.email.repository;

import com.temelio.nonprofit.email.model.ConfigEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfigRepository extends CrudRepository<ConfigEntity, UUID> {

    Optional<ConfigEntity> findByFoundationId(UUID foundationId);
}
