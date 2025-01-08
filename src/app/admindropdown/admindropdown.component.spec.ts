import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmindropdownComponent } from './admindropdown.component';

describe('AdmindropdownComponent', () => {
  let component: AdmindropdownComponent;
  let fixture: ComponentFixture<AdmindropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdmindropdownComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdmindropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
