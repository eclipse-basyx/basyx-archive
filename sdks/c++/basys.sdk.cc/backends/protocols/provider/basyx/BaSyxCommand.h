/*
 * BaSyxCommand.h
 *
 *  Created on: 15.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXCOMMAND_H_
#define BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXCOMMAND_H_


enum BaSyxCommand {
		GET = 1,
		SET = 2,
		CREATE = 3,
		DEL = 4, // DELETE is a reserved keyword
		INVOKE = 5
};


#endif /* BACKENDS_PROTOCOLS_PROVIDER_BASYX_BASYXCOMMAND_H_ */
