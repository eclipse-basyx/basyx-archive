import { HttpClientModule, HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { OneM2MAssetAdministrationShellManager } from './onem2m-aas-manager';
import { AssetAdministrationShell, SubModel, Property, AssetKind, DataType } from '../resources/basic-resources';
import { ConnectedAssetAdministrationShell, ConnectedSingleProperty, ConnectedProperty, ConnectedCollectionProperty } from '../resources/connected-resources';
import { OneM2MHttpClient, OneM2MHttpClientConfiguration, OneM2MHttpClientProtocol } from 'angular-onem2m';
import { OneM2MResourceManager, OneM2MResourceExplorer } from 'angular-onem2m';

import 'mocha';
import { expect } from 'chai';


describe('OneM2MManagerTest', () => {

  let client: OneM2MHttpClient;
  let resourceManager: OneM2MResourceManager;
  let smartcontrolManager: OneM2MAssetAdministrationShellManager;

  before((done) => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    var http: HttpClient = TestBed.get(HttpClient);
    this.client = new OneM2MHttpClient(http);
    this.resourceManager = new OneM2MResourceManager(this.client);
    this.smartcontrolManager = new OneM2MAssetAdministrationShellManager(this.resourceManager);
    this.client.start(new OneM2MHttpClientConfiguration(OneM2MHttpClientProtocol.HTTP, "localhost", 8282, "admin:admin")).subscribe(result => {        
      done();
    });
  });

  after(() => {
    TestBed.resetTestingModule();
  });
  

});