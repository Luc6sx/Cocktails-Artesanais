import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'; // HttpHeaders adicionado
import { Observable } from 'rxjs';
import { Receita } from './receita.model'; // Seu modelo de receita

@Injectable({
  providedIn: 'root'
})
export class RecipeService { // Nome da classe é RecipeService

  private apiUrl = 'http://localhost:8080/api/receitas';

  constructor(private http: HttpClient) { }

  getReceitas(): Observable<Receita[]> {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin')
    });
    return this.http.get<Receita[]>(this.apiUrl, { headers: headers });
  }

  createReceita(receita: Omit<Receita, 'id'>): Observable<Receita> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('admin:admin')
    });
    return this.http.post<Receita>(this.apiUrl, receita, { headers: headers });
  }

  uploadImagem(receitaId: number, file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin')
    });

    return this.http.post(`${this.apiUrl}/${receitaId}/upload-imagem`, formData, { headers: headers });
  }
}