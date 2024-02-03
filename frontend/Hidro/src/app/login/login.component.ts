import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";

import { Toaster } from "ngx-toast-notifications";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user!: string;
  password!: string;

  me=false

  constructor(public router: Router,
              private toaster: Toaster) { }

  ngOnInit(): void {
  }

  login() {
    console.log(this.user);
    console.log(this.password);

    // const user = {user: this.user, password: this.password}
    // this.usersSErvice.login(user).subscribe(
    //   data=>{
    //     console.log('token: ',data)
    //     this.router.navigateByUrl('/prueba')
    //     console.log('tokenito: ',data)
    // })

    if (this.user && this.user.trim()!='' && this.password){
      if (this.user.toLowerCase()==='admin' && this.password.toLowerCase()==='admin'){
        // this.router.navigateByUrl('/')
        this.router.navigateByUrl('/app')
      } 
      else this.showToast('danger', 'Usuario y/o contraseña no válidos.')
    }
    else this.showToast('warning', 'Debe introducir un usuario y una contraseña.')
    

  }

  // Toaster
  /* typeMessage: ['success', 'danger', 'warning', 'info', 'primary', 'secondary', 'dark', 'light']; */
  showToast(typeMessage: any, messageText: any) {
    this.toaster.open({
      text: messageText,
      type: typeMessage,
      position: 'top-right'
    });
  }
}
