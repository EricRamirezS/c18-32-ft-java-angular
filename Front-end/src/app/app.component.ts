import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { RouteGuardService } from './services/route-guard/route-guard.service';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [
        RouterOutlet,
        NavbarComponent,
        HomePageComponent,
        FooterComponent,
    ],
    template: `
        @if(showNavbarAndFooter){
        <app-navbar></app-navbar>
        }
        <router-outlet></router-outlet>
        @if(showNavbarAndFooter){
        <app-footer></app-footer>
        }
    `,
    styleUrl: './app.component.css',
})
export class AppComponent {
    title = 'Front-end';

    showNavbarAndFooter = true;

    constructor(private routeGuardService: RouteGuardService) {
        this.routeGuardService.showNavbarAndFooter$.subscribe(
            (show) => (this.showNavbarAndFooter = show)
        );
    }
}
