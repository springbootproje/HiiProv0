import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import {
    TaskCreationDto,
    TaskDto,
} from '../demo/components/projects/project.model';
import { ApiResponse } from '../demo/components/projects/project.model';
@Injectable({
    providedIn: 'root',
})
export class TaskService {
    private apiUrl = 'http://localhost:8080/tache';

    constructor(private http: HttpClient) {}

    // Method to get authorization headers
    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        return new HttpHeaders({
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        });
    }

    // Fetch tasks by project ID
    getTasksByProject(projectId: number): Observable<TaskDto[]> {
        const headers = this.getAuthHeaders();
        return this.http.get<TaskDto[]>(`${this.apiUrl}/project/${projectId}`, {
            headers,
        });
    }

    // Fetch a single task by its ID
    getTaskById(taskId: number): Observable<TaskDto> {
        const headers = this.getAuthHeaders();
        return this.http.get<TaskDto>(`${this.apiUrl}/${taskId}`, { headers });
    }

    // Create a new task
    createTask(task: TaskCreationDto): Observable<TaskCreationDto> {
        const headers = this.getAuthHeaders();
        return this.http.post<TaskCreationDto>(`${this.apiUrl}/create`, task, {
            headers,
        });
    }

    // Update an existing task
    updateTask(task: TaskDto): Observable<ApiResponse> {
        const headers = this.getAuthHeaders();
        return this.http.put<ApiResponse>(
            `${this.apiUrl}/update/${task.id}`,
            task,
            { headers }
        );
    }
    updateTaskStatus(taskId: number, status: string): Observable<TaskDto> {
        const url = `${this.apiUrl}/updateStatus/${taskId}`;
        const headers = this.getAuthHeaders();

        return this.http.put<TaskDto>(url, { status }, { headers });
    }


    // Delete a task by its ID
    deleteTask(taskId: number): Observable<void> {
        const headers = this.getAuthHeaders();
        return this.http.delete<void>(`${this.apiUrl}/delete/${taskId}`, { headers });
    }


}
