import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VotingResultsPageComponent } from './voting-results-page.component';

describe('VotingResultsPageComponent', () => {
  let component: VotingResultsPageComponent;
  let fixture: ComponentFixture<VotingResultsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VotingResultsPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VotingResultsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
