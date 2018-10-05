// Generated using typescript-generator version 1.29.355 on 2018-01-16 09:48:40.

export class AccessControlRule {
    acor: string[] = [];
    acop: number = undefined;
    acco: Acco[] = [];
    acaf: boolean = undefined;
}

export class Resource {
    ty: number = undefined;
    ri: string = undefined;
    pi: string = undefined;
    ct: string = undefined;
    lt: string = undefined;
    lbl: string[] = [];
    rn: string = undefined;
}

export class RegularResource extends Resource {
    acpi: string[] = [];
    et: string = undefined;
    daci: string[] = [];
}

export class AnnounceableResource extends RegularResource {
    at: string[] = [];
    aa: string[] = [];
}

export class MgmtResource extends AnnounceableResource {
    mgd: number = undefined;
    obis: string[] = [];
    obps: string[] = [];
    dc: string = undefined;
}

export class Acmp extends MgmtResource {
    acmlk: string = undefined;
}

export class AnnounceableSubordinateResource extends Resource {
    et: string = undefined;
    at: string[] = [];
    aa: string[] = [];
}

export class Acp extends AnnounceableSubordinateResource {
    pv: SetOfAcrs = undefined;
    pvs: SetOfAcrs = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class AnnouncedSubordinateResource extends Resource {
    et: string = undefined;
    lnk: string = undefined;
}

export class AcpA extends AnnouncedSubordinateResource {
    pv: SetOfAcrs = undefined;
    pvs: SetOfAcrs = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class ActionStatus {
    ac: string = undefined;
    sus: number = undefined;
}

export class Ae extends AnnounceableResource {
    apn: string = undefined;
    api: string = undefined;
    aei: string = undefined;
    poa: string[] = [];
    or: string = undefined;
    nl: string = undefined;
    rr: boolean = undefined;
    csz: PermittedMediaTypes[] = [];
    esi: E2ESecInfo = undefined;
    ch: ChildResourceRef[] = [];
    grp: Grp[] = [];
    sch: Sch[] = [];
    ts: Ts[] = [];
    sub: Sub[] = [];
    smd: Smd[] = [];
    sgFlexContainerResource: FlexContainerResource[] = [];
    acp: Acp[] = [];
    cnt: Cnt[] = [];
    pch: Pch[] = [];
    trpt: Trpt[] = [];
}

export class AnnouncedResource extends Resource {
    acpi: string[] = [];
    et: string = undefined;
    lnk: string = undefined;
    daci: string[] = [];
}

export class AeA extends AnnouncedResource {
    apn: string = undefined;
    api: string = undefined;
    aei: string = undefined;
    poa: string[] = [];
    or: string = undefined;
    nl: string = undefined;
    rr: boolean = undefined;
    csz: PermittedMediaTypes[] = [];
    esi: E2ESecInfo = undefined;
    ch: ChildResourceRef[] = [];
    cntOrCntAOrGrp: any[] = [];
}

export class AggregatedNotification {
    sgn: Notification[] = [];
}

export class AggregatedResponse {
    ri: string = undefined;
    rsp: Rsp[] = [];
}

export class FlexContainerResource {
    ty: number = undefined;
    ri: string = undefined;
    pi: string = undefined;
    ct: string = undefined;
    lt: string = undefined;
    lbl: string[] = [];
    acpi: string[] = [];
    et: string = undefined;
    daci: string[] = [];
    at: string[] = [];
    aa: string[] = [];
    st: number = undefined;
    cr: string = undefined;
    cnd: string = undefined;
    or: string = undefined;
    rn: string = undefined;
}

export class Ajap extends FlexContainerResource {
    dir: number = undefined;
    ch: ChildResourceRef[] = [];
    smdOrSubOrAjso: any[] = [];
}

export class AnnouncedFlexContainerResource {
    ty: number = undefined;
    ri: string = undefined;
    pi: string = undefined;
    ct: string = undefined;
    lt: string = undefined;
    lbl: string[] = [];
    acpi: string[] = [];
    et: string = undefined;
    lnk: string = undefined;
    daci: string[] = [];
    st: number = undefined;
    cnd: string = undefined;
    or: string = undefined;
    rn: string = undefined;
}

export class Ajapa extends AnnouncedFlexContainerResource {
    dir: number = undefined;
    ch: ChildResourceRef[] = [];
    smdOrSmdAOrSub: any[] = [];
}

export class Ajfw extends FlexContainerResource {
    ch: ChildResourceRef[] = [];
    smdOrSub: RegularResource[] = [];
}

export class Ajfwa extends AnnouncedFlexContainerResource {
    ch: ChildResourceRef[] = [];
    smdOrSmdAOrSub: Resource[] = [];
}

export class Ajif extends FlexContainerResource {
    ajir: string = undefined;
    ch: ChildResourceRef[] = [];
    smdOrSubOrAjmd: any[] = [];
}

export class Ajifa extends AnnouncedFlexContainerResource {
    ajir: string = undefined;
    ch: ChildResourceRef[] = [];
    smdOrSmdAOrSub: any[] = [];
}

export class Ajmc extends FlexContainerResource {
    inp: string = undefined;
    clst: string = undefined;
    out: string = undefined;
    ch: ChildResourceRef[] = [];
    smdOrSub: RegularResource[] = [];
}

export class Ajmca extends AnnouncedFlexContainerResource {
    ch: ChildResourceRef[] = [];
    smdOrSmdAOrSub: Resource[] = [];
}

export class Ajmd extends FlexContainerResource {
    ch: ChildResourceRef[] = [];
    smdOrSubOrAjmc: any[] = [];
}

export class Ajmda extends AnnouncedFlexContainerResource {
    ch: ChildResourceRef[] = [];
    smdOrSmdAOrSub: any[] = [];
}

export class Ajpr extends FlexContainerResource {
    crv: string = undefined;
    rqv: string = undefined;
    ch: ChildResourceRef[] = [];
    smdOrSub: RegularResource[] = [];
}

export class Ajpra extends AnnouncedFlexContainerResource {
    ch: ChildResourceRef[] = [];
    smdOrSmdAOrSub: Resource[] = [];
}

export class Ajso extends FlexContainerResource {
    ajop: string = undefined;
    ena: boolean = undefined;
    ch: ChildResourceRef[] = [];
    smdOrSubOrAjif: any[] = [];
}

export class Ajsoa extends AnnouncedFlexContainerResource {
    ajop: string = undefined;
    ena: boolean = undefined;
    ch: ChildResourceRef[] = [];
    smdOrSmdAOrSub: any[] = [];
}

export class Ajsw extends FlexContainerResource {
    ch: ChildResourceRef[] = [];
    smdOrSubOrAjap: any[] = [];
}

export class Ajswa extends AnnouncedFlexContainerResource {
    ch: ChildResourceRef[] = [];
    smdOrSmdAOrSub: any[] = [];
}

export class Andi extends MgmtResource {
    dvd: string = undefined;
    dvt: string = undefined;
    awi: string = undefined;
    sli: number = undefined;
    sld: number = undefined;
    ss: string = undefined;
    lnh: string[] = [];
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class AnnouncedMgmtResource extends AnnouncedResource {
    mgd: number = undefined;
    obis: string[] = [];
    obps: string[] = [];
    dc: string = undefined;
}

export class AndiA extends AnnouncedMgmtResource {
    dvd: string = undefined;
    dvt: string = undefined;
    awi: string = undefined;
    sli: number = undefined;
    sld: number = undefined;
    ss: string = undefined;
    lnh: string[] = [];
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class Ani extends MgmtResource {
    ant: string = undefined;
    ldv: string[] = [];
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class AniA extends AnnouncedMgmtResource {
    ant: string = undefined;
    ldv: string[] = [];
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class AnyArgType {
    nm: string = undefined;
    val: any = undefined;
}

export class Asar extends RegularResource {
    apci: string[] = [];
    aai: string[] = [];
    aae: string[] = [];
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class Attribute {
    nm: string = undefined;
    val: any = undefined;
}

export class BackOffParameters {
    bops: Bops[] = [];
}

export class Bat extends MgmtResource {
    btl: number = undefined;
    bts: number = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class BatA extends AnnouncedMgmtResource {
    btl: number = undefined;
    bts: number = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class BatchNotify {
    num: number = undefined;
    dur: Duration = undefined;
}

export class Cb extends Resource {
    acpi: string[] = [];
    cst: number = undefined;
    csi: string = undefined;
    srt: number[] = [];
    poa: string[] = [];
    nl: string = undefined;
    esi: E2ESecInfo = undefined;
    ch: ChildResourceRef[] = [];
    rol: Rol[] = [];
    dlv: Dlv[] = [];
    grp: Grp[] = [];
    sch: Sch[] = [];
    asar: Asar[] = [];
    ae: Ae[] = [];
    csrA: CsrA[] = [];
    stcl: Stcl[] = [];
    cnt: Cnt[] = [];
    lcp: Lcp[] = [];
    csr: Csr[] = [];
    stcg: Stcg[] = [];
    sub: Sub[] = [];
    tk: Tk[] = [];
    nod: Nod[] = [];
    sgFlexContainerResource: FlexContainerResource[] = [];
    acp: Acp[] = [];
    mssp: Mssp[] = [];
    req: Req[] = [];
    mgc: Mgc[] = [];
}

export class ChildResourceRef {
    value: string = undefined;
    nm: string = undefined;
    typ: number = undefined;
    spid: string = undefined;
}

export class Cin extends AnnounceableSubordinateResource {
    st: number = undefined;
    cr: string = undefined;
    cnf: string = undefined;
    cs: number = undefined;
    conr: ContentRef = undefined;
    or: string = undefined;
    con: any = undefined;
    ch: ChildResourceRef[] = [];
    smd: Smd[] = [];
}

export class CinA extends AnnouncedSubordinateResource {
    st: number = undefined;
    cnf: string = undefined;
    cs: number = undefined;
    or: string = undefined;
    con: any = undefined;
    ch: ChildResourceRef[] = [];
    smd: Smd[] = [];
}

export class Cmbf extends MgmtResource {
    aec: string[] = [];
    mbfs: number = undefined;
    sgp: number = undefined;
}

export class Cmdf extends MgmtResource {
    cmlk: MgmtLinkRef[] = [];
}

export class Cmdv extends MgmtResource {
    od: number = undefined;
    dev: string = undefined;
    ror: string[] = [];
    rct: any = undefined;
    rctn: boolean = undefined;
    rch: any = undefined;
}

export class Cml extends MgmtResource {
    od: number = undefined;
    ror: string[] = [];
    rct: any = undefined;
    rctn: boolean = undefined;
    rch: any = undefined;
    lec: string[] = [];
    lqet: number[] = [];
    lset: number[] = [];
    loet: number[] = [];
    lrp: number[] = [];
    lda: string = undefined;
}

export class Cmnr extends MgmtResource {
    aecs: string[] = [];
    cmlk: MgmtLinkRef[] = [];
}

export class Cmp extends MgmtResource {
    cpn: string = undefined;
    cmlk: MgmtLinkRef[] = [];
}

export class Cmpv extends MgmtResource {
    aec: string[] = [];
    dqet: number = undefined;
    dset: number = undefined;
    doet: number = undefined;
    drp: number = undefined;
    dda: boolean = undefined;
}

export class Cmwr extends MgmtResource {
    ttn: string[] = [];
    mrv: number = undefined;
    swt: number = undefined;
    bop: BackOffParameters = undefined;
    ohc: any = undefined;
    cmlk: MgmtLinkRef = undefined;
}

export class Cnt extends AnnounceableResource {
    la: string = undefined; // NOTE mschoeffler: manually added, as this is part of the next release
    ol: string = undefined; // NOTE mschoeffler: manually added, as this is part of the next release    
    st: number = undefined;
    cr: string = undefined;
    mni: number = undefined;
    mbs: number = undefined;
    mia: number = undefined;
    cni: number = undefined;
    cbs: number = undefined;
    li: string = undefined;
    or: string = undefined;
    disr: boolean = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
    smd: Smd[] = [];
    sgFlexContainerResource: FlexContainerResource[] = [];
    cin: Cin[] = [];
    cnt: Cnt[] = [];
}

export class CntA extends AnnouncedResource {
    st: number = undefined;
    mni: number = undefined;
    mbs: number = undefined;
    mia: number = undefined;
    cni: number = undefined;
    cbs: number = undefined;
    li: string = undefined;
    or: string = undefined;
    disr: boolean = undefined;
    ch: ChildResourceRef[] = [];
    cinOrCinAOrCnt: any[] = [];
}

export class ContentRef {
    urir: Urir[] = [];
}

export class Csr extends AnnounceableResource {
    cst: number = undefined;
    poa: string[] = [];
    cb: string = undefined;
    csi: string = undefined;
    mei: string = undefined;
    tri: number = undefined;
    rr: boolean = undefined;
    nl: string = undefined;
    esi: E2ESecInfo = undefined;
    trn: number = undefined;
    ch: ChildResourceRef[] = [];
    nodAOrCntOrCntA: any[] = [];
}

export class CsrA extends AnnouncedResource {
    cst: number = undefined;
    poa: string[] = [];
    cb: string = undefined;
    csi: string = undefined;
    rr: boolean = undefined;
    nl: string = undefined;
    esi: E2ESecInfo = undefined;
    ch: ChildResourceRef[] = [];
    nodAOrCntOrCntA: any[] = [];
}

export class Dac extends RegularResource {
    dae: boolean = undefined;
    dap: string[] = [];
    dal: string = undefined;
}

export class DataLink {
    nm: string = undefined;
    dcid: string = undefined;
    atn: string = undefined;
}

export class DeletionContexts {
    tod: string[] = [];
    lr: LocationRegion[] = [];
}

export class DeliveryMetaData {
    tcop: boolean = undefined;
    tcin: string[] = [];
}

export class Dlv extends RegularResource {
    st: number = undefined;
    sr: string = undefined;
    tg: string = undefined;
    ls: string = undefined;
    eca: string = undefined;
    dmd: DeliveryMetaData = undefined;
    arq: any = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class DownloadArgsType {
    ftyp: string = undefined;
    url: string = undefined;
    unm: string = undefined;
    pwd: string = undefined;
    fsi: number = undefined;
    tgf: string = undefined;
    dss: number = undefined;
    surl: string = undefined;
    stt: string = undefined;
    cpt: string = undefined;
    any: AnyArgType[] = [];
}

export class Dvc extends MgmtResource {
    can: string = undefined;
    att: boolean = undefined;
    cas: ActionStatus = undefined;
    cus: boolean = undefined;
    ena: boolean = undefined;
    dis: boolean = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class DvcA extends AnnouncedMgmtResource {
    can: string = undefined;
    att: boolean = undefined;
    cas: ActionStatus = undefined;
    cus: boolean = undefined;
    ena: boolean = undefined;
    dis: boolean = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class Dvi extends MgmtResource {
    dlb: string = undefined;
    man: string = undefined;
    mod: string = undefined;
    dty: string = undefined;
    fwv: string = undefined;
    swv: string = undefined;
    hwv: string = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class DviA extends AnnouncedMgmtResource {
    dlb: string = undefined;
    man: string = undefined;
    mod: string = undefined;
    dty: string = undefined;
    fwv: string = undefined;
    swv: string = undefined;
    hwv: string = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class DynAuthDasRequest {
    org: string = undefined;
    trt: number = undefined;
    op: number = undefined;
    oip: Oip = undefined;
    olo: LocationRegion = undefined;
    orid: string[] = [];
    rts: string = undefined;
    trid: string = undefined;
    ppl: string = undefined;
    rfa: string[] = [];
    tids: string[] = [];
}

export class DynAuthDasResponse {
    dai: Dai = undefined;
    tkns: string[] = [];
}

export class DynAuthLocalTokenIdAssignments {
    ltia: Ltia[] = [];
}

export class DynAuthTokenReqInfo {
    dasi: Dasi[] = [];
}

export class DynAuthTokenSummary {
    tkid: string = undefined;
    tknb: string = undefined;
    tkna: string = undefined;
    tknm: string = undefined;
    tkau: string[] = [];
}

export class E2ESecInfo {
    esf: number[] = [];
    escert: any[] = [];
    esro: ReceiverESPrimRandObject = undefined;
}

export class Evcg extends RegularResource {
    cr: string = undefined;
    evi: string = undefined;
    evt: number = undefined;
    evs: string = undefined;
    eve: string = undefined;
    opt: number[] = [];
    ds: number = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class EventNotificationCriteria {
    crb: string = undefined;
    cra: string = undefined;
    ms: string = undefined;
    us: string = undefined;
    sts: number = undefined;
    stb: number = undefined;
    exb: string = undefined;
    exa: string = undefined;
    sza: number = undefined;
    szb: number = undefined;
    om: number[] = [];
    atr: Attribute[] = [];
    net: number[] = [];
    md: MissingData = undefined;
}

export class Evl extends MgmtResource {
    lgt: number = undefined;
    lgd: string = undefined;
    lgst: number = undefined;
    lga: boolean = undefined;
    lgo: boolean = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class EvlA extends AnnouncedMgmtResource {
    lgt: number = undefined;
    lgd: string = undefined;
    lgst: number = undefined;
    lga: boolean = undefined;
    lgo: boolean = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class ExecReqArgsListType {
    rst: ResetArgsType[] = [];
    rbo: RebootArgsType[] = [];
    uld: UploadArgsType[] = [];
    dld: DownloadArgsType[] = [];
    swin: SoftwareInstallArgsType[] = [];
    swup: SoftwareUpdateArgsType[] = [];
    swun: SoftwareUninstallArgsType[] = [];
}

export class Exin extends RegularResource {
    exs: number = undefined;
    exr: number = undefined;
    exd: boolean = undefined;
    ext: string = undefined;
    exm: number = undefined;
    exf: Duration = undefined;
    exy: Duration = undefined;
    exn: number = undefined;
    exra: ExecReqArgsListType = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class FilterCriteria {
    crb: string = undefined;
    cra: string = undefined;
    ms: string = undefined;
    us: string = undefined;
    sts: number = undefined;
    stb: number = undefined;
    exb: string = undefined;
    exa: string = undefined;
    lbl: string[] = [];
    ty: number[] = [];
    sza: number = undefined;
    szb: number = undefined;
    cty: string[] = [];
    atr: Attribute[] = [];
    fu: number = undefined;
    lim: number = undefined;
    smf: string[] = [];
    fo: boolean = undefined;
    cfs: number = undefined;
    cfq: string = undefined;
    lvl: number = undefined;
    ofst: number = undefined;
}

export class Fwr extends MgmtResource {
    vr: string = undefined;
    fwn: string = undefined;
    url: string = undefined;
    ud: boolean = undefined;
    uds: ActionStatus = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class FwrA extends AnnouncedMgmtResource {
    vr: string = undefined;
    fwn: string = undefined;
    url: string = undefined;
    ud: boolean = undefined;
    uds: ActionStatus = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class Gio extends FlexContainerResource {
    gion: string = undefined;
    gios: string = undefined;
    giip: ListOfDataLinks = undefined;
    giop: ListOfDataLinks = undefined;
    giil: ListOfDataLinks = undefined;
    giol: ListOfDataLinks = undefined;
    ch: ChildResourceRef[] = [];
    smdOrSub: RegularResource[] = [];
}

export class Gioa extends AnnouncedFlexContainerResource {
    gion: string = undefined;
    gios: string = undefined;
    giip: ListOfDataLinks = undefined;
    giop: ListOfDataLinks = undefined;
    giil: ListOfDataLinks = undefined;
    giol: ListOfDataLinks = undefined;
    ch: ChildResourceRef[] = [];
    smdOrSmdAOrSub: Resource[] = [];
}

export class Gis extends FlexContainerResource {
    gisn: string = undefined;
    giip: string = undefined;
    giop: string[] = [];
    ch: ChildResourceRef[] = [];
    gisOrGioOrSmd: any[] = [];
}

export class Gisa extends AnnouncedFlexContainerResource {
    gisn: string = undefined;
    giip: ListOfDataLinks = undefined;
    giop: ListOfDataLinks = undefined;
    ch: ChildResourceRef[] = [];
    gisaOrGioaOrSmd: any[] = [];
}

export class Grp extends AnnounceableResource {
    cr: string = undefined;
    mt: number = undefined;
    cnm: number = undefined;
    mnm: number = undefined;
    mid: string[] = [];
    macp: string[] = [];
    mtv: boolean = undefined;
    csy: number = undefined;
    gn: string = undefined;
    ch: ChildResourceRef[] = [];
    subOrSmd: RegularResource[] = [];
}

export class GrpA extends AnnouncedResource {
    mt: number = undefined;
    cnm: number = undefined;
    mnm: number = undefined;
    mid: string[] = [];
    macp: string[] = [];
    mtv: boolean = undefined;
    csy: number = undefined;
    gn: string = undefined;
    ch: ChildResourceRef[] = [];
    subOrSmdOrSmdA: Resource[] = [];
}

export class Lcp extends AnnounceableResource {
    los: number = undefined;
    lou: Duration[] = [];
    lot: string = undefined;
    lor: string = undefined;
    loi: string = undefined;
    lon: string = undefined;
    lost: string = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class LcpA extends AnnouncedResource {
    los: number = undefined;
    lou: Duration[] = [];
    lot: string = undefined;
    lor: string = undefined;
    loi: string = undefined;
    lon: string = undefined;
    lost: string = undefined;
}

export class ListOfChildResourceRef {
    ch: ChildResourceRef[] = [];
}

export class ListOfDataLinks {
    dle: DataLink[] = [];
}

export class LocationRegion {
    accc: string[] = [];
    accr: number[] = [];
}

export class Mem extends MgmtResource {
    mma: number = undefined;
    mmt: number = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class MemA extends AnnouncedMgmtResource {
    mma: number = undefined;
    mmt: number = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class MetaInformation {
    ty: number = undefined;
    nm: string = undefined;
    ot: string = undefined;
    rqet: string = undefined;
    rset: string = undefined;
    oet: string = undefined;
    rt: ResponseTypeInfo = undefined;
    rp: string = undefined;
    rcn: number = undefined;
    ec: string = undefined;
    da: boolean = undefined;
    gid: string = undefined;
    fc: FilterCriteria = undefined;
    drt: number = undefined;
}

export class Mgc extends RegularResource {
    dc: string = undefined;
    cmt: number = undefined;
    exra: ExecReqArgsListType = undefined;
    exe: boolean = undefined;
    ext: string = undefined;
    exm: number = undefined;
    exf: Duration = undefined;
    exy: Duration = undefined;
    exn: number = undefined;
    ch: ChildResourceRef[] = [];
    exinOrSub: RegularResource[] = [];
}

export class MgmtLinkRef {
    value: string = undefined;
    nm: string = undefined;
    typ: number = undefined;
}

export class MissingData {
    num: number = undefined;
    dur: Duration = undefined;
}

export class Mssp extends RegularResource {
    ch: ChildResourceRef[] = [];
    svsnOrSub: RegularResource[] = [];
}

export class Nod extends AnnounceableResource {
    ni: string = undefined;
    hcl: string = undefined;
    ch: ChildResourceRef[] = [];
    memOrBatOrAni: RegularResource[] = [];
}

export class NodA extends AnnouncedResource {
    ni: string = undefined;
    hcl: string = undefined;
    ch: ChildResourceRef[] = [];
    memAOrBatAOrAniA: Resource[] = [];
}

export class Notification {
    nev: Nev = undefined;
    vrq: boolean = undefined;
    sud: boolean = undefined;
    sur: string = undefined;
    cr: string = undefined;
    nfu: string = undefined;
    idr: Idr = undefined;
}

export class Ntp extends RegularResource {
    cr: string = undefined;
    ac: number = undefined;
    plbl: string = undefined;
    rrs: number = undefined;
    ch: ChildResourceRef[] = [];
    pdrOrSub: RegularResource[] = [];
}

export class Ntpr extends RegularResource {
    ntu: string[] = [];
    npi: string = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class ObjectFactory {
}

export class OperationResult {
    rsc: number = undefined;
    rqi: string = undefined;
    pc: PrimitiveContent = undefined;
    to: string = undefined;
    fr: string = undefined;
    ot: string = undefined;
    rset: string = undefined;
    ec: string = undefined;
    cnst: number = undefined;
    cnot: number = undefined;
}

export class OriginatorESPrimRandObject {
    esri: string = undefined;
    esrv: string = undefined;
    esrx: string = undefined;
    esks: number = undefined;
    espa: number[] = [];
}

export class SubordinateResource extends Resource {
    et: string = undefined;
}

export class Pch extends SubordinateResource {
}

export class Pdr extends RegularResource {
    dr: DeletionContexts = undefined;
    drr: number = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class PrimitiveContent {
    anyOrAny: any[] = [];
}

export class RateLimit {
    mnn: number = undefined;
    tww: Duration = undefined;
}

export class Rbo extends MgmtResource {
    rbo: boolean = undefined;
    far: boolean = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class RboA extends AnnouncedMgmtResource {
    rbo: boolean = undefined;
    far: boolean = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class RebootArgsType {
    any: AnyArgType[] = [];
}

export class ReceiverESPrimRandObject {
    esri: string = undefined;
    esrv: string = undefined;
    esrx: string = undefined;
    esks: number[] = [];
    espa: number[] = [];
}

export class Req extends RegularResource {
    st: number = undefined;
    op: number = undefined;
    tg: string = undefined;
    org: string = undefined;
    rid: string = undefined;
    mi: MetaInformation = undefined;
    pc: PrimitiveContent = undefined;
    rs: number = undefined;
    ors: OperationResult = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class ResetArgsType {
    any: AnyArgType[] = [];
}

export class ResourceWrapper {
    sgResource: Resource = undefined;
    sgRegularResource: RegularResource = undefined;
    sgAnnouncedResource: AnnouncedResource = undefined;
    sgAnnounceableResource: AnnounceableResource = undefined;
    sgSubordinateResource: SubordinateResource = undefined;
    sgAnnouncedSubordinateResource: AnnouncedSubordinateResource = undefined;
    sgAnnounceableSubordinateResource: AnnounceableSubordinateResource = undefined;
    sgMgmtResource: MgmtResource = undefined;
    sgAnnouncedMgmtResource: AnnouncedMgmtResource = undefined;
    sgFlexContainerResource: FlexContainerResource = undefined;
    sgAnnouncedFlexContainerResource: AnnouncedFlexContainerResource = undefined;
    uri: string = undefined;
}

export class ResponseTypeInfo {
    rtv: number = undefined;
    nu: string[] = [];
}

export class Rol extends RegularResource {
    rlid: string = undefined;
    tkis: string = undefined;
    tkhd: string = undefined;
    tknb: string = undefined;
    tkna: string = undefined;
    rlnm: string = undefined;
    rltl: string = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class Rqp {
    op: number = undefined;
    to: string = undefined;
    fr: string = undefined;
    rqi: string = undefined;
    ty: number = undefined;
    pc: PrimitiveContent = undefined;
    rids: string[] = [];
    ot: string = undefined;
    rqet: string = undefined;
    rset: string = undefined;
    oet: string = undefined;
    rt: ResponseTypeInfo = undefined;
    rp: string = undefined;
    rcn: number = undefined;
    ec: string = undefined;
    da: boolean = undefined;
    gid: string = undefined;
    fc: FilterCriteria = undefined;
    drt: number = undefined;
    tkns: string = undefined;
    tids: string = undefined;
    ltids: string[] = [];
    tqi: boolean = undefined;
}

export class Rsp {
    rsc: number = undefined;
    rqi: string = undefined;
    pc: PrimitiveContent = undefined;
    to: string = undefined;
    fr: string = undefined;
    ot: string = undefined;
    rset: string = undefined;
    ec: string = undefined;
    cnst: number = undefined;
    cnot: number = undefined;
    ati: DynAuthLocalTokenIdAssignments = undefined;
    tqf: DynAuthTokenReqInfo = undefined;
}

export class Sch extends AnnounceableSubordinateResource {
    se: ScheduleEntries = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class SchA extends AnnouncedSubordinateResource {
    se: ScheduleEntries = undefined;
}

export class ScheduleEntries {
    sce: string[] = [];
}

export class SecurityInfo {
    sit: number = undefined;
    daq: DynAuthDasRequest = undefined;
    dres: DynAuthDasResponse = undefined;
    ero: ReceiverESPrimRandObject = undefined;
    epo: string = undefined;
    eckm: any = undefined;
}

export class SetOfAcrs {
    acr: AccessControlRule[] = [];
}

export class Smd extends AnnounceableResource {
    cr: string = undefined;
    dcrp: string = undefined;
    soe: string = undefined;
    dsp: any = undefined;
    or: string = undefined;
    rels: string[] = [];
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class SmdA extends AnnouncedResource {
    dcrp: string = undefined;
    soe: string = undefined;
    dsp: any = undefined;
    or: string = undefined;
    rels: string[] = [];
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class SoftwareInstallArgsType {
    url: string = undefined;
    uuid: string = undefined;
    unm: string = undefined;
    pwd: string = undefined;
    eer: string = undefined;
    any: AnyArgType[] = [];
}

export class SoftwareUninstallArgsType {
    uuid: string = undefined;
    vr: string = undefined;
    eer: string = undefined;
    any: AnyArgType[] = [];
}

export class SoftwareUpdateArgsType {
    uuid: string = undefined;
    vr: string = undefined;
    url: string = undefined;
    unm: string = undefined;
    pwd: string = undefined;
    eer: string = undefined;
    any: AnyArgType[] = [];
}

export class Stcg extends RegularResource {
    cr: string = undefined;
    ch: ChildResourceRef[] = [];
    evcgOrSub: RegularResource[] = [];
}

export class Stcl extends RegularResource {
    cr: string = undefined;
    sci: string = undefined;
    cei: string = undefined;
    cdi: string = undefined;
    srs: number = undefined;
    sm: number = undefined;
    cp: ScheduleEntries = undefined;
    evi: string = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class Sub extends RegularResource {
    cr: string = undefined;
    enc: EventNotificationCriteria = undefined;
    exc: number = undefined;
    nu: string[] = [];
    gpi: string = undefined;
    nfu: string = undefined;
    bn: BatchNotify = undefined;
    rl: RateLimit = undefined;
    psn: number = undefined;
    pn: number = undefined;
    nsp: number = undefined;
    ln: boolean = undefined;
    nct: number = undefined;
    nec: string = undefined;
    su: string = undefined;
    ch: ChildResourceRef[] = [];
    schOrNtpr: Resource[] = [];
}

export class Svsn extends RegularResource {
    ni: string = undefined;
    csi: string = undefined;
    di: string[] = [];
    rlk: string[] = [];
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class Swr extends MgmtResource {
    vr: string = undefined;
    swn: string = undefined;
    url: string = undefined;
    in: boolean = undefined;
    un: boolean = undefined;
    ins: ActionStatus = undefined;
    act: boolean = undefined;
    dea: boolean = undefined;
    acts: ActionStatus = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class SwrA extends AnnouncedMgmtResource {
    vr: string = undefined;
    swn: string = undefined;
    url: string = undefined;
    in: boolean = undefined;
    un: boolean = undefined;
    ins: ActionStatus = undefined;
    act: boolean = undefined;
    dea: boolean = undefined;
    acts: ActionStatus = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class Tk extends RegularResource {
    tkid: string = undefined;
    tkob: string = undefined;
    vr: string = undefined;
    tkis: string = undefined;
    tkhd: string = undefined;
    tknb: string = undefined;
    tkna: string = undefined;
    tknm: string = undefined;
    tkau: string[] = [];
    tkps: Tkps = undefined;
    tkex: string = undefined;
    ch: ChildResourceRef[] = [];
    sub: Sub[] = [];
}

export class TokenClaimSet {
    vr: string = undefined;
    tkid: string = undefined;
    tkhd: string = undefined;
    tkis: string = undefined;
    tknb: string = undefined;
    tkna: string = undefined;
    tknm: string = undefined;
    tkau: string[] = [];
    tkps: TokenPermissions = undefined;
    tkex: string = undefined;
}

export class TokenPermission {
    ris: string[] = [];
    pv: SetOfAcrs = undefined;
    rids: string[] = [];
}

export class TokenPermissions {
    pm: TokenPermission[] = [];
}

export class Trpt extends AnnounceableResource {
    ptn: boolean = undefined;
    pri: number = undefined;
    pdt: number = undefined;
    pit: number = undefined;
    sti: number = undefined;
    dsi: number = undefined;
    vdt: string = undefined;
    ttn: string[] = [];
    ch: ChildResourceRef[] = [];
    subOrSch: Resource[] = [];
}

export class TrptA extends AnnouncedResource {
    ptn: boolean = undefined;
    pri: number = undefined;
    pdt: number = undefined;
    pit: number = undefined;
    sti: number = undefined;
    dsi: number = undefined;
    vdt: string = undefined;
    ttn: string[] = [];
    ch: ChildResourceRef[] = [];
    subOrSchA: Resource[] = [];
}

export class Ts extends AnnounceableResource {
    st: number = undefined;
    cr: string = undefined;
    mni: number = undefined;
    mbs: number = undefined;
    mia: number = undefined;
    cni: number = undefined;
    cbs: number = undefined;
    pei: number = undefined;
    mdd: boolean = undefined;
    mdn: number = undefined;
    mdl: string[] = [];
    mdc: number = undefined;
    mdt: number = undefined;
    or: string = undefined;
    la: string = undefined;
    ol: string = undefined;
    ch: ChildResourceRef[] = [];
    tsiOrSubOrSmd: Resource[] = [];
}

export class Tsa extends AnnouncedResource {
    st: number = undefined;
    mni: number = undefined;
    mbs: number = undefined;
    mia: number = undefined;
    cni: number = undefined;
    cbs: number = undefined;
    pei: number = undefined;
    mdd: boolean = undefined;
    mdl: string[] = [];
    mdc: number = undefined;
    mdt: number = undefined;
    or: string = undefined;
    ch: ChildResourceRef[] = [];
    tsiOrTsiaOrSub: Resource[] = [];
}

export class Tsi extends AnnounceableSubordinateResource {
    dgt: string = undefined;
    con: any = undefined;
    snr: number = undefined;
}

export class Tsia extends AnnouncedSubordinateResource {
    dgt: string = undefined;
    con: any = undefined;
    snr: number = undefined;
}

export class UploadArgsType {
    ftyp: string = undefined;
    url: string = undefined;
    unm: string = undefined;
    pwd: string = undefined;
    any: AnyArgType[] = [];
}

export class Acco {
    actw: string[] = [];
    acip: Acip = undefined;
    aclr: LocationRegion = undefined;
}

export class Bops {
    nwa: number = undefined;
    ibt: number = undefined;
    abt: number = undefined;
    mbt: number = undefined;
    rbt: number = undefined;
}

export class Duration {
}

export class Urir {
    nm: string = undefined;
    uri: string = undefined;
}

export class Oip {
    ip4: string = undefined;
    ip6: string = undefined;
}

export class Dai {
    gp: SetOfAcrs = undefined;
    pl: string = undefined;
}

export class Ltia {
    lti: string = undefined;
    tkid: string = undefined;
}

export class Dasi {
    uri: string = undefined;
    daq: DynAuthDasRequest = undefined;
    sdr: string = undefined;
}

export class Nev {
    rep: any = undefined;
    om: Om = undefined;
    net: number = undefined;
}

export class Idr {
    org: string = undefined;
    fc: FilterCriteria = undefined;
}

export class Tkps {
    pm: TokenPermission = undefined;
}

export class Acip {
    ipv4: string[] = [];
    ipv6: string[] = [];
}

export class Om {
    op: number = undefined;
    org: string = undefined;
}

export type PermittedMediaTypes = "APPLICATION_XML" | "APPLICATION_JSON" | "APPLICATION_CBOR";
