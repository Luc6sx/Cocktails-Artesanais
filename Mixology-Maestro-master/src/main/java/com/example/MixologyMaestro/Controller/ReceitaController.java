package com.example.MixologyMaestro.Controller;

import com.example.MixologyMaestro.Model.Receita;
import com.example.MixologyMaestro.Service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // indica um controlador rest
@RequestMapping("/api/receitas") // caminho endpoints para todos neste controller
@CrossOrigin(origins = "http://localhost:4200") // Permite requisições de angular

public class ReceitaController
{

    @Autowired
    private ReceitaService receitaService; // Injeção de dependência do serviço de receitas

    // endpoint crud

    //adicionar/criar uma receita (POST /api/receitas)
    @PostMapping
    public ResponseEntity<Receita> criarReceita(@RequestBody Receita receita) {

        Receita novaReceita = receitaService.criarReceita(receita); // chama o serviço para criar a receita
        return ResponseEntity.ok(novaReceita); // retorna a nova receita criada com status 200 OK
    }

    // Buscar receitas (GET /api/receitas)
    @GetMapping
    public ResponseEntity<List<Receita>> buscarTodasReceitas() {
        List<Receita> receitas = receitaService.buscarTodasReceitas(); // chama o serviço para buscar todas as receitas
        return ResponseEntity.ok(receitas); // retorna a lista de receitas com status 200 OK
    }

    //Buscar receita por ID (GET /api/receitas/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Receita> buscarReceitaPorId(@PathVariable Long id) {
        Receita receita = receitaService.buscarReceitaPorId(id); // chama o serviço para buscar a receita pelo ID
        if (receita != null) {
            return ResponseEntity.ok(receita); // retorna a receita encontrada com status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // retorna 404 Not Found se a receita não existir
        }
    }

    // Atualizar receita (PUT /api/receitas/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Receita> atualizarReceita(@PathVariable Long id, @RequestBody Receita receitaAtualizada) {
        Receita receita = receitaService.atualizarReceita(id, receitaAtualizada); // chama o serviço para atualizar a receita
        if (receita != null) {
            return ResponseEntity.ok(receita); // retorna a receita atualizada com status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // retorna 404 Not Found se a receita não existir
        }
    }
    // Excluir receita (DELETE /api/receitas/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirReceita(@PathVariable Long id) {
        if (receitaService.buscarReceitaPorId(id) != null) { // verifica se a receita existe
            receitaService.excluirReceita(id); // chama o serviço para excluir a receita
            return ResponseEntity.noContent().build(); // retorna 204 No Content se a exclusão for bem-sucedida
        } else {
            return ResponseEntity.notFound().build(); // retorna 404 Not Found se a receita não existir
        }
    }
}
