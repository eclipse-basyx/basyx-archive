import { Resource } from './onem2m-resources';
import { Acp, Ae, Cb, Cin, Cnt, Sub } from './onem2m-resources';



export class OneM2MResourcesUtil {
    private static resourceMap: Map<string, { ty: number, clazz: any }> = new Map<string, { ty: number, clazz: any }>([
        ["m2m:acp", { ty: 1, clazz: Acp }],
        ["m2m:ae", { ty: 2, clazz: Ae }],
        ["m2m:cnt", { ty: 3, clazz: Cnt }],
        ["m2m:cin", { ty: 4, clazz: Cin }],
        ["m2m:cb", { ty: 5, clazz: Cb }],
        ["m2m:sub", { ty: 23, clazz: Sub }],
    ]
    );

    static addChildToParent(parent: Resource, child: Resource): void {
        if (parent instanceof Cb) {
            if (child instanceof Ae) {
                let item: Ae = parent.ae.find(item => item.ri == child.ri);
                if (item) {
                    var index = parent.ae.indexOf(item);
                    parent.ae[index] = child;
                } else {
                    parent.ae.push(child);
                }
            } else if (child instanceof Cnt) {
                let item: Cnt = parent.cnt.find(item => item.ri == child.ri);
                if (item) {
                    var index = parent.cnt.indexOf(item);
                    parent.cnt[index] = child;
                } else {
                    parent.cnt.push(child);
                }
            } else if (child instanceof Acp) {
                let item: Acp = parent.acp.find(item => item.ri == child.ri);
                if (item) {
                    var index = parent.acp.indexOf(item);
                    parent.acp[index] = child;
                } else {
                    parent.acp.push(child);
                }
            } else if (child instanceof Sub) {
                let item: Sub = parent.sub.find(item => item.ri == child.ri);
                if (item) {
                    var index = parent.sub.indexOf(item);
                    parent.sub[index] = child;
                } else {
                    parent.sub.push(child);
                }
            } else {
                throw Error("Type pair not supported(1): [PARENT] " + JSON.stringify(parent) + " [CHILD]" + JSON.stringify(child));
            }
        } else if (parent instanceof Ae) {
            if (child instanceof Cnt) {
                let item: Cnt = parent.cnt.find(item => item.ri == child.ri);
                if (item) {
                    var index = parent.cnt.indexOf(item);
                    parent.cnt[index] = child;
                } else {
                    parent.cnt.push(child);
                }
            } else if (child instanceof Acp) {
                let item: Acp = parent.acp.find(item => item.ri == child.ri);
                if (item) {
                    var index = parent.acp.indexOf(item);
                    parent.acp[index] = child;
                } else {
                    parent.acp.push(child);
                }
            } else if (child instanceof Sub) {
                let item: Sub = parent.sub.find(item => item.ri == child.ri);
                if (item) {
                    var index = parent.sub.indexOf(item);
                    parent.sub[index] = child;
                } else {
                    parent.sub.push(child);
                }
            } else {
                throw Error("Type pair not supported(2): [PARENT] " + JSON.stringify(parent) + " [CHILD]" + JSON.stringify(child));
            }
        } else if (parent instanceof Cnt) {
            if (child instanceof Cin) {
                let item: Cin = parent.cin.find(item => item.ri == child.ri);
                if (item) {
                    var index = parent.cin.indexOf(item);
                    parent.cin[index] = child;
                } else {
                    parent.cin.push(child);
                }
            } else if (child instanceof Cnt) {
                let item: Cnt = parent.cnt.find(item => item.ri == child.ri);
                if (item) {
                    var index = parent.cnt.indexOf(item);
                    parent.cnt[index] = child;
                } else {
                    parent.cnt.push(child);
                }
            } else if (child instanceof Sub) {
                let item: Sub = parent.sub.find(item => item.ri == child.ri);
                if (item) {
                    var index = parent.sub.indexOf(item);
                    parent.sub[index] = child;
                } else {
                    parent.sub.push(child);
                }
            } else {
                throw Error("Type pair not supported(3): [PARENT] " + JSON.stringify(parent) + " [CHILD]" + JSON.stringify(child));
            }
        } else {
            throw Error("Type pair not supported: [PARENT] " + JSON.stringify(parent) + " [CHILD]" + JSON.stringify(child));
        }
    }

    public static getChildrenArray(res: Resource): Resource[] {
        if (res instanceof Cb) {
            return [].concat(res.csr)
                .concat(res.csrA)
                .concat(res.nod)
                .concat(res.ae)
                .concat(res.cnt)
                .concat(res.grp)
                .concat(res.acp)
                .concat(res.sub)
                .concat(res.mgc)
                .concat(res.lcp)
                .concat(res.stcg)
                .concat(res.req)
                .concat(res.dlv)
                .concat(res.sch)
                .concat(res.mssp)
                .concat(res.asar)
                .concat(res.rol)
                .concat(res.tk)
                .concat(res.sgFlexContainerResource);
        } else if (res instanceof Ae) {
            return [].concat(res.cnt)
                .concat(res.grp)
                .concat(res.acp)
                .concat(res.sub)
                .concat(res.pch)
                .concat(res.sch)
                .concat(res.smd)
                .concat(res.ts)
                .concat(res.trpt)
                .concat(res.sgFlexContainerResource);
        } else if (res instanceof Cnt) {
            return [].concat(res.cin)
                .concat(res.cnt)
                .concat(res.sub)
                .concat(res.smd)
                .concat(res.sgFlexContainerResource);
        }
        return null;
    }

    public static isResource(xsdTypeName: string): boolean {
        return this.resourceMap.has(xsdTypeName);
    }

    public static isOtherPrimitiveContent(xsdTypeName: string): boolean {
        switch (xsdTypeName) {
            case "m2m:uril":
                return true;
        }
        return false;
    }

    static createOtherPrimitiveContent(xsdTypeName: string, json: any): Resource {
        var content: any = null;
        switch (xsdTypeName) {
            case "m2m:uril":
                content = json; // TODO test                
                break;
            default:
                throw Error("Unknown xsd type name.");
        }
        return content;
    }

    public static getTyFromXsdTypeName(xsdTypeName: string): number {
        return OneM2MResourcesUtil.resourceMap.get(xsdTypeName).ty;
    }

    public static getXsdTypeNameFromTy(ty: number): string {
        OneM2MResourcesUtil.resourceMap.forEach((value: { ty: number, clazz: any }, key: string) => {
            if (value.ty === ty) {
                return key;
            }
        });
        return null;
    }

    public static getTyFromResource(res: Resource): number {
        OneM2MResourcesUtil.resourceMap.forEach((value: { ty: number, clazz: any }, key: string) => {
            if (res instanceof value.clazz) {
                return value.ty;
            }
        });
        return null;
    }

    public static getXsdTypeNameFromResource(res: Resource): string {
        OneM2MResourcesUtil.resourceMap.forEach((value: { ty: number, clazz: any }, key: string) => {
            if (res instanceof value.clazz) {
                return key;
            }
        });
        return null;
    }

    public static getClazzFromXsdTypeName(xsdTypeName: string): any {
        return OneM2MResourcesUtil.resourceMap.get(xsdTypeName).clazz;
    }

    public static getClazzFromTy(ty: number): any {
        OneM2MResourcesUtil.resourceMap.forEach((value: { ty: number, clazz: any }, key: string) => {
            if (value.ty === ty) {
                return value.clazz;
            }
        });
    }

    static createResource(xsdTypeName: string): Resource {
        var res: Resource = null;
        var clazz: any = OneM2MResourcesUtil.getClazzFromXsdTypeName(xsdTypeName);
        res = new clazz();
        res.ty = OneM2MResourcesUtil.getTyFromXsdTypeName(xsdTypeName);
        return res;
    }
}
