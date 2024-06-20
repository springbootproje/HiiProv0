// Importations nécessaires
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProjectService } from 'src/app/services/project.service';
import { TaskService } from 'src/app/services/task.service';
import { ProjectSummaryDto, TaskDto } from '../project.model';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { MessageService } from 'primeng/api';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-project-preview',
    templateUrl: './project-preview.component.html',
    styleUrls: ['./project-preview.component.scss'],
    providers: [MessageService],
})
export class ProjectPreviewComponent implements OnInit {
    project: ProjectSummaryDto;
    tasks: TaskDto[] = [];
    filteredTasks: { [key: string]: TaskDto[] } = {
        backlog: [],
        todo: [],
        doing: [],
        done: [],
    };
    taskSearchTerm: string = '';
    displayTaskDetailsDialog: boolean = false;
    displayCreateTaskDialog: boolean = false;
    selectedTask: TaskDto | null = null;
    taskStatusForNew: string = '';
    taskColumns = [
        { title: 'Backlog', status: 'backlog' },
        { title: 'To Do', status: 'todo' },
        { title: 'Doing', status: 'doing' },
        { title: 'Done', status: 'done' },
    ];
    users: { label: string; value: number }[] = [];
    statuses: { label: string; value: string }[] = [
        { label: 'Not Started', value: 'not_started' },
        { label: 'To Do', value: 'todo' },
        { label: 'Doing', value: 'doing' },
        { label: 'Done', value: 'done' },
    ];

    // Formulaire pour l'édition des tâches
    taskEditForm: FormGroup;

    constructor(
        private fb: FormBuilder,
        private projectService: ProjectService,
        private taskService: TaskService,
        private messageService: MessageService,
        private route: ActivatedRoute
    ) {
        this.taskEditForm = this.fb.group({
            title: [''],
            description: [''],
            status: [''],
            userId: [null]
        });
    }

    ngOnInit(): void {
        this.loadProject();
    }

    loadProject(): void {
        const projectId = this.getProjectIdFromRoute();
        this.projectService.getProjectById(projectId).subscribe((project) => {
            this.project = project;
            this.tasks = project.tasks || [];

            this.users = project.members.map((member) => ({
                label: `${member.firstName} ${member.lastName}`,
                value: member.id,
            }));

            this.organizeTasks();
        });
    }

    organizeTasks(): void {
        this.filteredTasks = {
            backlog: [],
            todo: [],
            doing: [],
            done: [],
        };
        this.tasks.forEach((task) => {
            switch (task.status) {
                case 'not_started':
                    this.filteredTasks['backlog'].push(task);
                    break;
                case 'todo':
                    this.filteredTasks['todo'].push(task);
                    break;
                case 'doing':
                    this.filteredTasks['doing'].push(task);
                    break;
                case 'done':
                    this.filteredTasks['done'].push(task);
                    break;
                default:
                    break;
            }
        });
    }

    filterTasks(): void {
        if (this.taskSearchTerm) {
            const searchTermLower = this.taskSearchTerm.toLowerCase();
            this.filteredTasks = {
                backlog: this.tasks.filter(
                    (task) =>
                        task.status === 'not_started' &&
                        task.description.toLowerCase().includes(searchTermLower)
                ),
                todo: this.tasks.filter(
                    (task) =>
                        task.status === 'todo' &&
                        task.description.toLowerCase().includes(searchTermLower)
                ),
                doing: this.tasks.filter(
                    (task) =>
                        task.status === 'doing' &&
                        task.description.toLowerCase().includes(searchTermLower)
                ),
                done: this.tasks.filter(
                    (task) =>
                        task.status === 'done' &&
                        task.description.toLowerCase().includes(searchTermLower)
                ),
            };
        } else {
            this.organizeTasks();
        }
    }

    showTaskDetails(task: TaskDto): void {
        this.selectedTask = task;
        // Initialiser le formulaire de modification avec les détails de la tâche sélectionnée
        this.taskEditForm.patchValue({
            title: task.title,
            description: task.description,
            status: task.status,
            userId: task.userId
        });
        this.displayTaskDetailsDialog = true;
    }

    showCreateTaskDialog(status: string): void {
        this.taskStatusForNew = this.mapColumnStatusToTaskStatus(status);
        this.displayCreateTaskDialog = true;
    }

    mapColumnStatusToTaskStatus(columnStatus: string): string {
        switch (columnStatus) {
            case 'backlog':
                return 'not_started';
            case 'todo':
                return 'todo';
            case 'doing':
                return 'doing';
            case 'done':
                return 'done';
            default:
                return 'not_started';
        }
    }

    onTaskCreated(): void {
        this.displayCreateTaskDialog = false;
        this.loadProject();
    }

    updateTask(): void {
        if (this.selectedTask) {
            const updatedTask = { ...this.selectedTask, ...this.taskEditForm.value };
            this.taskService.updateTask(updatedTask).subscribe({
                next: (response) => {
                    console.log("Tâche mise à jour avec succès :", response);
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Succès',
                        detail: 'Les détails de la tâche ont été mis à jour avec succès!',
                    });
                    this.loadProject(); // Recharger le projet pour mettre à jour les tâches
                    this.displayTaskDetailsDialog = false; // Fermer le dialogue
                },
                error: (error) => {
                    console.error("Erreur lors de la mise à jour de la tâche :", error);
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Erreur',
                        detail: 'Échec de la mise à jour de la tâche',
                    });
                }
            });
        }
    }

    deleteTask(taskId: number): void {
        if (confirm('Êtes-vous sûr de vouloir supprimer cette tâche ?')) {
            this.taskService.deleteTask(taskId).subscribe({
                next: () => {
                    console.log('Tâche supprimée avec succès');
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Succès',
                        detail: 'Tâche supprimée avec succès!',
                    });
                    this.loadProject(); // Recharger le projet pour mettre à jour les tâches
                },
                error: (error) => {
                    console.error('Erreur lors de la suppression de la tâche :', error);
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Erreur',
                        detail: 'Échec de la suppression de la tâche',
                    });
                },
            });
        }
    }

    onTaskDrop(event: CdkDragDrop<TaskDto[]>): void {
        if (event.previousContainer === event.container) {
            // Réorganiser dans la même colonne
            moveItemInArray(
                event.container.data,
                event.previousIndex,
                event.currentIndex
            );
        } else {
            // Transférer entre les colonnes
            transferArrayItem(
                event.previousContainer.data,
                event.container.data,
                event.previousIndex,
                event.currentIndex
            );

            // Mettre à jour le statut de la tâche basé sur la nouvelle colonne
            const task = event.container.data[event.currentIndex];
            const newStatus = this.getStatusFromContainer(event.container.id);

            if (task.status !== newStatus) {
                task.status = newStatus;

                // Mettre à jour la tâche dans le service backend
                this.taskService.updateTaskStatus(task.id, newStatus).subscribe({
                    next: (updatedTask) => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Succès',
                            detail: 'Le statut de la tâche a été mis à jour avec succès!',
                        });
                    },
                    error: (error) => {
                        console.error('Échec de la mise à jour du statut de la tâche', error);
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Erreur',
                            detail: 'Échec de la mise à jour du statut de la tâche',
                        });
                        this.loadProject(); // Recharger les tâches en cas d'erreur
                    },
                });
            }
        }
    }

    getStatusFromContainer(containerId: string): string {
        switch (containerId) {
            case 'cdk-drop-list-0':
                return 'not_started';
            case 'cdk-drop-list-1':
                return 'todo';
            case 'cdk-drop-list-2':
                return 'doing';
            case 'cdk-drop-list-3':
                return 'done';
            default:
                return '';
        }
    }

    getProjectIdFromRoute(): number {
        return +this.route.snapshot.paramMap.get('id')!;
    }

    getUserName(userId: number): string {
        const user = this.users.find((u) => u.value === userId);
        return user ? user.label : 'Non assigné';
    }

}
