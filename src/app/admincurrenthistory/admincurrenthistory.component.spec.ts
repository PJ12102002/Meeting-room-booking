import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmincurrenthistoryComponent } from './admincurrenthistory.component';

describe('AdmincurrenthistoryComponent', () => {
  let component: AdmincurrenthistoryComponent;
  let fixture: ComponentFixture<AdmincurrenthistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdmincurrenthistoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdmincurrenthistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
