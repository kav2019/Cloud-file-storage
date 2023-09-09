import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from "./material-module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {authInterceptorProviders} from "./helper/auth-interceptor.service";
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import {authErrorInterceptorProviders} from "./helper/error-interceptor.service";
import { IndexComponent } from './layout/index/index.component';
import { NavigationComponent } from './layout/navigation/navigation.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    IndexComponent,
    NavigationComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    MaterialModule
  ],
  providers: [authInterceptorProviders, authErrorInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
