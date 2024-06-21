import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html',
})
export class AppMenuComponent implements OnInit {

    model: any[] = [];
    role: string | null = localStorage.getItem('role');

    constructor(public layoutService: LayoutService,private messageService: MessageService,   private authService: AuthService,private router: Router  ) {

    }

    ngOnInit() {
        this.model = [
            {
                label: 'General', // Dashboard
                icon: 'pi pi-fw pi-home', // Home icon
                items: [
                    {
                        label: 'Dashboard', // Overview of supervised projects
                        icon: 'pi pi-fw pi-eye',
                        routerLink: ['/'],
                    },

                ],
            },

            {
                label: 'Projetcs Management', // Project management
                icon: 'pi pi-fw pi-sitemap', // Sitemap icon
                items: [
                    {
                        label: 'My Projects', // List of supervised projects
                        icon: 'pi pi-fw pi-list',
                        routerLink: ['/project'],
                    },

                    {
                        label: ' Project Creation', // List of supervised projects
                        icon: 'pi pi-fw pi-user-plus',
                        command: () => this.checkProjectCreationPermission(),
                    },
                ],
            },
            {
                label: 'Ressources', // Resources
                icon: 'pi pi-fw pi-book', // Book icon
                items: [
                    {
                        label: 'Ressources and Library', // Resources and library
                        icon: 'pi pi-fw pi-folder-open',
                        routerLink: ['/resources/library'],
                    },
                ],
            },


            {
                label: 'Settings', // Account Settings
                icon: 'pi pi-fw pi-cog', // Settings icon
                items: [



                    {
                        label: 'Changing Password', // Change password
                        icon: 'pi pi-fw pi-key',
                        routerLink: ['/settings/changepassword'],
                    },

                    {
                        label: 'LogOut', // My profile
                        icon: 'pi pi-fw pi-user-edit',
                        command: () => this.logout() ,
                    },

                ],
            },
        ];
    }
    checkProjectCreationPermission() {
        if (this.role === 'TEACHER') {
            this.router.navigate(['/project/create']); // Navigate to project creation page
        } else {
            this.messageService.add({
                severity: 'error',
                summary: 'Access Denied',
                detail: 'Only teachers can create projects.',
            });
        }
    }


    logout() {
        localStorage.removeItem('accessToken');
    this.authService.logout(); // Appelle le service de déconnexion
    this.router.navigate(['/auth/login']); // Redirige vers la page de connexion
}
}
