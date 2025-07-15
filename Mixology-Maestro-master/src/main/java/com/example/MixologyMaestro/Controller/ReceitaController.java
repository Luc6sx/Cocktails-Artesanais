package com.example.MixologyMaestro.Controller;

import com.example.MixologyMaestro.Model.Receita;
import com.example.MixologyMaestro.Service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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

    @PostMapping(path = "/{id}/upload-imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImagem(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            // Verifica se o arquivo está vazio
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Arquivo vazio");
            }

            // Gera um nome único para o arquivo
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName); // Define o caminho onde o arquivo será salvo

            // Cria o diretório se não existir
            Files.createDirectories(path.getParent());

            // Salva o arquivo no sistema de arquivos
            Files.write(path, file.getBytes());

            // Atualiza a receita com a URL da imagem
            Receita receita = receitaService.buscarReceitaPorId(id);
            if (receita != null) {
                receita.setImagemURL("/uploads/" + fileName); // Define a URL da imagem na receita
                receitaService.atualizarReceita(id, receita); // Atualiza a receita no banco de dados
                return ResponseEntity.ok("Imagem carregada com sucesso: " + fileName);
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se a receita não existir
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao carregar imagem: " + e.getMessage()); // Retorna 500 Internal Server Error em caso de falha
        }
    }
}
