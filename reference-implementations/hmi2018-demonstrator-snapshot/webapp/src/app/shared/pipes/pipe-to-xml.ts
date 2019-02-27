import { PipeTransform, Pipe } from "@angular/core";

@Pipe({ name: 'toXml', pure: false })
export class ToXmlPipe implements PipeTransform {
  transform(value: any, args: any[] = null): any {
    if (!value) {
      return null;
    }
    if (!(value instanceof Array)) {
      return null;
    }
    let res: string = "";
    for (let i = 0; i < value.length; i++) {
      res = res + "" + value[i].toXML();
    }
    return res;
  }
}