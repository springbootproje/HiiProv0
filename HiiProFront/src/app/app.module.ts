import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import {
    HashLocationStrategy,
    LocationStrategy,
    PathLocationStrategy,
} from '@angular/common';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AppLayoutModule } from './layout/app.layout.module';
import { NotfoundComponent } from './demo/components/notfound/notfound.component';
import { ChangePasswordComponent } from './demo/components/change-password/change-password.component'; // Importez votre composant ici
import { RadioButtonModule } from 'primeng/radiobutton';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { AuthInterceptor } from './demo/components/auth/auth-interceptor.components';
import { PanelModule } from 'primeng/panel'; // Ajoutez le module Panel de PrimeNG
import { PasswordModule } from 'primeng/password'; // Ajoutez le module Password de PrimeNG
import { ButtonModule } from 'primeng/button'; // Ajoutez le module Button de PrimeNG
import { InputTextModule } from 'primeng/inputtext';
import {DialogModule} from "primeng/dialog"; // Ajoutez le module InputText de PrimeNG

@NgModule({
    declarations: [AppComponent, NotfoundComponent],
    imports: [AppRoutingModule, AppLayoutModule,DialogModule, BrowserModule, HttpClientModule, ToastModule, ReactiveFormsModule, PanelModule, ButtonModule, PasswordModule, InputTextModule, ChangePasswordComponent, ChangePasswordComponent],
    providers: [
        { provide: LocationStrategy, useClass: PathLocationStrategy },
        { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },


        MessageService,
    ],
    exports: [ToastModule],
    bootstrap: [AppComponent],
})
export class AppModule {}
