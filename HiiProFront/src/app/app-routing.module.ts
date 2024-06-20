import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { NotfoundComponent } from './demo/components/notfound/notfound.component';
import { AppLayoutComponent } from './layout/app.layout.component';
import { authGuard } from './demo/components/auth/auth-guard.component';
import {ProjectPreviewComponent} from "./demo/components/projects/project-preview/project-preview.component";
import {DashboardComponent} from "./demo/components/dashboard/dashboard.component";

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
                            path: 'documentation',
                            loadChildren: () =>
                                import(
                                    './demo/components/documentation/documentation.module'
                                ).then((m) => m.DocumentationModule),
                            canActivate: [authGuard],
                        },
                        {
                            path: 'blocks',
                            loadChildren: () =>
                                import(
                                    './demo/components/primeblocks/primeblocks.module'
                                ).then((m) => m.PrimeBlocksModule),
                            canActivate: [authGuard],
                        },
                        {
                            path: 'pages',
                            loadChildren: () =>
                                import(
                                    './demo/components/pages/pages.module'
                                ).then((m) => m.PagesModule),
                            canActivate: [authGuard],
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
                {
                    path: 'landing',
                    loadChildren: () =>
                        import('./demo/components/landing/landing.module').then(
                            (m) => m.LandingModule
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
