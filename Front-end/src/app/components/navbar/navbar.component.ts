import { Component, inject } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
    selector: 'app-navbar',
    standalone: true,
    imports: [RouterLink, ReactiveFormsModule],
    templateUrl: './navbar.component.html',
    styleUrl: './navbar.component.css',
})
export class NavbarComponent {
    formForgotPass: FormGroup;
    emailRegex =
        /[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}/;

    constructor(
        private fb: FormBuilder,
        private router: Router,
        private authService: AuthService
    ) {
        this.formForgotPass = this.fb.group({
            email: [
                '',
                [Validators.required, Validators.pattern(this.emailRegex)],
            ],
        });
    }

    submitFormForgotPass() {
        if (this.formForgotPass.valid) {
            const emailData = {
                email: this.formForgotPass.get('email')?.value,
                callbackUrl: 'https://c18-32-ft-java-angular-front-end.vercel.app/reset-password', // specify the callback URL here
                redirectUrl: 'https://c18-32-ft-java-angular-front-end.vercel.app/', // specify the redirect URL here
            };

            this.authService.sendEmailPasswordRecovery(emailData).subscribe(
                (response) => {
                    console.log('Email sent successfully', response);
                    // Handle success, e.g., display a message or navigate to another page
                    alert(response.message);
                    this.router.navigate(['/']);
                },
                (error) => {
                    console.log('Email sending failed', error);
                    // Handle error
                    alert('Failed to send email. Please try again later.');
                }
            );
        } else {
            console.error('Form is invalid');
        }
    }
}
