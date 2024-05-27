import { Component } from '@angular/core';
import { TopVacationsComponent } from '../templates/top-vacations/top-vacations.component'
import { OffersComponent } from '../templates/offers/offers.component'
import { PropertyTypeComponent } from '../templates/property-type/property-type.component'
import { TravelsCommunityComponent } from '../templates/travels-community/travels-community.component'

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    TopVacationsComponent,
    OffersComponent,
    PropertyTypeComponent,
    TravelsCommunityComponent
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {


    items= [1,2,3,4]
    item2= [1,2]
}
