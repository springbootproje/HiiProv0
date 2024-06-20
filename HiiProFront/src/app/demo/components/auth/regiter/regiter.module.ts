import { RadioButtonModule } from 'primeng/radiobutton';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ReactiveFormsModule } from '@angular/forms';
import { RegiterComponent } from './regiter.component';
import { RegiterRoutingModule } from './regiter-routing.module';
import { ToastModule } from 'primeng/toast'; // Import ToastModule
import { MessageService } from 'primeng/api';
import { InputTextModule } from 'primeng/inputtext';

@NgModule({
    imports: [
        RadioButtonModule,
        ButtonModule,
        CardModule,
        ReactiveFormsModule,
        RegiterRoutingModule,
        CommonModule,
        InputTextModule,
    ],
    declarations: [RegiterComponent],
    providers: [MessageService],
})
export class RegiterModule {}
