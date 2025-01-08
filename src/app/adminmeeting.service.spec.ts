import { TestBed } from '@angular/core/testing';

import { AdminmeetingService } from './adminmeeting.service';

describe('AdminmeetingService', () => {
  let service: AdminmeetingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminmeetingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
