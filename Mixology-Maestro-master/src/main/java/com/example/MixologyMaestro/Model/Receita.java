package com.example.MixologyMaestro.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// Removidas as importações do Lombok
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // Indica que esta classe é uma entidade JPA
@Table(name = "receitas") // Define o nome da tabela no banco de dados
// Removidas as anotações do Lombok
public class Receita
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de ID
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT") // Define o tipo de coluna como TEXT para suportar descrições longas
    private String descricao;

    @Column(columnDefinition = "TEXT")
    private String instrucoes; // Corrigido para 'instrucoes' (sem 'ç')

    @Column(length = 500)
    private String imagemURL;

    @Column(nullable = false, length = 100)
    private String categoria;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceitaIngrediente> receitaIngredientes = new ArrayList<>(); // Inicialização direta


}