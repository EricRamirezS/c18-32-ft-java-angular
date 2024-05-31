import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-voting-page',
  standalone: true,
  imports: [],
  templateUrl: './voting-page.component.html',
  styleUrl: './voting-page.component.css'
})
export class VotingPageComponent implements OnInit {
  results = [
    { label: 'Opción 1', percentage: 70, color: '#4caf50' },
    { label: 'Opción 2', percentage: 20, color: '#2196f3' },
    { label: 'Opción 3', percentage: 10, color: '#ff5722' },
  ];

  ngOnInit(): void {
    
  }
}
