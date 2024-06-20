import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User, UserLogin } from '../interfaces/auth';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private baseUrl = 'http://localhost:8080';

    constructor(private http: HttpClient) {}
    // Method to get authorization headers
    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        return new HttpHeaders({
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        });
    }

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
    changePassword(currentPassword: string, newPassword: string): Observable<any> {
        const headers = this.getAuthHeaders();
        return this.http.post(`${this.baseUrl}/user/change-password`, { currentPassword, newPassword },{ headers }) ;

    }

}
