package com.temelio.nonprofit.email.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "foundations")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FoundationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrganizationEntity> organizations = new ArrayList<>();

}
