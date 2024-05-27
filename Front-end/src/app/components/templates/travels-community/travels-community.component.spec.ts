import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TravelsCommunityComponent } from './travels-community.component';

describe('TravelsCommunityComponent', () => {
  let component: TravelsCommunityComponent;
  let fixture: ComponentFixture<TravelsCommunityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TravelsCommunityComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TravelsCommunityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
