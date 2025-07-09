package com.example.MixologyMaestro.Repository;

import com.example.MixologyMaestro.Model.Receita;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>
{
// JpaRepository<TipoDaEntidade, TipoDaChavePrimaria>
    // Este interface já fornece os métodos:
    // save(entity) -> para adicionar e atualizar
    // findById(id) -> para buscar por ID
    // findAll() -> para buscar todos
    // deleteById(id) -> para excluir por ID
    // delete(entity) -> para excluir uma entidade
    // existsById(id) -> para verificar se existe por ID
}
