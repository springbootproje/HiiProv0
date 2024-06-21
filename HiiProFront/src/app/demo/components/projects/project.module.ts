import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { TableModule } from 'primeng/table';
import { MultiSelectModule } from 'primeng/multiselect';
import { DialogModule } from 'primeng/dialog';
import { CardModule } from 'primeng/card';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ToastModule } from 'primeng/toast';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

import { ProjectFormComponent } from './project-form/project-form.component';
import { ProjectListComponent } from './project-list/project-list.component';
import { ProjectEditComponent } from './project-edit/project-edit.component';
import { ProjectPreviewComponent } from './project-preview/project-preview.component';
import { TaskFormComponent } from './task-form/task-form.component'; // Import TaskFormComponent
import { MessageService, ConfirmationService } from 'primeng/api';
import {DockModule} from "primeng/dock";

const routes: Routes = [
    { path: '', component: ProjectListComponent },
    { path: 'create', component: ProjectFormComponent },
    { path: 'edit/:id', component: ProjectEditComponent },
    { path: 'preview/:id', component: ProjectPreviewComponent },
];

@NgModule({
    declarations: [
        ProjectFormComponent,
        ProjectListComponent,
        ProjectEditComponent,
        ProjectPreviewComponent,
        TaskFormComponent, // Declare TaskFormComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        InputTextModule,
        ButtonModule,
        CalendarModule,
        DropdownModule,
        InputTextareaModule,
        TableModule,
        MultiSelectModule,
        DialogModule,
        CardModule,
        ProgressSpinnerModule,
        ToastModule,
        DragDropModule, // Import DragDropModule for the drag-and-drop functionality
        ConfirmDialogModule,
        RouterModule.forChild(routes),
        DockModule,
    ],
    providers: [MessageService, ConfirmationService],
    exports: [ToastModule],
})
export class ProjectModule {}
