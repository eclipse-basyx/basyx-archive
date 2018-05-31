/*
 * BRef.h - Smart reference class
 *
 *      Author: kuhn
 */

#ifndef REF_BREF_H_
#define REF_BREF_H_


/* ****************************************************************
 * Include files
 * ****************************************************************/
#include <types/BNullObject.h>
#include <types/BValue.h>
#include <types/BString.h>
#include <types/BArray.h>


/* ****************************************************************
 * BRef class implements smart pointers
 * ****************************************************************/
template <typename T> class BRef {

	// Internal members
	public:

		// Indicate if pointer should be freed when access counter reaches one
		bool freeOnZero;

		// Store reference to element
		T* containedReference;

		// Base reference counter
		// - This field is only used if the BRef was created with default constructor, not with copy constructor
		int *refCounter;

		// Pointer to reference counter used by this instance
		int *refCounterRef;



	// Default constructor
	public:
		// Default constructor
		BRef(T *pointer, bool parFreeOnzero = true) {
			// Store parameter values
			freeOnZero         = parFreeOnzero;
			containedReference = pointer;
			refCounter         = (int *) malloc(sizeof(int));
			refCounterRef      = refCounter;
			*refCounterRef     = 1;

			// Null pointer check
			if (containedReference == 0) freeOnZero=false;

			printf("Constructing %i!\n", containedReference);
		}


	// Supporting constructors
	public:
		// Create null reference
		BRef() : BRef(new BNullObject(), true) {}                                 // @suppress("Class members should be properly initialized")
		// Create value references
		BRef(int    value) : BRef(new BValue(value), true) {}                     // @suppress("Class members should be properly initialized")
		BRef(float  value) : BRef(new BValue(value), true) {}                     // @suppress("Class members should be properly initialized")
		BRef(double value) : BRef(new BValue(value), true) {}                     // @suppress("Class members should be properly initialized")
		BRef(bool   value) : BRef(new BValue(value), true) {}                     // @suppress("Class members should be properly initialized")
		BRef(char   value) : BRef(new BValue(value), true) {}                     // @suppress("Class members should be properly initialized")
		// Create string references
		BRef(std::string value) : BRef(new BString(value), true) {}               // @suppress("Class members should be properly initialized")
		// Create array references
		BRef(int    *value, int size) : BRef(new BArray(value, size), true) {}    // @suppress("Class members should be properly initialized")
		BRef(float  *value, int size) : BRef(new BArray(value, size), true) {}    // @suppress("Class members should be properly initialized")
		BRef(double *value, int size) : BRef(new BArray(value, size), true) {}    // @suppress("Class members should be properly initialized")
		BRef(bool   *value, int size) : BRef(new BArray(value, size), true) {}    // @suppress("Class members should be properly initialized")
		BRef(char   *value, int size) : BRef(new BArray(value, size), true) {}    // @suppress("Class members should be properly initialized")


	// Constructors and operators for reference counting
	public:
		// Copy constructor
		// - C++ standard permits copy elision as optimization. In this case, one pair of
		//   descturctors/constructors is not called, and thus a copy is not created.
		//   Instead an already created object is passed. This should not affect the
		//   reference counting mechanism here, as copy elision never affects the total
		//   number of created objects.
		BRef(const BRef<T> &other) {
			// Update fields
			freeOnZero         = other.freeOnZero;
			refCounterRef      = other.refCounterRef;
			containedReference = other.containedReference;
			refCounter         = 0;

			// Increase reference counter
			(*refCounterRef)++;

			printf("Copying %i %i!\n", containedReference, (*refCounterRef));
		}


		// Assignment operator
		// - Overriding of this operator tracks value assignments
		BRef<T>& operator = (const BRef<T> &other) {
			// Update fields
			freeOnZero         = other.freeOnZero;
			refCounterRef      = other.refCounterRef;
			containedReference = other.containedReference;
			refCounter         = 0;

			// Increase reference counter
			(*refCounterRef)++;

			printf("Assigning %i %i!\n", containedReference, (*refCounterRef));

			// Return assigned value
			return *this;
		}


	    // Implicit conversion operator for contained pointer type
	    template <typename U> operator BRef<U>() const {
			printf("Casting %i %i!\n", containedReference, (*refCounterRef));

			// This cast is safe, because the template argument is only used as pointer type, and pointer types always have same size
	    	return *((BRef<U> *) this);
	    }


		// Destructor
		~BRef() {
			printf("Desctructing %i %i %i!\n", containedReference, *refCounterRef, refCounterRef);

			// Decrement reference counter
			(*refCounterRef)--;

			printf("Desctructing2 %i %i %i!\n", containedReference, *refCounterRef, refCounterRef);
			// Error check
			if (*refCounterRef < 0) printf("BRef semantic error!\n");

			// Delete element if necessary
			// - Necessary means deleting is requested (freeOnZero) and reference counter reaches zero
			if (((*refCounterRef) == 0) && (freeOnZero)) {delete containedReference; containedReference=0; printf("Deleted!\n");}
			// - Make sure the reference counter pointer is freed, since this is the last instance
			if ((*refCounterRef) == 0) free(refCounterRef);
		}


	// Public member functions
	public:
		// Get element reference
		T* getRef() {return containedReference;}

		// Get reference counter
		int getRefCnt() {return *refCounterRef;}

		// Check if a class should be freed on zero
		bool getFreeOnZero() {return freeOnZero;}


	// Operator overloading for ease of access and for preventing misuse
	public:
	    // Member access operator. This class should never be used as pointer.
		// Therefore, member access points to contained pointer.
	    T* operator->() const {return containedReference;}

	    // Prevent taking address of BRef objects. Taking addresses will void the
	    // purpose of this class.
	    T* operator&() {return 0;}
};



#endif /* REF_BREF_H_ */
