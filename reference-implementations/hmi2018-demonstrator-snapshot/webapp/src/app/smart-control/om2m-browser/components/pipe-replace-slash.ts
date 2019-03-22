import { PipeTransform, Pipe } from "@angular/core";

@Pipe({ name: 'replaceSlash', pure: false })
export class ReplaceSlashPipe implements PipeTransform {
	transform(value: any): any {
		if (value != null)
			return value.replace(/\//g, ' / ');
		// return value.replace(/\//g, ' \#a3d39c; ');
		// return value.replace(/\//g, '&#xf0da;');
		// return value.replace('/', '&#xf0da;');
		// return value.replace(/ /g, "--");
		else return '';
	}
}
