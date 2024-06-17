import { Component, OnInit, OnDestroy } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Product } from '../../api/product';
import { ProductService } from '../../service/product.service';
import {Subscription, debounceTime, Subject, tap, takeUntil, switchMap, of, Observable, lastValueFrom} from 'rxjs';
import { LayoutService } from 'src/app/layout/service/app.layout.service';
import {ProjectService} from "../../../services/project.service";
import {ProjectSummaryDto, TaskDto} from "../projects/project.model";
import {catchError} from "rxjs/operators";
import { Router } from '@angular/router';
import {UserDto} from "../../../interfaces/user-dto";

@Component({
    templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit, OnDestroy {
    projects: ProjectSummaryDto[] = [];// Liste des projets

    items!: MenuItem[];

    products!: Product[];

    chartData: any;

    chartOptions: any;

    subscription!: Subscription;
    tasks:TaskDto[];

    activeProjects: number = 0; // To store the count of active projects

    newProjects: number = 0; // Placeholder for new projects count
    teamMembers: any[] = []; // Array to hold team members data
    maxUsersInAnyProject: number = 0; // Maximum number of users in any project
    newTeamMembers: number = 0; // Number of new team members this month
    resourceUtilization: number = 0; // To store the current resource utilization percentage
    utilizationChange: number = 0; // To store the change in resource utilization
    totalCompletedTasks: number = 0; // Nombre total de tâches terminées
    uniqueMembers: UserDto[] = []; // Liste des membres uniques à travers tous les projets


    private destroy$ = new Subject<void>(); // Used for unsubscribing


    recentActivities: any[];

    constructor(
        private productService: ProductService,
        public layoutService: LayoutService,
    private projectService: ProjectService,

) {
        this.subscription = this.layoutService.configUpdate$
            .pipe(debounceTime(25))
            .subscribe((config) => {
                this.initChart();
            });
    }

    ngOnInit() {
        this.initChart();
        this.loadActiveProjects(); // Load active projects on init
        this.loadTeamMembers(); // Load team members data
        this.loadResourceUtilization();

        this.loadProjects();

        this.productService
            .getProductsSmall()
            .then((data) => (this.products = data));

        this.items = [
            { label: 'Add New', icon: 'pi pi-fw pi-plus' },
            { label: 'Remove', icon: 'pi pi-fw pi-minus' },
        ];

        this.recentActivities = [
            { description: 'Updated project plan', date: new Date() },
            {
                description: 'Completed task: UI Design',
                date: new Date(new Date().setDate(new Date().getDate() - 1)),
            },
            {
                description: 'Held project status meeting',
                date: new Date(new Date().setDate(new Date().getDate() - 2)),
            },
            {
                description: 'Added new team member: John Doe',
                date: new Date(new Date().setDate(new Date().getDate() - 4)),
            },
        ];

    }




    // Fetch the list of projects and count them as active
    loadActiveProjects() {
        this.projectService.getProjects().pipe(
            tap((projects: ProjectSummaryDto[]) => {
                this.activeProjects = projects.length; // All projects are considered active
                this.newProjects = this.calculateNewProjects(projects); // Calculate new projects since last week
            }),
            catchError(error => {
                console.error('Error fetching projects', error);
                // Return an empty array to continue the stream in case of error
                return [];
            }),
            takeUntil(this.destroy$) // Automatically unsubscribe when component is destroyed
        ).subscribe(); // Only to trigger the observable
    }
    loadProjects() {
        this.projectService.getProjects().pipe(
            tap((projects: ProjectSummaryDto[]) => {
                console.log(projects); // Vérifiez ici les données des projets

                this.projects = projects;


            }),
            catchError(error => {
                console.error('Erreur lors de la récupération des projets', error);
                return of([]); // Retourne un tableau vide en cas d'erreur
            }),
            takeUntil(this.destroy$)
        ).subscribe();
    }






    // Function to calculate the number of new projects since last week
    calculateNewProjects(projects: ProjectSummaryDto[]): number {
        const today = new Date();
        const oneWeekAgo = new Date(today);
        oneWeekAgo.setDate(today.getDate() - 7);

        const newProjects = projects.filter(project => {
            const projectCreateDate = new Date(project.createDate);
            return projectCreateDate >= oneWeekAgo;
        });

        return newProjects.length;
    }
    loadTeamMembers() {
        this.projectService.getProjects().pipe(
            tap((projects: ProjectSummaryDto[]) => {
                // Calculate the maximum number of users in any project
                this.maxUsersInAnyProject = Math.max(...projects.map(project => project.members.length), 0);

                // Calculate the number of new team members this month
                this.newTeamMembers = this.calculateNewTeamMembers(projects);

                // Log the results to ensure correctness
                console.log(`Maximum users in any project: ${this.maxUsersInAnyProject}`);
                console.log(`New team members this month: ${this.newTeamMembers}`);
            }),
            catchError(error => {
                console.error('Error fetching team members', error);
                return [];
            }),
            takeUntil(this.destroy$) // Automatically unsubscribe when component is destroyed
        ).subscribe(); // Only to trigger the observable
    }


    // Function to calculate the number of new team members this month


    calculateNewTeamMembers(projects: ProjectSummaryDto[]): number {
        const currentMonth = new Date().getMonth(); // Get current month
        const currentYear = new Date().getFullYear(); // Get current year
        let newMembersCount = 0;

        projects.forEach(project => {
            const projectCreateDate = new Date(project.createDate);
            if (projectCreateDate.getMonth() === currentMonth && projectCreateDate.getFullYear() === currentYear) {
                newMembersCount += project.members.length;
            }
        });

        return newMembersCount;
    }


    getPreviousUtilization(): Observable<number> {
        const previousUtilization = localStorage.getItem('previousUtilization');
        return previousUtilization ? of(Number(previousUtilization)) : of(65); // Default value
    }

    storeCurrentUtilization(currentUtilization: number) {
        localStorage.setItem('previousUtilization', currentUtilization.toFixed(2));
    }

    loadResourceUtilization() {
        this.projectService.getProjects().pipe(
            tap((projects: ProjectSummaryDto[]) => {
                console.log('Projects:', projects);

                this.totalCompletedTasks = projects.reduce((sum, project) => {
                    return sum + project.tasks.filter(task => task.status === 'done').length;
                }, 0);

                console.log('Total Completed Tasks:', this.totalCompletedTasks);

                const totalTasks = projects.reduce((sum, project) => sum + project.tasks.length, 0);
                this.resourceUtilization = totalTasks > 0 ? (this.totalCompletedTasks / totalTasks) * 100 : 0;

                console.log(`Resource Utilization: ${this.resourceUtilization}%`);

                this.storeCurrentUtilization(this.resourceUtilization);
            }),
            switchMap(() => this.getPreviousUtilization()),
            tap((previousUtilization: number) => {
                this.utilizationChange = this.resourceUtilization - previousUtilization;
                console.log(`Utilization Change: ${this.utilizationChange}%`);
            }),
            catchError(error => {
                console.error('Error fetching resource utilization data', error);
                return of([]);
            }),
            takeUntil(this.destroy$)
        ).subscribe();
    }



    initChart() {
        const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');
        const textColorSecondary = documentStyle.getPropertyValue(
            '--text-color-secondary'
        );
        const surfaceBorder =
            documentStyle.getPropertyValue('--surface-border');

        this.chartData = {
            labels: [
                'January',
                'February',
                'March',
                'April',
                'May',
                'June',
                'July',
            ],
            datasets: [
                {
                    label: 'First Dataset',
                    data: [65, 59, 80, 81, 56, 55, 40],
                    fill: false,
                    backgroundColor:
                        documentStyle.getPropertyValue('--bluegray-700'),
                    borderColor:
                        documentStyle.getPropertyValue('--bluegray-700'),
                    tension: 0.4,
                },
                {
                    label: 'Second Dataset',
                    data: [28, 48, 40, 19, 86, 27, 90],
                    fill: false,
                    backgroundColor:
                        documentStyle.getPropertyValue('--green-600'),
                    borderColor: documentStyle.getPropertyValue('--green-600'),
                    tension: 0.4,
                },
            ],
        };

        this.chartOptions = {
            plugins: {
                legend: {
                    labels: {
                        color: textColor,
                    },
                },
            },
            scales: {
                x: {
                    ticks: {
                        color: textColorSecondary,
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false,
                    },
                },
                y: {
                    ticks: {
                        color: textColorSecondary,
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false,
                    },
                },
            },
        };
    }

    ngOnDestroy() {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }
}
