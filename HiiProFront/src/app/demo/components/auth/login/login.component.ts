import { Component } from '@angular/core';
import { LayoutService } from 'src/app/layout/service/app.layout.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/services/auth.service';
import { UserLogin } from 'src/app/interfaces/auth';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
})
export class LoginComponent {
    loginForm = this.fb.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
    });

    constructor(
        private fb: FormBuilder,
        private authService: AuthService,
        private router: Router,
        private msgService: MessageService,
        public layoutService: LayoutService
    ) {}

    get email() {
        return this.loginForm.controls['email'];
    }
    get password() {
        return this.loginForm.controls['password'];
    }

    loginUser() {
        const { email, password } = this.loginForm.value;
        this.authService.login({ email, password } as UserLogin).subscribe({
            next: (response: any) => {
                console.log(response);
                const token = response.accessToken;
                localStorage.removeItem('accessToken');
                localStorage.setItem('accessToken', token); // Save token in local storage
                sessionStorage.setItem('email', email as string);
                this.msgService.add({
                    severity: 'success',
                    summary: 'Success',
                    detail: 'Login successful',
                });
                this.router.navigate(['/']);
            },
            error: (error) => {
                this.msgService.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Something went wrong',
                });
                console.error('Login error:', error);
            },
        });
    }
}
