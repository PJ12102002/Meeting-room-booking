import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserpasthistoryComponent } from './userpasthistory.component';

describe('UserpasthistoryComponent', () => {
  let component: UserpasthistoryComponent;
  let fixture: ComponentFixture<UserpasthistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserpasthistoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserpasthistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
