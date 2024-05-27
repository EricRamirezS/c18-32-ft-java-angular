import { Injectable } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { BehaviorSubject} from 'rxjs';
import { filter } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class RouteGuardService {
    private showNavbarAndFooterSubject = new BehaviorSubject<boolean>(true);
    showNavbarAndFooter$ = this.showNavbarAndFooterSubject.asObservable();

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {
        this.router.events
            .pipe(filter((event) => event instanceof NavigationEnd))
            .subscribe(() => {
                const currentRoute = this.activatedRoute.root;
                this.checkRouteConfig(currentRoute);
            });
    }

    private checkRouteConfig(route: ActivatedRoute) {
        if (route.children.length > 0) {
            route.children.forEach((child) => {
                if (child.snapshot.data['hideNavbarAndFooter']) {
                    this.showNavbarAndFooterSubject.next(false);
                } else {
                    this.showNavbarAndFooterSubject.next(true);
                }
                this.checkRouteConfig(child);
            });
        }
    }
}
