import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {NotificationService} from "../../service/notification.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  // public registerForm: FormGroup;

  constructor(
    private authService: AuthService,
    public registerForm: FormGroup,
    private notificationService: NotificationService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.registerForm = this.createRegisterForm();
  }

  createRegisterForm(): FormGroup {
    return this.fb.group({
      email: ['', Validators.compose([Validators.required, Validators.email])],
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required])]
    });
  }

  submit(): void {
    console.log(this.registerForm.value);

    this.authService.register({
      email: this.registerForm.value.email,
      username: this.registerForm.value.username,
      password: this.registerForm.value.password,
    }).subscribe(data => {
      console.log(data);
      this.notificationService.showSnackBar('Successfully Registered!');
    }, error => {
      this.notificationService.showSnackBar('Something went wrong during registration');
    });
  }
}
