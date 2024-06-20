import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaskService } from 'src/app/services/task.service';
import { MessageService } from 'primeng/api';
import { TaskCreationDto, TaskDto } from '../project.model';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-task-form',
    templateUrl: './task-form.component.html',
    styleUrls: ['./task-form.component.scss'],
})
export class TaskFormComponent implements OnInit {
    @Input() initialStatus: string = 'not_started'; // Default to 'not_started' if no initial status is provided
    @Input() projectId: number; // Input for the project ID
    @Input() users: { label: string; value: number }[] = []; // Input list of users for assignment dropdown
    @Input() statuses: { label: string; value: string }[] = []; // Input list of statuses for status dropdown
    @Output() taskSubmitted = new EventEmitter<TaskCreationDto>();

    taskForm: FormGroup;
    isLoading: boolean = false; // Manage the loading state

    constructor(
        private fb: FormBuilder,
        private taskService: TaskService,
        private messageService: MessageService,
        private route: ActivatedRoute
    ) {}

    ngOnInit(): void {
        this.taskForm = this.fb.group({
            title: ['', Validators.required], // Added title field
            description: ['', Validators.required],
            status: [this.initialStatus, Validators.required], // Apply the initialStatus to the form control
            user: [null], // Initialize without validation, can be optional
        });
    }

    onSubmit(): void {
        if (this.taskForm.valid) {
            this.isLoading = true; // Set loading to true when submission starts
            const taskData: TaskCreationDto = {
                title: this.taskForm.value.title,
                description: this.taskForm.value.description,

                status: this.taskForm.value.status.value, // Extract the 'value' of status
                userId: this.taskForm.value.user?.value || null, // Extract the 'value' of user or set to null if not assigned
                projectId: +this.route.snapshot.paramMap.get('id')!, // Include the projectId
            };
            console.log(taskData);
            this.taskService.createTask(taskData).subscribe({
                next: (task) => {
                    this.isLoading = false; // Reset loading state on success
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Task created successfully!',
                    });
                    this.taskSubmitted.emit(task); // Emit the created task
                    this.taskForm.reset({ status: this.initialStatus }); // Reset the form with initial status
                },
                error: (error) => {
                    this.isLoading = false; // Reset loading state on error
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Failed to create task',
                    });
                },
            });
        } else {
            this.messageService.add({
                severity: 'warn',
                summary: 'Validation',
                detail: 'Please fill all required fields',
            });
        }
    }
}
