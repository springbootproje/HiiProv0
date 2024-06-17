import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html',
})
export class AppMenuComponent implements OnInit {
    model: any[] = [];

    constructor(public layoutService: LayoutService) {}

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
                    {
                        label: 'Calendrier', // Calendar
                        icon: 'pi pi-fw pi-calendar',
                        routerLink: ['/dashboard/calendar'],
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
                label: 'Communication', // Communication
                icon: 'pi pi-fw pi-comments', // Comments icon
                items: [
                    {
                        label: 'Discussion', // Discussion
                        icon: 'pi pi-fw pi-comment',
                        routerLink: ['/communication/discussion'],
                    },
                ],
            },
            {
                label: 'Rapport et analyses', // Reports and Analysis
                icon: 'pi pi-fw pi-chart-line', // Chart line icon
                items: [
                    {
                        label: 'Liste des projets', // List of supervised projects
                        icon: 'pi pi-fw pi-list',
                        routerLink: ['/reports/projectname'],
                    },
                    {
                        label: 'Liste des Taches', // List of supervised projects
                        icon: 'pi pi-fw pi-check-square',
                        routerLink: ['/reports/projectname'],
                    },
                ],
            },
            {
                label: 'Paramètres du compte', // Account Settings
                icon: 'pi pi-fw pi-cog', // Settings icon
                items: [
                    {
                        label: 'Mon profil', // My profile
                        icon: 'pi pi-fw pi-user-edit',
                        routerLink: ['/settings/profile'],
                    },
                    {
                        label: 'Modification de la photo de profil', // Change profile picture
                        icon: 'pi pi-fw pi-user-plus',
                        routerLink: ['/settings/profilepic'],
                    },
                    {
                        label: 'Modification de la biographie', // Change biography
                        icon: 'pi pi-fw pi-pencil',
                        routerLink: ['/settings/bio'],
                    },
                    {
                        label: 'Gestion du compte', // Account management
                        icon: 'pi pi-fw pi-users',
                        routerLink: ['/settings/accountmanagement'],
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
                    {
                        label: 'Modifier la langue', // Change language
                        icon: 'pi pi-fw pi-globe',
                        routerLink: ['/settings/changelanguage'],
                    },
                    {
                        label: 'Modifier le thème', // Change theme
                        icon: 'pi pi-fw pi-palette',
                        routerLink: ['/settings/changetheme'],
                    },
                ],
            },
        ];
    }
}
