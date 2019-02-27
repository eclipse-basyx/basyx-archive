import { TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';

import { OneM2MHttpClient } from '../clients/onem2m-http-client';
import { OneM2MHttpClientConfiguration, OneM2MHttpClientProtocol } from '../clients/onem2m-http-client';
import { Cb, Rqp, Rsp, Cnt } from '../resources/onem2m-resources';
import { expect } from 'chai';
import { OneM2MResourceManager } from './onem2m-resource-manager';
import { DataResult, ResourceResult } from './onem2m-resource-common-datatypes'

import 'mocha';


describe('OneM2MResourceManagerTest', () => {

  let client: OneM2MHttpClient;
  let resourceManager: OneM2MResourceManager;

  before((done) => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    var http: HttpClient = TestBed.get(HttpClient);
    this.client = new OneM2MHttpClient(http);
    this.resourceManager = new OneM2MResourceManager(this.client);
    // default Eclipse oM2M credentials
    this.client.start(new OneM2MHttpClientConfiguration(OneM2MHttpClientProtocol.HTTP, "127.0.0.1", 8080, "admin:admin")).subscribe(
      result => {
        if (result === true) {
          done();
        } else {
          throw Error("Could not connect to CSE");
        }
      });
  });

  after(() => {
    TestBed.resetTestingModule();
  });


  it('Checks whether a container is created and deleted', (done) => {
    this.resourceManager.createContainer02('', 'unit-test', true).subscribe(
      result => {
        expect((result instanceof ResourceResult)).to.be.true;
        expect((result as ResourceResult<Cnt>).response.rsc).to.equal(2001);

        this.resourceManager.deleteResource('unit-test', true).subscribe(
          result => {
            expect((result instanceof DataResult)).to.be.true;
            expect((result as DataResult<Boolean>).response.rsc).to.equal(2002);
            done();
          }
        );
      }
    );
  });





});