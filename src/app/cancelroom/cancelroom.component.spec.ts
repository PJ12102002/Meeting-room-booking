import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelroomComponent } from './cancelroom.component';

describe('CancelroomComponent', () => {
  let component: CancelroomComponent;
  let fixture: ComponentFixture<CancelroomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CancelroomComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CancelroomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
