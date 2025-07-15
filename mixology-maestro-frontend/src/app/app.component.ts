import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RecipeService } from './recipe.service'; 
import { Receita } from './receita.model'; 
import { HttpClientModule } from '@angular/common/http'; 

@Component({
  selector: 'app-root',
  imports: [CommonModule, FormsModule, HttpClientModule],
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  // --- Campos para Upload de Imagem ---
  selectedFile: File | null = null; // Armazena o arquivo de imagem selecionado
  imageUrl: string | ArrayBuffer | null = null; // Para pré-visualização da imagem no HTML
  private receitaIdParaUpload: number | null = null; // Armazenará o ID da receita salva para associar a imagem

  // --- Propriedades Existentes ---
  abrirReceita = false;

  selectedReceita: Receita | null = null;

  isPopupVisible = false;
  novaReceita: any = {
    nome: '',
    descricao: '',
    instrucoes: '',
    imagemURL: '', 
    categoria: ''
  };
  receitas: Receita[] = [];
  title = 'mixology-maestro-frontend';
  

  constructor(private recipeService: RecipeService) { }

  ngOnInit(): void {
    this.carregarReceitas();
  }

  carregarReceitas(): void {
    this.recipeService.getReceitas().subscribe(dados => {
      console.log('Receitas carregadas do banco:', dados);
      this.receitas = dados;
    }, error => {
      console.error('Erro ao carregar receitas:', error);
      alert('Erro ao carregar receitas. Verifique o console do navegador e o console do backend.');
    });
  }

  abrirRe(receita : Receita) : void
  {
    this.abrirReceita = true;
    this.selectedReceita = receita;

  }
  abrirPopup(): void {
    console.log('Botão Criar foi clicado, tentando abrir o popup...');
    this.isPopupVisible = true;
    this.novaReceita = { // Limpa o formulário ao abrir o popup
      nome: '', descricao: '', instrucoes: '', imagemURL: '', categoria: ''
    };
    this.selectedFile = null; // Limpa o arquivo selecionado
    this.imageUrl = null; // Limpa a pré-visualização
  }

  fecharPopup(): void {
    this.isPopupVisible = false;
    this.selectedFile = null; // Garante que o arquivo selecionado seja limpo
    this.imageUrl = null; // Limpa a pré-visualização
    this.abrirReceita = false;
    this.selectedReceita = null;
  }

  // --- Métodos para Upload de Imagem ---
  onFileSelected(event: any): void {
    const file: File = event.target.files[0]; // Pega o primeiro arquivo selecionado

    if (file) {
      // Validação básica do tipo de arquivo no front-end
      if (!file.type.startsWith('image/')) {
        alert('Por favor, selecione um arquivo de imagem (JPG, PNG, GIF, etc.).');
        this.selectedFile = null;
        this.imageUrl = null;
        return;
      }

      this.selectedFile = file;

      // Exibir pré-visualização da imagem (opcional, para melhor UX)
      const reader = new FileReader();
      reader.onload = e => this.imageUrl = reader.result;
      reader.readAsDataURL(file);
    }
  }

  // --- Método de Submissão do Formulário (Integrado com Upload) ---
  onSubmit(): void {
    console.log('Dados do formulário a serem enviados:', this.novaReceita);

    // 1. Cria a receita no back-end
    this.recipeService.createReceita(this.novaReceita).subscribe(
      (receitaSalva) => {
        console.log('Receita criada com sucesso, ID:', receitaSalva.id);
        this.receitas.push(receitaSalva); // Adiciona a nova receita à lista local
        this.receitaIdParaUpload = receitaSalva.id; // Salva o ID para o upload da imagem

        // 2. Se um arquivo de imagem foi selecionado, faz o upload
        if (this.selectedFile && this.receitaIdParaUpload) {
          this.uploadImagemParaReceita(this.receitaIdParaUpload, this.selectedFile);
        } else {
          alert('Receita criada com sucesso! Nenhuma imagem foi selecionada para upload.');
          this.fecharPopup(); // Fecha o popup se não houver imagem para upload
          this.novaReceita = {}; // Limpa o formulário
        }
      },
      (error) => {
        console.error('Erro ao criar receita:', error);
        alert('Erro ao criar receita. Verifique o console do navegador e o console do backend.');
      }
    );
  }

  // --- Método para Chamar o Serviço de Upload de Imagem ---
  uploadImagemParaReceita(receitaId: number, file: File): void {
    this.recipeService.uploadImagem(receitaId, file).subscribe(
      (response) => {
        console.log('Upload de imagem bem-sucedido:', response);
        alert('Receita e imagem salvas com sucesso!');
        this.carregarReceitas(); // Recarrega a lista para mostrar a imagem atualizada
        this.fecharPopup();
        this.novaReceita = {};
      },
      (error) => {
        console.error('Erro no upload da imagem:', error);
        alert('Receita criada, mas houve um erro no upload da imagem. Verifique o console.');
        this.fecharPopup();
        this.novaReceita = {};
      }
    );
  }}