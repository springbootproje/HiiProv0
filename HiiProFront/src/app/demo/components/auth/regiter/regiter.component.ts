import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { User } from 'src/app/interfaces/auth';
import { AuthService } from 'src/app/services/auth.service';
import { passwordMatchValidator } from '../../shared/password-mach.directive';
import { LayoutService } from 'src/app/layout/service/app.layout.service';
@Component({
    selector: 'app-register',
    templateUrl: './regiter.component.html',
    styleUrls: ['./regiter.component.scss'],
})
export class RegiterComponent {
    registerForm = this.fb.group(
        {
            firstName: [
                '',
                [
                    Validators.required,
                    Validators.pattern(/^[a-zA-Z]+(?: [a-zA-Z]+)*$/),
                ],
            ],
            lastName: [
                '',
                [
                    Validators.required,
                    Validators.pattern(/^[a-zA-Z]+(?: [a-zA-Z]+)*$/),
                ],
            ],
            role: ['', [Validators.required]],
            email: ['', [Validators.required, Validators.email]],
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required],
        },
        {
            validators: passwordMatchValidator,
        }
    );

    constructor(
        public layoutService: LayoutService,
        private fb: FormBuilder,
        private authService: AuthService,
        private messageService: MessageService,
        private router: Router
    ) {}

    get firstName() {
        return this.registerForm.controls['firstName'];
    }
    get lastName() {
        return this.registerForm.controls['lastName'];
    }
    get role() {
        return this.registerForm.controls['role'];
    }

    get email() {
        return this.registerForm.controls['email'];
    }

    get password() {
        return this.registerForm.controls['password'];
    }

    get confirmPassword() {
        return this.registerForm.controls['confirmPassword'];
    }

    submitDetails() {
        const postData = { ...this.registerForm.value };
        delete postData.confirmPassword;
        this.authService.registerUser(postData as User).subscribe({
            next: (response) => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Success',
                    detail: 'Registered successfully',
                });
                console.log('success');
                this.router.navigate(['auth/login']);
            },
            error: (error) => {
                console.log('Failed to register:', error);
                this.messageService.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Something went wrong',
                });
            },
        });
    }
}
