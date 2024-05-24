import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-places-to-visit',
  standalone: true,
  imports: [],
  templateUrl: './places-to-visit.component.html',
  styleUrl: './places-to-visit.component.css'
})
export class PlacesToVisitComponent implements OnInit {

  ngOnInit(): void {
    this.setupModal();
  }

  setupModal(): void {
    document.addEventListener('DOMContentLoaded', () => {
      const modalButtons = document.querySelectorAll<HTMLButtonElement>('.ver-lista-btn');
      const modals = document.querySelectorAll<HTMLElement>('.modal');
      const closeButtons = document.querySelectorAll<HTMLButtonElement>('.close-btn');

      modalButtons.forEach(button => {
        button.addEventListener('click', () => {
          const modalId = button.dataset['modalTarget'];
          if (modalId) {
            const modal = document.querySelector<HTMLElement>(modalId);
            if (modal) {
              modal.style.display = 'block';
            }
          }
        });
      });

      closeButtons.forEach(button => {
        button.addEventListener('click', () => {
          const modal = button.closest('.modal');
          if (modal) {
            modal.classList.remove('show'); // Agrega esta línea
          }
        });
      });
      

      window.addEventListener('click', (event) => {
        modals.forEach(modal => {
          if (event.target == modal) {
            modal.style.display = 'none';
          }
        });
      });
    });
  }
}


