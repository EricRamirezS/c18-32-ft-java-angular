import { Component, inject } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
    selector: 'app-reset-password',
    standalone: true,
    imports: [RouterLink, ReactiveFormsModule],
    templateUrl: './reset-password.component.html',
    styleUrl: './reset-password.component.css',
})
export class ResetPasswordComponent {
    formResetPass: FormGroup;
    passwordRegex = /^.{6,}$/;

    token: string = '';
    newPassword: string = '';

    constructor(private fb: FormBuilder,private authService: AuthService) {
        this.formResetPass = this.fb.group({
            newPassword: [
                '',
                [Validators.required, Validators.pattern(this.passwordRegex)],
            ],
        });
    }

    // submitFormResetPass(){
    //     if (this.formResetPass.valid) {
    //         // Handle form submission
    //     }
    // }

    changeForgottenPassword(): void {
        if (!this.token || !this.newPassword) {
            console.error('All fields are required.');
            return;
        }

        this.authService
            .changeForgottenPassword({ token: this.token, newPassword: this.newPassword })
            .subscribe(
                (response) => {
                    console.log('Password changed successfully:', response);
                    // Handle success
                },
                (error) => {
                    console.error('Failed to change password:', error);
                    // Handle error
                }
            );
    }
}
