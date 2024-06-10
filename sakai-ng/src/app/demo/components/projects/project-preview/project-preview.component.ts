import { Component, OnInit } from '@angular/core';
import { ProjectService } from 'src/app/services/project.service';
import { TaskService } from 'src/app/services/task.service';
import { ProjectSummaryDto, TaskDto } from '../project.model';
import {
    CdkDragDrop,
    moveItemInArray,
    transferArrayItem,
} from '@angular/cdk/drag-drop';
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
    taskStatusForNew: string = ''; // This will hold the status for new tasks
    taskColumns = [
        { title: 'Backlog', status: 'backlog' },
        { title: 'To Do', status: 'todo' },
        { title: 'Doing', status: 'doing' },
        { title: 'Done', status: 'done' },
    ];
    users: { label: string; value: number }[] = []; // List of project members
    statuses: { label: string; value: string }[] = [
        { label: 'Not Started', value: 'not_started' },
        { label: 'To Do', value: 'todo' },
        { label: 'Doing', value: 'doing' },
        { label: 'Done', value: 'done' },
    ];

    constructor(
        private projectService: ProjectService,
        private taskService: TaskService,
        private messageService: MessageService,
        private route: ActivatedRoute
    ) {}

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
            console.log(project);

            // Organize tasks after they are loaded
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
            // Ensure the task status matches the column status used in taskColumns
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
        this.displayTaskDetailsDialog = true;
    }

    showCreateTaskDialog(status: string): void {
        // Map the column status to the task status
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
        this.loadProject(); // Reload tasks after a new one is created
    }

    onTaskDrop(event: CdkDragDrop<TaskDto[]>): void {
        if (event.previousContainer === event.container) {
            moveItemInArray(
                event.container.data,
                event.previousIndex,
                event.currentIndex
            );
        } else {
            transferArrayItem(
                event.previousContainer.data,
                event.container.data,
                event.previousIndex,
                event.currentIndex
            );
            const task = event.container.data[event.currentIndex];
            task.status = this.getStatusFromContainer(event.container.id);
            this.taskService.updateTaskStatus(task.id, task.status).subscribe({
                next: () => {
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Task status updated successfully!',
                    });
                },
                error: () => {
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Failed to update task status',
                    });
                    this.loadProject(); // Revert changes on error
                },
            });
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
        return +this.route.snapshot.paramMap.get('id')!; // Get project ID from the route parameters and convert it to a number
    }
}
