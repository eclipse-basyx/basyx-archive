import { TestBed } from '@angular/core/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';

import { OneM2MHttpClient } from '../clients/onem2m-http-client';
import { OneM2MHttpClientConfiguration, OneM2MHttpClientProtocol } from '../clients/onem2m-http-client';
import { Cb, Rqp, Rsp, Cnt, Resource, PrimitiveContent, FilterCriteria } from '../resources/onem2m-resources';
import { OneM2MResourcesUtil } from '../resources/onem2m-resources-util';
import { expect } from 'chai';
import { OneM2MResourceExplorer } from './onem2m-resource-explorer';
import { OneM2MResourceManager } from './onem2m-resource-manager';
import { DataResult, ResourceResult, ResourceExtendedResult } from './onem2m-resource-common-datatypes'

import 'mocha';


describe('OneM2MResourceExplorer', () => {

  let client: OneM2MHttpClient;
  let resourceExplorer: OneM2MResourceExplorer;
  let resourceManager: OneM2MResourceManager;

  before((done) => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    var http: HttpClient = TestBed.get(HttpClient);
    this.client = new OneM2MHttpClient(http);
    this.resourceManager = new OneM2MResourceManager(this.client);
    this.resourceExplorer = new OneM2MResourceExplorer(this.client);
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


  it('Checks whether a deleted child container is not retrievable anymore', (done) => {
    this.resourceManager.createContainer02('', 'unit-test', true).subscribe(
      result => {
        expect((result instanceof ResourceResult)).to.be.true;
        expect((result as ResourceResult<Cnt>).response.rsc).to.equal(2001);
        this.resourceManager.createContainer02('unit-test', 'child-container', true).subscribe(
          result => {
            expect((result instanceof ResourceResult)).to.be.true;
            expect((result as ResourceResult<Cnt>).response.rsc).to.equal(2001);
            this.resourceManager.deleteResource('unit-test/child-container', true).subscribe(
              result => {
                expect((result instanceof DataResult)).to.be.true;
                expect((result as DataResult<Boolean>).response.rsc).to.equal(2002);

                this.resourceExplorer.retrieveResourceWithChildrenRecursive('unit-test', true).subscribe(result => {
                  expect(OneM2MResourcesUtil.getChildrenArray((result as ResourceExtendedResult<Resource>).resource).length).to.equal(0);

                  this.resourceManager.deleteResource('unit-test', true).subscribe(
                    result => {
                      expect((result instanceof DataResult)).to.be.true;
                      expect((result as DataResult<Boolean>).response.rsc).to.equal(2002);
                      done();
                    });

                });

              }
            );
          }
        );

      }
    );
  });


  it('Checks whether retrieve resource with children works correctly', (done) => {
    this.resourceManager.createContainer02('', 'unit-test', true).subscribe(
      result => {
        expect((result instanceof ResourceResult)).to.be.true;
        expect((result as ResourceResult<Cnt>).response.rsc).to.equal(2001);
        this.resourceManager.createContainer02('unit-test', 'child-container-1', true).subscribe(
          result => {
            expect((result instanceof ResourceResult)).to.be.true;
            expect((result as ResourceResult<Cnt>).response.rsc).to.equal(2001);
            this.resourceManager.createContainer02('unit-test/child-container-1', 'child-container-2', true).subscribe(
              result => {
                expect((result instanceof ResourceResult)).to.be.true;
                expect((result as ResourceResult<Cnt>).response.rsc).to.equal(2001);

                this.resourceExplorer.retrieveResourceWithChildrenRecursive('unit-test', true).subscribe(result => {
                  expect(OneM2MResourcesUtil.getChildrenArray((result as ResourceExtendedResult<Resource>).resource).length).to.equal(1);
                  this.resourceManager.deleteResource('unit-test', true).subscribe(
                    result => {
                      expect((result instanceof DataResult)).to.be.true;
                      expect((result as DataResult<Boolean>).response.rsc).to.equal(2002);
                      done();
                    });

                });

              }
            );
          }
        );

      }
    );
  });



  it('retrieve ACP', (done) => {


    var rqp: Rqp = this.resourceManager.getClient().createDefaultRequest('', false);
    rqp.op = 2;
    rqp.pc = new PrimitiveContent;
    rqp.fc = new FilterCriteria;
    rqp.fc.fu = 1;
    rqp.ty = 1;
    rqp.drt = 2;


    this.resourceManager.getClient().send(rqp).map(response => new ResourceResult<Resource>(response, (response.rsc === 2000) ? response.pc.anyOrAny[0] : null))
      .subscribe(result => {
        let uril: string[] = result.resource;
        expect(uril.length).to.equal(1);
        let acpri: string = uril[0];

        this.resourceExplorer.retrieveResourceWithChildrenRecursive(acpri, false, 0).subscribe(resultACP => {
          expect(resultACP.resource).to.be.not.null;
          done();
        });

      })



  });



});