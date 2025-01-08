import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminbookroomComponent } from './adminbookroom.component';

describe('AdminbookroomComponent', () => {
  let component: AdminbookroomComponent;
  let fixture: ComponentFixture<AdminbookroomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminbookroomComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminbookroomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
