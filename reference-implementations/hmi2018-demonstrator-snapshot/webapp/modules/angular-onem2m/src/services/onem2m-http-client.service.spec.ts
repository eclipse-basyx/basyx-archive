import { TestBed } from '@angular/core/testing';

import { OneM2MHttpClientService } from './onem2m-http-client.service';
import { OneM2MHttpClientConfiguration, OneM2MHttpClientProtocol } from '../clients/onem2m-http-client';
import { Cb, Rqp, Rsp } from '../resources/onem2m-resources';
import { expect } from 'chai';
import 'mocha';
import { HttpClientModule, HttpClient } from '@angular/common/http';



describe('OneM2MHttpClientServiceTest', () => {

  let clientService: OneM2MHttpClientService;

  before((done) => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        {
          provide: OneM2MHttpClientService, useFactory: (http: HttpClient) => {
            return new OneM2MHttpClientService(http);
          },
          deps: [HttpClient]
        }
      ]
    });
    clientService = TestBed.get(OneM2MHttpClientService);
    clientService.start(new OneM2MHttpClientConfiguration(OneM2MHttpClientProtocol.HTTP, "127.0.0.1", 8080, "admin:admin")).subscribe(
      result => {
        if (result === true) {
          done();
        } else {
          throw new Error("Could not connect to CSE");
        }
      });
  });

  after(() => {
    TestBed.resetTestingModule();
  });



  it('Checks whether the correct request identifier is returned', (done) => {
    var rqp: Rqp = clientService.createDefaultRequest("");
    rqp.op = 2;
    rqp.rqi = '1337';
    clientService.send(rqp).subscribe(
      rsp => { expect(rsp.rqi).to.equal(rqp.rqi); done(); }
    );
  });

  it('Checks whether m2m:cb is retrieved', (done) => {
    var rqp: Rqp = clientService.createDefaultRequest("");
    rqp.op = 2;
    rqp.rcn = 4;
    clientService.send(rqp).subscribe(
      (rsp: Rsp) => {
        var pcVec = rsp.pc.anyOrAny;
        expect(pcVec).have.lengthOf(1);
        expect((pcVec[0] instanceof Cb)).to.be.true;
        expect((pcVec[0] as Cb).rn).to.equal("in-name");
        done();
      }
    );
  });
});