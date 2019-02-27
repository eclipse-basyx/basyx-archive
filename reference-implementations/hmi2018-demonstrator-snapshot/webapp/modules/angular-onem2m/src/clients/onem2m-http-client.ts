import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import { ReplaySubject } from 'rxjs/ReplaySubject';

import { OneM2MClient } from "./onem2m-client";
import { OneM2MResourcesUtil } from '../resources/onem2m-resources-util';

import { Cb, Rsp, Resource, Rqp, PrimitiveContent } from "../resources/onem2m-resources";
import { OneM2MResourceManager } from '../utils/onem2m-resource-manager';

export enum OneM2MHttpClientProtocol {
  HTTP,
  HTTPS
}

export class OneM2MHttpClientConfiguration {
  protocol: OneM2MHttpClientProtocol;
  host: string;
  port: Number;
  from: string;

  constructor(protocol: OneM2MHttpClientProtocol, host: string, port: Number, from: string) {
    this.protocol = protocol;
    this.host = host;
    this.port = port;
    this.from = from;
  }
}

export class OneM2MHttpClient implements OneM2MClient {

  private config: OneM2MHttpClientConfiguration;
  private cb: Cb;
  private rqiCounter = 0;

  constructor(private http: HttpClient) {

  }

  private enum2val(protocol: OneM2MHttpClientProtocol): string {
    if (protocol == OneM2MHttpClientProtocol.HTTP) {
      return "http://";
    } else if (protocol == OneM2MHttpClientProtocol.HTTPS) {
      return "https://";
    }
    return null;
  }

  public getConfiguration(): OneM2MHttpClientConfiguration {
    return this.config;
  }

  public start(config: OneM2MHttpClientConfiguration): Observable<Boolean> {
    let subject = new ReplaySubject<Boolean>(1);
    this.config = config;
    new OneM2MResourceManager(this).retrieveCSEBase().subscribe(
      result => {
        if (result.response.rsc === 2000) {
          this.cb = result.resource;
          subject.next(true);
          subject.complete();
        } else {
          subject.error("Could not retrieve CSE base.");
        }
      },
      error => {
        subject.error(error);
      }
    )
    return subject.asObservable();
  }

  public stop(): void {
    this.config = undefined;
    this.cb = undefined;
  }

  public createDefaultRequest(path: string, hierarchical?: boolean): Rqp {
    var rqp: Rqp = new Rqp();
    rqp.to = this.enum2val(this.config.protocol) + this.config.host + ':' + this.config.port + '/' + ((hierarchical) ? this.cb.rn + '/' : '') + '' + path;
    rqp.fr = this.config.from;
    rqp.rqi = (this.rqiCounter++).toString();

    return rqp;
  }


  public send(rqp: Rqp): Observable<Rsp> {
    if (this.config == null) {
      console.log("Config is not set! Did you call 'start'?");
    }
    var mime: string = "application/json"; // Supports only JSON for now

    // url
    var url = rqp.to;

    // fill header
    var headers: any = {};
    headers['Accept'] = mime;
    if (rqp.op) {
      if (rqp.op === 1 && rqp.ty === undefined) {
        throw new Error("Ty must be set if op = 1");
      }
      headers['Content-Type'] = rqp.op == 1 ? (mime + ';ty=' + rqp.ty) : mime;
    }
    if (rqp.fr) {
      headers['X-M2M-Origin'] = rqp.fr;
    }
    if (rqp.rqi) {
      headers['X-M2M-RI'] = rqp.rqi;
    }

    // fill GET parameters
    var params: string[] = [];

    if (rqp.da) {
      params.push("drt=" + rqp.drt);
    }
    if (rqp.ty) {
      params.push("ty=" + rqp.ty);
    }
    if (rqp.drt) {
      params.push("drt=" + rqp.drt);
    }
    if (rqp.fc !== undefined && rqp.fc !== null) {
      // filter criteria
      if (rqp.fc.fu !== undefined) {
        params.push("fu=" + rqp.fc.fu);
      }
      if (rqp.fc.lbl.length > 0) {
        for (var lbl of rqp.fc.lbl) {
          params.push("lbl=" + lbl);
        }
      }
      if (rqp.fc.lvl != undefined) {
        params.push("lvl=" + rqp.fc.lvl);
      }
    }
    if (rqp.rcn) {
      params.push("rcn=" + rqp.rcn);
    }

    url = params.length > 0 ? url + '?' + params.join('&') : url;

    var obs: Observable<any> = null;
    switch (rqp.op) {
      case 1: { // Create
        if (rqp.pc.anyOrAny.length != 1) {
          throw new Error("Primitive content must have one element!");
        }
        obs = this.http.post(url, rqp.pc.anyOrAny[0], { 'headers': headers, observe: 'response' });
        break;
      }
      case 2: { // Retrieve
        obs = this.http.get(url, { 'headers': headers, observe: 'response' });
        break;
      }
      case 3: { // Update
        break; // not implemented yet        
      }
      case 4: { // Delete
        obs = this.http.delete(url, { 'headers': headers, observe: 'response' });
        break;
      }
      case 5: { // Notify
        break; // not implemented yet        
      }
    }
    var subject: ReplaySubject<Rsp> = new ReplaySubject<Rsp>(1);
    obs.subscribe(
      response => {
        subject.next(this.createRsp(response));
        subject.complete();
      },
      error => {
        subject.error(this.createRsp(error));
      }
    );
    return subject.asObservable();
  }

  private createRsp(response: HttpResponse<any>): Rsp {
    let rsp: Rsp = new Rsp();
    rsp.fr = response.headers.get("x-m2m-origin");
    rsp.rqi = response.headers.get("x-m2m-ri");
    rsp.rsc = Number(response.headers.get("x-m2m-rsc"));
    if (response.statusText.length > 0
      && response.status !== 404
      && response.status !== 409) {
      if (response.body !== null && response.body !== undefined) { // received body
        var json = response.body; // .json();
        if (Object.keys(json).length != 1) {
          throw new Error("Invalid state: A single resource is expected.");
        }
        var type: string = Object.keys(json)[0];
        if (!type.startsWith("m2m:")) {
          throw new Error("Invalid state: Unknown oneM2M response");
        }
        rsp.pc = new PrimitiveContent;
        if (OneM2MResourcesUtil.isResource(type)) {
          var resource: Resource = this.mapResponseJsonToResource(type, json[type]);
          rsp.pc.anyOrAny.push(resource);
        } else if (OneM2MResourcesUtil.isOtherPrimitiveContent(type)) {
          var content: any = this.mapResponseJsonToOtherPrimitveContent(type, json[type]);
          rsp.pc.anyOrAny.push(content);
        }
      }
    }
    return rsp;
  }

  private mapResponseJsonToOtherPrimitveContent(type: string, json: any): Resource {
    var content: any = OneM2MResourcesUtil.createOtherPrimitiveContent(type, json);
    return content;
  }


  private mapResponseJsonToResource(type: string, json: any): Resource {
    var resource: Resource = OneM2MResourcesUtil.createResource(type);
    // fill attributes
    this.mapResponseJsonToResourceProperties(resource, type, json);
    // fill children
    this.mapResponseJsonToResourceChildren(resource, type, json);

    return resource;
  }


  private mapResponseJsonToResourceProperties(resource: Resource, type: string, json: any): void {
    for (var attr of Object.getOwnPropertyNames(json)) {
      if (resource.hasOwnProperty(attr)) {
        resource[attr] = json[attr];
      }
    }
  }
  private mapResponseJsonToResourceChildren(resource: Resource, type: string, json: any): void {
    for (var attr of Object.getOwnPropertyNames(json)) {
      if (attr.startsWith("m2m:")) { // children detected
        var childType: string = attr;
        var childJsons: any[] = json[attr];
        for (var childJson of childJsons) {
          var childResource: Resource = OneM2MResourcesUtil.createResource(childType);
          this.mapResponseJsonToResourceProperties(childResource, childType, childJson);
          if (resource.ri === childResource.pi) {
            this.mapResponseJsonToResourceChildren(childResource, childType, childJson);
            OneM2MResourcesUtil.addChildToParent(resource, childResource);
          }
        }
      }
    }
  }
}
