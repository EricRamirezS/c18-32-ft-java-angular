import { Routes } from '@angular/router';
import { HomePageComponent } from './components/home-page/home-page.component';
import { PlacesToVisitComponent } from './components/places-to-visit/places-to-visit.component';
import { UserPanelComponent } from './components/user-panel/user-panel.component';
import { VotingPageComponent } from './components/voting-page/voting-page.component';
import { VotingResultsPageComponent } from './components/voting-results-page/voting-results-page.component';
import { Error404Component } from './components/error-404/error-404.component';


const pageTitle = 'MyPage.'

export const routes: Routes = [
  {path: '', title: `Home Page | ${pageTitle}`, component:HomePageComponent},
  {path: 'home-page', title: `Home Page | ${pageTitle}`, component:HomePageComponent},
  {path: 'places-to-visit', title: `Places | ${pageTitle}`, component: PlacesToVisitComponent},
  {path: 'user-panel', title: `My account | ${pageTitle}`, component: UserPanelComponent},
  {path: 'voting-page', title: `Voting | ${pageTitle}`, component: VotingPageComponent},
  {path: 'voting-results', title: `Results | ${pageTitle}`, component: VotingResultsPageComponent},
  {path: '404', title: `Error 404 | ${pageTitle}`, component: Error404Component },
  // {path: '**', pathMatch: 'full', redirectTo: "404" }
];
