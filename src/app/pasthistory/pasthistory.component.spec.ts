import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasthistoryComponent } from './pasthistory.component';

describe('PasthistoryComponent', () => {
  let component: PasthistoryComponent;
  let fixture: ComponentFixture<PasthistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PasthistoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PasthistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
