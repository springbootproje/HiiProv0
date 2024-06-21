import { Component, OnInit } from '@angular/core';
import { ProjectService } from 'src/app/services/project.service';
import { ProjectSummaryDto } from '../project.model';
import { UserDto } from 'src/app/interfaces/user-dto';
import { MessageService, ConfirmationService } from 'primeng/api'; // Import ConfirmationService
import { Route, Router } from '@angular/router';

@Component({
    selector: 'app-project-list',
    templateUrl: './project-list.component.html',
    styleUrls: ['./project-list.component.scss'],
    providers: [MessageService, ConfirmationService],
})
export class ProjectListComponent implements OnInit {
    projects: ProjectSummaryDto[] = [];
    filteredProjects: ProjectSummaryDto[] = [];
    displayCreateProjectDialog: boolean = false;
    searchTerm: string = '';
    role: string | null = null;
    userId: string | null = localStorage.getItem('id');
    selectedProject: ProjectSummaryDto | null = null; // Selected project for editing

    constructor(
        private projectService: ProjectService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private router: Router // Inject ConfirmationService
    ) {}

    ngOnInit(): void {
        this.role = localStorage.getItem('role');
        this.loadProjects();
    }

    loadProjects(): void {
        this.projectService.getProjects().subscribe((projects) => {
            this.projects = projects;
            this.filteredProjects = this.projects;
        });
    }
    filterProjects(): void {
        if (this.role === 'TEACHER') {
            this.filteredProjects = this.projects;
        } else if (this.role === 'STUDENT') {
            this.filteredProjects = this.projects.filter(project => this.isProjectMember(project));
        }

        if (this.searchTerm) {
            this.filteredProjects = this.filteredProjects.filter((project) =>
                project.title.toLowerCase().includes(this.searchTerm.toLowerCase())
            );
        }
    }
    isProjectMember(project: ProjectSummaryDto): boolean {
        return project.members.some(member => member.id === Number(this.userId));
    }

    deleteProject(id: number): void {
        if (this.role !== 'TEACHER') {
            this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: 'Only teachers can delete projects',
            });
            return;
        }
        this.projectService.deleteProject(id).subscribe({
            next: (response) => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Success',
                    detail: response.message,
                });
                this.loadProjects();
            },
            error: (error) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Failed to delete project',
                });
            },
        });
    }

    showCreateProjectDialog(): void {
        if (this.role !== 'TEACHER') {
            this.messageService.add({
                severity: 'error',
                summary: 'Access Denied',
                detail: 'Only teachers can create projects',
            });
            return;
        }
        this.displayCreateProjectDialog = true;
        this.selectedProject = null; // Clear selected project
    }

    onProjectCreated(): void {
        this.displayCreateProjectDialog = false;
        this.loadProjects(); // Reload projects after a new one is created
    }

    showProjectDetails(projectId: number): void {
        // This can navigate to a new page, or open a dialog
        this.router.navigate([`/project/preview/${projectId}`]); // Replace with your actual routing logic
    }

    editProject(project: ProjectSummaryDto): void {

        if (this.role !== 'TEACHER') {
            this.messageService.add({
                severity: 'error',
                summary: 'Access Denied',
                detail: 'Only teachers can edit projects',
            });
            return;
        }


        this.selectedProject = project; // Set the selected project
        this.displayCreateProjectDialog = true; // Open the dialog for editing
    }


    getMembersTooltipText(members: UserDto[]): string {
        return members.length
            ? members.map((m) => `${m.firstName} ${m.lastName}`).join(', ')
            : 'No members';
    }

    confirmDeleteProject(id: number): void {
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete this project?',
            accept: () => {
                this.deleteProject(id);
            },
        });
    }
}
