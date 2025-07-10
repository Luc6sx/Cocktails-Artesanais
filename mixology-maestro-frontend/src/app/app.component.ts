import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms';
import { RecipeService } from './recipe.service';
import { Receita } from './receita.model';

@Component({
  selector: 'app-root',
  imports: [CommonModule, FormsModule], 
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  
  isPopupVisible = false;
  novaReceita: any = {};
  receitas: Receita[] = [];
  title = 'mixology-maestro-frontend';

  constructor(private recipeService: RecipeService) {}

  ngOnInit(): void {
    this.carregarReceitas();
  }

  carregarReceitas(): void {
    this.recipeService.getReceitas().subscribe(dados => {
      console.log('Receitas carregadas do banco:', dados);
      this.receitas = dados;
    });
  }

  abrirPopup(): void {
      console.log('Botão Criar foi clicado, tentando abrir o popup...');
    this.isPopupVisible = true;
  }

  fecharPopup(): void {
    this.isPopupVisible = false;
  }

  onSubmit(): void {
    console.log('Dados do formulário a serem enviados:', this.novaReceita);

    this.recipeService.createReceita(this.novaReceita)
      .subscribe(receitaSalva => {
        console.log('Receita salva com sucesso!', receitaSalva);

        this.receitas.push(receitaSalva);

        this.fecharPopup();
        this.novaReceita = {}; 
      });
  }

 
}