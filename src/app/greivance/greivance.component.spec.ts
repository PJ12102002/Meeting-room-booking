import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GreivanceComponent } from './greivance.component';

describe('GreivanceComponent', () => {
  let component: GreivanceComponent;
  let fixture: ComponentFixture<GreivanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GreivanceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GreivanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
