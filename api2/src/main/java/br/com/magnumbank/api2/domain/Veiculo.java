package br.com.magnumbank.api2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "veiculo", indexes = {
        @Index(name = "idx_veiculo_marca", columnList = "marca_id")})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String codigo;
    @Column(nullable = false)
    private String modelo;
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    private Marca marca;
}
