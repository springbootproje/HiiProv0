import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html',
})
export class AppMenuComponent implements OnInit {
    model: any[] = [];

    constructor(public layoutService: LayoutService,   private authService: AuthService,       private router: Router  ) {

    }

    ngOnInit() {
        this.model = [
            {
                label: 'Generale', // Dashboard
                icon: 'pi pi-fw pi-home', // Home icon
                items: [
                    {
                        label: 'Tableau de bord', // Overview of supervised projects
                        icon: 'pi pi-fw pi-eye',
                        routerLink: ['/'],
                    },

                ],
            },

            {
                label: 'Gestion des projets', // Project management
                icon: 'pi pi-fw pi-sitemap', // Sitemap icon
                items: [
                    {
                        label: 'Mes Projets', // List of supervised projects
                        icon: 'pi pi-fw pi-list',
                        routerLink: ['/projects/membermanagement'],
                    },
                    {
                        label: 'Mes Taches', // List of supervised projects
                        icon: 'pi pi-fw pi-check-square',
                        routerLink: ['/projects/membermanagement'],
                    },
                    {
                        label: 'Creation de Projet', // List of supervised projects
                        icon: 'pi pi-fw pi-user-plus',
                        routerLink: ['/projects/membermanagement'],
                    },
                ],
            },
            {
                label: 'Ressources', // Resources
                icon: 'pi pi-fw pi-book', // Book icon
                items: [
                    {
                        label: 'Ressources et bibliothèque', // Resources and library
                        icon: 'pi pi-fw pi-folder-open',
                        routerLink: ['/resources/library'],
                    },
                ],
            },


            {
                label: 'Paramètres du compte', // Account Settings
                icon: 'pi pi-fw pi-cog', // Settings icon
                items: [
                    {
                        label: 'Deconnexion', // My profile
                        icon: 'pi pi-fw pi-user-edit',
                        command: () => this.logout() ,
                    },


                    {
                        label: 'Changer le mot de passe', // Change password
                        icon: 'pi pi-fw pi-key',
                        routerLink: ['/settings/changepassword'],
                    },
                    {
                        label: 'Configuration du système', // System configuration
                        icon: 'pi pi-fw pi-sliders-h',
                        routerLink: ['/settings/systemconfig'],
                    },


                ],
            },
        ];
    }

logout() {
    this.authService.logout(); // Appelle le service de déconnexion
    this.router.navigate(['/auth/login']); // Redirige vers la page de connexion
}
}
