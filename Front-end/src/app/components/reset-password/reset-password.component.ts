import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.css'
})
export class ResetPasswordComponent {
    formResetPass: FormGroup;
    passwordRegex = /^.{6,}$/;

    constructor(private fb: FormBuilder) {
        this.formResetPass = this.fb.group({
            newPassword: ['', [Validators.required, Validators.pattern(this.passwordRegex)]]
        })
    }

    submitFormResetPass(){
        if (this.formResetPass.valid) {
            // Handle form submission
        }
    }

}
