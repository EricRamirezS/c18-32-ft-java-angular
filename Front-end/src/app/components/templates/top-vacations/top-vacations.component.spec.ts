import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopVacationsComponent } from './top-vacations.component';

describe('TopVacationsComponent', () => {
  let component: TopVacationsComponent;
  let fixture: ComponentFixture<TopVacationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopVacationsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TopVacationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
