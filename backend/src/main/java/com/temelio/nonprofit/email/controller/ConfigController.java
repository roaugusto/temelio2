package com.temelio.nonprofit.email.controller;

import com.temelio.nonprofit.email.dto.ConfigChangeDTO;
import com.temelio.nonprofit.email.dto.ConfigDTO;
import com.temelio.nonprofit.email.dto.Response;
import com.temelio.nonprofit.email.service.ConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/template")
@Tag(name = "Template")
public class ConfigController {

    private final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/{foundationId}")
    public ResponseEntity<Response<ConfigDTO>> getConfigByFoundationId(@PathVariable UUID foundationId) {
        ConfigDTO configDTO = configService.findByFoundationId(foundationId);

        Response<ConfigDTO> response = new Response<>(HttpStatus.OK.value(), "OK", configDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<ConfigDTO>> saveConfig(@RequestBody ConfigChangeDTO configDTO) {
        ConfigDTO newConfig = configService.save(configDTO);

        Response<ConfigDTO> response = new Response<>(HttpStatus.CREATED.value(), "Config created!", newConfig);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<Response<ConfigDTO>> updateConfig(@RequestBody ConfigChangeDTO configDTO) {
        ConfigDTO updatedConfig = configService.update(configDTO);

        Response<ConfigDTO> response = new Response<>(HttpStatus.OK.value(), "Config updated!", updatedConfig);
        return ResponseEntity.ok(response);
    }

}
