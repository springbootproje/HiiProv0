import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User, UserLogin } from '../interfaces/auth';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private baseUrl = 'http://localhost:8080';

    constructor(private http: HttpClient) {}

    registerUser(userDetails: User): Observable<any> {
        return this.http.post(`${this.baseUrl}/user/new`, userDetails);
    }

    login(userLogin: UserLogin): Observable<any> {
        return this.http.post(`${this.baseUrl}/user/login`, userLogin);
    }

    isLoggedIn(): boolean {
        return !!localStorage.getItem('accessToken');
    }

    getToken(): string | null {
        return localStorage.getItem('accessToken');
    }

    logout(): void {
        localStorage.removeItem('accessToken');
        sessionStorage.removeItem('email');
    }
}
