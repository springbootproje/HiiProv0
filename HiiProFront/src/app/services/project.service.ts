import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
    ProjectCreationDto,
    ProjectSummaryDto,
    ProjectEntity,
    ApiResponse, TaskDto,
} from '../demo/components/projects/project.model';

@Injectable({
    providedIn: 'root',
})
export class ProjectService {
    private apiUrl = 'http://localhost:8080/project'; // Update with your API URL

    constructor(private http: HttpClient) {}

    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('accessToken');
        return new HttpHeaders({
            Authorization: `Bearer ${token}`,
        });
    }

    getProject(id: number): Observable<ProjectSummaryDto> {
        return this.http.get<ProjectSummaryDto>(`${this.apiUrl}/${id}`);
    }

    createProject(project: ProjectCreationDto): Observable<any> {
        const headers = this.getAuthHeaders();
        return this.http.post<any>(`${this.apiUrl}/new`, project, { headers });
    }

    getProjects(): Observable<ProjectSummaryDto[]> {
        const headers = this.getAuthHeaders();
        return this.http.get<ProjectSummaryDto[]>(`${this.apiUrl}/list`, {
            headers,
        });
    }

    getProjectById(id: number): Observable<ProjectSummaryDto> {
        const headers = this.getAuthHeaders();
        return this.http.get<ProjectSummaryDto>(`${this.apiUrl}/${id}`, {
            headers,
        });
    }

    updateProject(
        id: number,
        project: ProjectEntity
    ): Observable<ProjectEntity> {
        const headers = this.getAuthHeaders();
        return this.http.put<ProjectEntity>(
            `${this.apiUrl}/update/${id}`,
            project,
            { headers }
        );
    }

    deleteProject(id: number): Observable<ApiResponse> {
        const headers = this.getAuthHeaders();
        return this.http.delete<ApiResponse>(`${this.apiUrl}/delete/${id}`, {
            headers,
        });
    }

    searchProjectsByTitle(title: string): Observable<ProjectSummaryDto[]> {
        const headers = this.getAuthHeaders();
        return this.http.get<ProjectSummaryDto[]>(`${this.apiUrl}/search`, {
            headers,
            params: { title },
        });
    }

    getProjectsFromStartDate(startDate: string): Observable<ProjectEntity[]> {
        const headers = this.getAuthHeaders();
        return this.http.get<ProjectEntity[]>(`${this.apiUrl}/date`, {
            headers,
            params: { start: startDate },
        });
    }
}
