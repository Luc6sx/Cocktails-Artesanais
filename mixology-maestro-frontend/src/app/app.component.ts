import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgFor } from '@angular/common';

interface Receita
{
  id: number;
  nome : string;
}

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NgFor],
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
  
})
export class AppComponent {
  //array que guardará a lista de receitas vindas do back-end
  receitas: Receita[] = [
    //exemplo
    {id: 1, nome:"caipirinha Clássica"}
  ];

  criarReceita(): void 
  {
    console.log("criado")

    const novaReceita: Receita = {
      id:Date.now(), //gera um id unico baseado no tempo
      nome: "nova receita"
    }
    this.receitas.push(novaReceita);
  }
  title = 'mixology-maestro-frontend';
}




