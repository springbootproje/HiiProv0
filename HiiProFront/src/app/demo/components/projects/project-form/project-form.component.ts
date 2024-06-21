import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { ProjectService } from 'src/app/services/project.service';
import { MessageService } from 'primeng/api';
import { ProjectCreationDto } from '../project.model';
import { UserDto } from 'src/app/interfaces/user-dto';
import { UserOption } from 'src/app/interfaces/user-option';

@Component({
    selector: 'app-project-form',
    templateUrl: './project-form.component.html',
    styleUrls: ['./project-form.component.scss'],
    providers: [MessageService],
})
export class ProjectFormComponent implements OnInit {
    projectForm: FormGroup;
    users: UserOption[] = [];
    isLoading: boolean = false; // Loading state
    @Output() projectCreated = new EventEmitter<void>(); // Event emitter to notify project creation
    role: string | null = localStorage.getItem('role');
    constructor(
        private fb: FormBuilder,
        private userService: UserService,
        private projectService: ProjectService,
        private messageService: MessageService
    ) {}

    ngOnInit(): void {
        this.projectForm = this.fb.group({
            title: ['', Validators.required],
            description: ['', Validators.required],
            members: [[], Validators.required],
        });

        this.loadUsers();
    }

    loadUsers(): void {
        this.userService.getUsers().subscribe({
            next: (users: UserDto[]) => {
                this.users = users.map((user) => ({
                    label: `${user.firstName} ${user.lastName}`,
                    value: user.id,
                }));
            },
            error: (error) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Failed to load users',
                });
            },
        });
    }

    onSubmit(): void {
        if (this.role !== 'TEACHER') {
            this.messageService.add({
                severity: 'error',
                summary: 'Access Denied',
                detail: 'Only teachers can create projects',
            });
            return;
        }
        if (this.projectForm.valid) {
            this.isLoading = true; // Start loading
            const project: ProjectCreationDto = {
                title: this.projectForm.value.title,
                description: this.projectForm.value.description,
                memberIds: this.projectForm.value.members.map(
                    (member: any) => member.value
                ),
            };

            this.projectService.createProject(project).subscribe({
                next: (response) => {
                    this.isLoading = false; // Stop loading
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Project created successfully!',
                    });
                    this.projectCreated.emit(); // Emit event on successful creation
                    this.resetForm(); // Reset the form
                },
                error: (error) => {
                    console.log(error);
                    this.isLoading = false; // Stop loading
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Failed to create project',
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

    resetForm(): void {
        this.projectForm.reset();
        this.projectForm.markAsPristine();
        this.projectForm.markAsUntouched();
    }
}
