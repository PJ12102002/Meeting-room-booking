import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveroomComponent } from './removeroom.component';

describe('RemoveroomComponent', () => {
  let component: RemoveroomComponent;
  let fixture: ComponentFixture<RemoveroomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemoveroomComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RemoveroomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
