package com.example.MixologyMaestro.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "receita_ingredientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaIngrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Muitas ReceitaIngrediente para uma Receita
    @JoinColumn(name = "receita_id", nullable = false) // Coluna de FK para Receita
    private Receita receita;

    @ManyToOne // Muitas ReceitaIngrediente para um Ingrediente
    @JoinColumn(name = "ingrediente_id", nullable = false) // Coluna de FK para Ingrediente
    private Ingrediente ingrediente;

    // Atributo da relação: a quantidade deste ingrediente nesta receita
    private Double quantidade;

    @Column(length = 50)
    private String unidadeMedida; // Ex: "ml", "folhas", "cubos" (se for específica da receita)



}
