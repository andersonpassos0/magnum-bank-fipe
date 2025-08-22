package br.com.magnumbank.api2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marca", indexes = {
        @Index(name = "uk_marca_codigo", columnList = "codigo", unique = true)})

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
