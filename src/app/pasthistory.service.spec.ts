import { TestBed } from '@angular/core/testing';

import { PasthistoryService } from './pasthistory.service';

describe('PasthistoryService', () => {
  let service: PasthistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PasthistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
