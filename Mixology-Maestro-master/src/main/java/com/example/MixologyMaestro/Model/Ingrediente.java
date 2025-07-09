package com.example.MixologyMaestro.Model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "ingredientes") // Define o nome da tabela no banco de dados
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ingrediente
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de ID
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL, orphanRemoval = true)
// mappedBy aponta para o campo 'ingrediente' na entidade ReceitaIngrediente
    private List<ReceitaIngrediente> receitaIngredientes = new ArrayList<>();

}
