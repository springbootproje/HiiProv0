import { Component, OnInit, OnDestroy } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Product } from '../../api/product';
import { ProductService } from '../../service/product.service';
import { Subscription, debounceTime } from 'rxjs';
import { LayoutService } from 'src/app/layout/service/app.layout.service';

@Component({
    templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit, OnDestroy {
    items!: MenuItem[];

    products!: Product[];

    chartData: any;

    chartOptions: any;

    subscription!: Subscription;

    tasks = [
        {
            name: 'Update project plan',
            status: 'In Progress',
            priority: 'High',
            dueDate: new Date(2024, 4, 25),
        },
        {
            name: 'Review design specs',
            status: 'Pending',
            priority: 'Medium',
            dueDate: new Date(2024, 4, 27),
        },
        {
            name: 'Deploy new version',
            status: 'Completed',
            priority: 'Low',
            dueDate: new Date(2024, 4, 20),
        },
        {
            name: 'Fix security issues',
            status: 'In Progress',
            priority: 'Critical',
            dueDate: new Date(2024, 4, 30),
        },
        // More tasks...
    ];
    projects = [
        {
            name: 'Project Alpha',
            status: 'Active',
            progress: 70,
            deadline: new Date(2024, 11, 25),
        },
        {
            name: 'Project Beta',
            status: 'Planning',
            progress: 20,
            deadline: new Date(2024, 12, 15),
        },

        // Add more projects as needed
    ];

    recentActivities: any[];

    constructor(
        private productService: ProductService,
        public layoutService: LayoutService
    ) {
        this.subscription = this.layoutService.configUpdate$
            .pipe(debounceTime(25))
            .subscribe((config) => {
                this.initChart();
            });
    }

    ngOnInit() {
        this.initChart();
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
