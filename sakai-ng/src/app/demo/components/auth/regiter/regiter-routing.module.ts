import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RegiterComponent } from './regiter.component';

@NgModule({
    imports: [
        RouterModule.forChild([{ path: '', component: RegiterComponent }]),
    ],
    exports: [RouterModule],
})
export class RegiterRoutingModule {}
