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
                label: 'General', // Dashboard
                icon: 'pi pi-fw pi-home', // Home icon
                items: [
                    {
                        label: 'Dashboard', // Overview of supervised projects
                        icon: 'pi pi-fw pi-eye',
                        routerLink: ['/'],
                    },
                    {
                        label: 'Calendar', // Calendar
                        icon: 'pi pi-fw pi-calendar',
                        routerLink: ['/dashboard/calendar'],
                    },
                ],
            },

            {
                label: ' projects Management', // Project management
                icon: 'pi pi-fw pi-sitemap', // Sitemap icon
                items: [
                    {
                        label: 'My Projects', // List of supervised projects
                        icon: 'pi pi-fw pi-list',
                        routerLink: ['/project'],
                    },
                    {
                        label: 'My Tasks', // List of supervised projects
                        icon: 'pi pi-fw pi-check-square',
                        routerLink: ['/project/preview/:id'],
                    },
                    {
                        label: ' Projcet Creation', // List of supervised projects
                        icon: 'pi pi-fw pi-user-plus',
                        routerLink: ['/project/create'],
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
                label: 'Rapport and Analytics', // Reports and Analysis
                icon: 'pi pi-fw pi-chart-line', // Chart line icon
                items: [
                    {
                        label: 'List of projects', // List of supervised projects
                        icon: 'pi pi-fw pi-list',
                        routerLink: ['/project'],
                    },
                    {
                        label: 'List of Tasks', // List of supervised projects
                        icon: 'pi pi-fw pi-check-square',
                        routerLink: ['/reports/projectname'],
                    },
                ],
            },
            {
                label: 'Settings', // Account Settings
                icon: 'pi pi-fw pi-cog', // Settings icon
                items: [
                    {
                        label: 'My profil', // My profile
                        icon: 'pi pi-fw pi-user-edit',
                        routerLink: ['/settings/profile'],
                    },
                    {
                        label: 'Modify My Profil Picture', // Change profile picture
                        icon: 'pi pi-fw pi-user-plus',
                        routerLink: ['/settings/profilepic'],
                    },
                    {
                        label: 'Modify My Bio', // Change biography
                        icon: 'pi pi-fw pi-pencil',
                        routerLink: ['/settings/bio'],
                    },
                    {
                        label: 'Account Management', // Account management
                        icon: 'pi pi-fw pi-users',
                        routerLink: ['/settings/accountmanagement'],
                    },
                    {
                        label: 'Change My password', // Change password
                        icon: 'pi pi-fw pi-key',
                        routerLink: ['/settings/changepassword'],
                    },
                    {
                        label: 'system Configuration', // System configuration
                        icon: 'pi pi-fw pi-sliders-h',
                        routerLink: ['/settings/systemconfig'],
                    },
                    {
                        label: 'Modify the language', // Change language
                        icon: 'pi pi-fw pi-globe',
                        routerLink: ['/settings/changelanguage'],
                    },
                    {
                        label: 'Modify the theme', // Change theme
                        icon: 'pi pi-fw pi-palette',
                        routerLink: ['/settings/changetheme'],
                    },
                ],
            },
        ];
    }
}
