import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-panel',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './user-panel.component.html',
  styleUrl: './user-panel.component.css',
})
export class UserPanelComponent {
  formEditUser: FormGroup;
  user: any = { name: 'Antonia', age: '27', country: 'Uruguay' };
  urlUserPhoto = "https://as2.ftcdn.net/v2/jpg/03/49/49/79/1000_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg"
  isEditMode = false;

  constructor(private fb: FormBuilder) {
    this.formEditUser = this.fb.group({
      name: [''],
      age: [''],
      country: [''],
    });
    this.formEditUser.patchValue(this.user);
  }

  onSelectFile(event:any){
    if(event.target.files){
      let reader = new FileReader();
      reader.readAsDataURL(event.target.files[0])
      reader.onload = (event:any) => {
        this.urlUserPhoto = event.target.result;
      }
    }
  }

  enterEditMode() {
    // Switch to edit mode
    this.isEditMode = true;
  }

  saveChanges() {
    // Save changes and exit edit mode
    // You can implement logic here to send updated user information to the server
    this.isEditMode = false;
  }

  cancelEdit() {
    // Cancel editing and return to view mode
    // You can implement logic here to reset input fields if needed
    this.isEditMode = false;
  }
}
