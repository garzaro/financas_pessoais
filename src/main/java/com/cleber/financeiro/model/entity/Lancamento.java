package com.cleber.financeiro.model.entity;

import com.cleber.financeiro.model.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento", schema = "financas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "mes")
    private Integer mes;
    
    @Column(name = "ano")
    private Integer ano;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @Column(name = "valor")
    private BigDecimal valor;
    
    @Column(name = "data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;
    
    @Column(name = "tipo_lancamento")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipoLancamento;
    
    @Column(name = "status_lancamento")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento statusLancamento;

}
