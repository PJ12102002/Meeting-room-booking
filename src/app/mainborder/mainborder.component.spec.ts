import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainborderComponent } from './mainborder.component';

describe('MainborderComponent', () => {
  let component: MainborderComponent;
  let fixture: ComponentFixture<MainborderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MainborderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainborderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
