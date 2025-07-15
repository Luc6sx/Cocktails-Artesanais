
export interface Receita
{
  id: number;
  nome : string;
  imageUrl: string;
  instrucoes: string;
  categoria: string;
  descricao: string;
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'; // Adicione HttpHeaders
import { Observable } from 'rxjs'; // Para Observables

@Injectable(
  {
    providedIn: 'root'
  })
  export class ReceitaService
  {
    //URL da API
    private baseUrl = 'http://localhost:8080/api/receitas/';

    constructor(private http: HttpClient) {}

    //método para enviar a imagem para o back-end
    uploadImagem(receitaId: number, file: File): Observable<any>
    {
      const formData: FormData = new FormData();
      formData.append('file', file) // 'file' deve corresponder ao @RequestParam("file") do Spring Boot


    // Adicione o cabeçalho de autorização (Basic Auth)
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin') // btoa codifica em Base64
      // 'Content-Type': 'multipart/form-data' // HttpClient adiciona isso automaticamente com FormData
    });

      // Envia a requisição POST para o endpoint de upload do back-end
      return this.http.post(`${this.baseUrl}/${receitaId}/upload-imagem`, formData, { headers: headers });
    }

  }
  