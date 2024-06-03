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
    formRegister: FormGroup;
    loginForm: FormGroup;
    formForgotPass: FormGroup;

    emailRegex =
        /[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}/;

    constructor(
        private fb: FormBuilder,
        private router: Router,
        private authService: AuthService
    ) {
        this.formRegister = this.fb.group({
            username: ['', [Validators.required]],
            email: ['', [Validators.required]],
            password: [
                '',
                [Validators.required, Validators.minLength(6)],
            ],
        });
        this.loginForm = this.fb.group({
            email: [
                '',
                [Validators.required, Validators.pattern(this.emailRegex)],
            ],
            password: ['', Validators.required],
        });
        this.formForgotPass = this.fb.group({
            email: [
                '',
                [Validators.required, Validators.pattern(this.emailRegex)],
            ],
        });
    }

    submitFormRegister() {
        console.log('submited');

        if (this.formRegister.valid) {
            const userData = {
                username: this.formRegister.get('username')?.value,
                email: this.formRegister.get('registerEmail')?.value,
                password: this.formRegister.get('registerPassword')?.value,
                callbackUrl:
                    'https://c18-32-ft-java-angular-front-end.vercel.app/', // specify the callback URL here
                redirectUrl:
                    'https://c18-32-ft-java-angular-front-end.vercel.app/', // specify the redirect URL here
            };

            console.log('userData', userData);

            this.authService.register(userData).subscribe(
                (response) => {
                    console.log('Registration successful', response);
                    // Handle success, e.g., navigate to a confirmation page
                    this.router.navigate(['/']);
                },
                (error) => {
                    console.log('Registration failed', error);
                    // Handle error, e.g., display an error message
                }
            );
        } else {
            console.error('Form is invalid');
        }
    }

    submitFormLogin() {
        if (this.loginForm.valid) {
            const loginData = {
                email: this.loginForm.get('email')?.value,
                password: this.loginForm.get('password')?.value,
                emailVerificationCallbackUrl:
                    'http://your-app-url.com/verification', // Update with your verification URL
            };

            this.authService.login(loginData).subscribe(
                (response: any) => {
                    sessionStorage.setItem('accessToken', response.accessToken);
                    this.router.navigate(['/']); // Navigate to dashboard after successful login
                },
                (error) => {
                    console.error('Login failed', error);
                    // Handle login error, e.g., display an error message to the user
                }
            );
        } else {
            console.error('Form is invalid');
        }
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
