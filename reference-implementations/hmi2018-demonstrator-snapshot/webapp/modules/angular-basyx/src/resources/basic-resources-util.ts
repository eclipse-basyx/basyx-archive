
import { DataType, AssetKind } from './basic-resources';

export class ResourcesUtil {

    public static convertStrToDataType(str: String): DataType {
        switch (str) {
            case "ref":
                return DataType.REFERENCE;
            case "bool":
                return DataType.BOOLEAN;
            case "int":
                return DataType.INTEGER;
            case "float":
                return DataType.FLOAT;
            case "string":
                return DataType.STRING;
            case "void":
                return DataType.VOID;
        }
        return null;
    }

    public static convertDataTypeToStr(dt: DataType): String {
        switch (dt) {
            case DataType.REFERENCE:
                return "ref";
            case DataType.BOOLEAN:
                return "bool";
            case DataType.INTEGER:
                return "int";
            case DataType.FLOAT:
                return "float";
            case DataType.STRING:
                return "string";
            case DataType.VOID:
                return "void";
        }
        return null;
    }

    public static convertStrToAssetKind(str: String): AssetKind {
        switch (str) {
            case "inst":
                return AssetKind.INSTANCE;
            case "ty":
                return AssetKind.TYPE;
        }
        return null;
    }

    public static convertAssetKindToStr(ak: AssetKind): string {
        switch (ak) {
            case AssetKind.INSTANCE:
                return "inst";
            case AssetKind.TYPE:
                return "ty";
        }
        return null;
    }
}
