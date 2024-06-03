import { Component, inject } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import Swal from 'sweetalert2';

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
                callbackUrl:
                    'https://c18-32-ft-java-angular-front-end.vercel.app/reset-password',
                redirectUrl:
                    'https://c18-32-ft-java-angular-front-end.vercel.app/',
            };

            console.log('emailData', emailData);

            this.authService.sendEmailPasswordRecovery(emailData).subscribe(
                (response) => {
                    console.log('Email sent successfully', response);
                    Swal.fire({
                        title: 'Great! now ...',
                        text: 'Check your email for the next step',
                        imageUrl:
                            'https://mailmeteor.com/logos/assets/PNG/Gmail_Logo_512px.png',
                        imageWidth: 400,
                        imageHeight: 200,
                        imageAlt: 'email-image',
                    });
                    this.router.navigate(['/']);
                },
                (error) => {
                    console.error('Email sending failed', error);
                    if (error.status === 401) {
                        console.error(
                            'Unauthorized access - possibly backend logic error or CORS issue'
                        );
                    }
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Something went wrong! Failed to send email. Please try again later.',
                    });
                }
            );
        } else {
            console.error('Form is invalid');
        }
    }
}
