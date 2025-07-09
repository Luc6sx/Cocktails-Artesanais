package com.example.MixologyMaestro.Repository;

import com.example.MixologyMaestro.Model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    // JpaRepository<TipoDaEntidade, TipoDaChavePrimaria>
    // Este interface já fornece os métodos:
    // save(entity) -> para adicionar e atualizar
    // findById(id) -> para buscar por ID
    // findAll() -> para buscar todos
    // deleteById(id) -> para excluir por ID
    // delete(entity) -> para excluir uma entidade
    // existsById(id) -> para verificar se existe por ID
}
