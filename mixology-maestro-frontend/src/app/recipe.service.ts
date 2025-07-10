import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Receita } from './receita.model';
@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  //URL da API SpringBoot
  private apiUrl = 'http://localhost:8080/api/receitas';

  //HttpClient para poder fazer as requisições
  constructor(private http: HttpClient) { }

  /**
   * Busca todas as receitas no back-end.
   * Retorna um Observable com um array de Receitas.
   */
  public getReceitas() : Observable<Receita[]>
  {
    return this.http.get<Receita[]>(this.apiUrl);
  }

   /**
   * Envia uma nova receita para ser salva no back-end.
   * @param receita O objeto da receita a ser criada.
   */

  public createReceita(receita: Omit<Receita, 'id'>): Observable<Receita>
  {
    return this.http.post<Receita>(this.apiUrl, receita)
  }


}
