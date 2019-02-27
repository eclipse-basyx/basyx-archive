export class Host {
    key: string;

    serverUrl: string;
    port: number;

    relativePath: string;
    description: string;

    from: string;
    username: string;
    password: string;

    constructor(serverUrl: string, port: number) {
        this.serverUrl = serverUrl;
        this.port = port;
    }

    getStringOM2M() {
        return this.serverUrl + ':' + this.port + '/' + this.relativePath;
    }

    getHostOM2M(): HostOM2M {
        return new HostOM2M(this.key, this.serverUrl, this.port, this.from);
    }
}

export class HostOM2M extends Host {
    constructor(key: string, serverUrl: string, port: number, from: string) {
        super(serverUrl, port);
        this.key = key;
        this.from = from;
    }
}
export class HostIOTDM extends Host {
    constructor(serverUrl: string, port: number, relativePath: string) {
        super(serverUrl, port);
        this.relativePath = relativePath;
    }
}