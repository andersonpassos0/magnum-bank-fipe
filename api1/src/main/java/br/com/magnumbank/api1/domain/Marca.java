package br.com.magnumbank.api1.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marca")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String codigo;
    @Column(nullable = false)
    private String nome;
}
