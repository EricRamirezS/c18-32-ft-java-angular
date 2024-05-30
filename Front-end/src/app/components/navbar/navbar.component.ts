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

    emailData = {
        email: '',
        callbackUrl: 'http://your-app-url.com/callback', // specify the callback URL here
        redirectUrl: 'http://your-app-url.com/redirect', // specify the redirect URL here
    };

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
        this.authService.sendEmailPasswordRecovery(this.emailData).subscribe(
            (response) => {
                console.log('Email sent successfully', response);
                // Handle success, e.g., display a message or navigate to another page
                alert(response.message);
                this.router.navigate(['/']);
            },
            (error) => {
                console.log('Email sending failed', error);
                // Handle error
            }
        );
    }
}
