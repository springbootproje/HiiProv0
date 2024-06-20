import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { NotfoundComponent } from './demo/components/notfound/notfound.component';
import { AppLayoutComponent } from './layout/app.layout.component';
import { authGuard } from './demo/components/auth/auth-guard.component';
import {ProjectPreviewComponent} from "./demo/components/projects/project-preview/project-preview.component";
import {DashboardComponent} from "./demo/components/dashboard/dashboard.component";
import {ChangePasswordComponent} from "./demo/components/change-password/change-password.component";

@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                {
                    path: '',
                    component: AppLayoutComponent,
                    children: [
                        {
                            path: '',
                            loadChildren: () =>
                                import(
                                    './demo/components/dashboard/dashboard.module'
                                ).then((m) => m.DashboardModule),
                            canActivate: [authGuard],
                        },
                        {
                            path: 'settings',
                            children: [
                                {
                                    path: 'changepassword',
                                    component: ChangePasswordComponent, // Assurez-vous que ce composant est importé
                                    canActivate: [authGuard], // Si vous avez besoin de protéger cette route
                                },
                            ],
                        },




                {
                            path: 'project',
                            loadChildren: () =>
                                import(
                                    './demo/components/projects/project.module'
                                ).then((m) => m.ProjectModule),
                            canActivate: [authGuard],
                        },

                    ],
                },
                {
                    path: 'auth',
                    loadChildren: () =>
                        import('./demo/components/auth/auth.module').then(
                            (m) => m.AuthModule
                        ),
                },

                { path: 'notfound', component: NotfoundComponent },
                { path: '**', redirectTo: '/notfound' },
            ],
            {
                scrollPositionRestoration: 'enabled',
                anchorScrolling: 'enabled',
                onSameUrlNavigation: 'reload',
            }
        ),
    ],
    exports: [RouterModule],
})
export class AppRoutingModule {}
