import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';
import { MessageService } from 'primeng/api';
import { ProjectEntity } from '../project.model';

@Component({
    selector: 'app-project-edit',
    templateUrl: './project-edit.component.html',
    providers: [MessageService],
})
export class ProjectEditComponent implements OnInit {
    projectForm: FormGroup;
    users: any[] = [];
    projectId: number;

    constructor(
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private projectService: ProjectService,
        private userService: UserService,
        private messageService: MessageService,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.projectId = +this.route.snapshot.paramMap.get('id');
        this.projectForm = this.fb.group({
            title: ['', Validators.required],
            description: ['', Validators.required],
            createDate: [null, Validators.required],
            creatorUserId: [null, Validators.required],
            members: [[]],
        });

        this.loadUsers();
        this.loadProject();
    }

    loadUsers(): void {
        this.userService.getUsers().subscribe((users) => {
            this.users = users;
        });
    }

    loadProject(): void {
        this.projectService
            .getProjectById(this.projectId)
            .subscribe((project) => {
                this.projectForm.patchValue(project);
            });
    }

    onSubmit(): void {
        if (this.projectForm.valid) {
            const project: ProjectEntity = this.projectForm.value;
            this.projectService
                .updateProject(this.projectId, project)
                .subscribe(
                    (response) => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Success',
                            detail: 'Project updated successfully!',
                        });
                        this.router.navigate(['/project']); // Navigate to project list after update
                    },
                    (error) => {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Error',
                            detail: 'Failed to update project',
                        });
                    }
                );
        } else {
            this.messageService.add({
                severity: 'warn',
                summary: 'Validation',
                detail: 'Please fill all required fields',
            });
        }
    }
}
