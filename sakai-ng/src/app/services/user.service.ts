import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDto } from '../interfaces/user-dto';

@Injectable({
    providedIn: 'root',
})
export class UserService {
    private apiUrl = 'http://localhost:8080/user'; // Update with your API URL

    constructor(private http: HttpClient) {}

    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('accessToken');
        return new HttpHeaders({
            Authorization: `Bearer ${token}`,
        });
    }

    getUsers(): Observable<UserDto[]> {
        const headers = this.getAuthHeaders();
        return this.http.get<UserDto[]>(`${this.apiUrl}/list`, { headers });
    }
}
