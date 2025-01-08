import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsercurrenthistoryComponent } from './usercurrenthistory.component';

describe('UsercurrenthistoryComponent', () => {
  let component: UsercurrenthistoryComponent;
  let fixture: ComponentFixture<UsercurrenthistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsercurrenthistoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsercurrenthistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
