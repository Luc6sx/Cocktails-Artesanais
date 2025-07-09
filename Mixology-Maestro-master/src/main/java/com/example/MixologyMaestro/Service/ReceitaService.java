package com.example.MixologyMaestro.Service;

import com.example.MixologyMaestro.Model.Receita;
import com.example.MixologyMaestro.Repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaService
{
    @Autowired
    private ReceitaRepository receitaRepository;

    // Metodos CRUD

    //adicionar/criar uma receita
    public Receita criarReceita(Receita receita)
    {
        return receitaRepository.save(receita); //  salva a receita no banco de dados
    }

    public List<Receita> buscarTodasReceitas()
    {
        return receitaRepository.findAll(); // retorna todas as receitas do banco de dados
    }

    public Receita buscarReceitaPorId(Long id)
    {
        return receitaRepository.findById(id).orElse(null); // busca uma receita pelo ID, retorna null se não encontrar
    }

    public Receita atualizarReceita(Long id, Receita receitaAtualizada)
    {
        if (receitaRepository.existsById(id)) // verifica se a receita existe
        {
            receitaAtualizada.setId(id); // define o ID da receita atualizada
            return receitaRepository.save(receitaAtualizada); // salva a receita atualizada no banco de dados
        }
        return null; // retorna null se a receita não existir
    }

    public void excluirReceita(Long id)
    {
        if (receitaRepository.existsById(id)) // verifica se a receita existe
        {
            receitaRepository.deleteById(id); // exclui a receita pelo ID
        }
        // Se não existir, não faz nada (poderia lançar uma exceção se desejado)
    }
}
