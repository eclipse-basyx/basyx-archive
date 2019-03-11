/*
 * BaSyx.h
 * Contains all relevant data for the definition of the basyx protocol
 *  Created on: 16.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_BASYX_BASYX_H_
#define BACKENDS_PROTOCOLS_BASYX_BASYX_H_

enum BaSyxCommand {
		GET = 1,
		SET = 2,
		CREATE = 3,
		DEL = 4, // DELETE is a reserved keyword
		INVOKE = 5
};

#define BASYX_FRAMESIZE_SIZE 4
#define BASYX_STRINGSIZE_SIZE 4


#endif /* BACKENDS_PROTOCOLS_BASYX_BASYX_H_ */
