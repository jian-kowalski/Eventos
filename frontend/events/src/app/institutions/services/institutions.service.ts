import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Institution } from '../model/Institution';
import { first, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InstitutionsService {


  private readonly API = 'http://127.0.0.1:8080/institutions';


  constructor(private httpClient: HttpClient) {}

  list() {
    return this.httpClient.get<Institution[]>(this.API).pipe(first());
  }

  loadById(id: string) {
    return this.httpClient.get<Institution>(`${this.API}/${id}`).pipe(first())
  }

  save(record: Institution) {
   return this.httpClient.post<Institution>(this.API, record).pipe(first());
  }

}
