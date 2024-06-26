
import { Component } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import {PasswordModule} from "primeng/password";
import {PanelModule} from "primeng/panel";
import {DialogModule} from "primeng/dialog";
import {CardModule} from "primeng/card";
@Component({
  selector: 'app-change-password',
  standalone: true,
    imports: [
        ReactiveFormsModule,
        PasswordModule,
        PanelModule,
        DialogModule,
        CardModule
    ],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.scss'
})
export class ChangePasswordComponent {
    changePasswordForm: FormGroup;
    display: boolean = false;


    constructor(
        private fb: FormBuilder,
        private authService: AuthService,
        private msgService: MessageService,
        private router: Router
    ) {
        this.changePasswordForm = this.fb.nonNullable.group({
            currentPassword: ['', Validators.required],
            newPassword: ['', [Validators.required, Validators.minLength(6)]],
            confirmNewPassword: ['', Validators.required]
        }, {
            validators: this.passwordMatchValidator
        });
    }
    //start
    ngOnInit(): void {
        this.display = true; // Automatically show the dialog on component init
    }
    onDialogHide() {
        this.changePasswordForm.reset();
    }
    get currentPassword(): AbstractControl {
        return this.changePasswordForm.get('currentPassword')!;
    }

    get newPassword(): AbstractControl {
        return this.changePasswordForm.get('newPassword')!;
    }

    get confirmNewPassword(): AbstractControl {
        return this.changePasswordForm.get('confirmNewPassword')!;
    }

    passwordMatchValidator(form: AbstractControl): { mismatch: boolean } | null {
        return form.get('newPassword')!.value === form.get('confirmNewPassword')!.value
            ? null : { mismatch: true };
    }

    changePassword(): void {
        if (this.changePasswordForm.invalid) {
            this.msgService.add({
                severity: 'error',
                summary: 'Error',
                detail: 'Please provide valid inputs'
            });
            return;
        }

        const { currentPassword, newPassword } = this.changePasswordForm.value;
        console.log("Sending password change request:", { currentPassword, newPassword });

        this.authService.changePassword(currentPassword, newPassword).subscribe({
            next: (response: any) => {
                console.log('Password change response:', response);
                this.msgService.add({
                    severity: 'success',
                    summary: 'Success',
                    detail: 'Password changed successfully'
                });
                this.router.navigate(['/']); // Redirige vers la page d'accueil après le changement de mot de passe
            },
            error: (error) => {
                console.error('Password change error:', error);
                this.msgService.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Password change failed'
                });
            }
        });
    }
}
