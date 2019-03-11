/*
 * BRef.h - Smart reference class
 *
 *      Author: kuhn
 *      Updated: psota on 31.01.2019
 *			Re-implemented old functionality through std::shared_ptr
 */

//#define REF_BREF_H_

#ifndef REF_BREF_H_
#define REF_BREF_H_


/* ****************************************************************
 * Include files
 * ****************************************************************/
#include <types/BNullObject.h>
#include <types/BValue.h>
#include <types/BString.h>
//#include <types/BArray.h>

#include <memory>


/* ****************************************************************
 * BRef class implements smart pointers
 * ****************************************************************/
template <typename T> class BRef {

	// Internal members
	private:
		// Create a custom deleter function that does nothing, thus not freeing the shared_ptr's resource
		static void deleter_NOP(T*) {};
	private:
		// Use a standard library shared pointer for internal holding and reference counting
		std::shared_ptr<T> ptr;

	public:
		// Non-Default constructors
		BRef(T *pointer, bool parFreeOnzero = true ) : ptr{ nullptr } {
			// Constructor of std::shared_ptr allows specifying of a custom deleter function to be invoked when ref count reaches zero
			// Signature of the deleter function must be 'void d(T*)'
			if (!parFreeOnzero){
				// Use the NOP deleter function
				ptr = std::shared_ptr<T>{ pointer, BRef::deleter_NOP };
			} else {
				ptr = std::shared_ptr<T>{ pointer };
			};
		}

		// Construct BRef from another shared_ptr
		// FreeOnZero behaviour is already stored inside the shared_ptr's deleter function
		template<typename U>
		BRef(std::shared_ptr<U> ptr) : ptr{ std::dynamic_pointer_cast<T>(ptr) } {};

		BRef(const T & val) : ptr{ std::make_shared<T>(val) } {};

	// Supporting constructors
	public:
		// Create null reference
		BRef() : BRef(std::make_shared<BNullObject>()) {}                   // @suppress("Class members should be properly initialized")

		// Create value references 
		BRef(int    value) : BRef(std::make_shared<BValue>(value)) {}		 // @suppress("Class members should be properly initialized")
		BRef(float  value) : BRef(std::make_shared<BValue>(value)) {}		 // @suppress("Class members should be properly initialized")
		BRef(double value) : BRef(std::make_shared<BValue>(value)) {}		 // @suppress("Class members should be properly initialized")
		BRef(bool   value) : BRef(std::make_shared<BValue>(value)) {}		 // @suppress("Class members should be properly initialized")
		BRef(char   value) : BRef(std::make_shared<BValue>(value)) {}		 // @suppress("Class members should be properly initialized")

		// Create string references
		BRef(const std::string & value) : BRef(std::make_shared<BString>(value)) {} // @suppress("Class members should be properly initialized")
		BRef(const char * value) : BRef(std::make_shared<BString>(value)) {} // @suppress("Class members should be properly initialized")

		// Create array references
		//BRef(int    *value, int size) : BRef(new BArray(value, size), true) {}    // @suppress("Class members should be properly initialized")
		//BRef(float  *value, int size) : BRef(new BArray(value, size), true) {}    // @suppress("Class members should be properly initialized")
		//BRef(double *value, int size) : BRef(new BArray(value, size), true) {}    // @suppress("Class members should be properly initialized")
		//BRef(bool   *value, int size) : BRef(new BArray(value, size), true) {}    // @suppress("Class members should be properly initialized")
		//BRef(char   *value, int size) : BRef(new BArray(value, size), true) {}    // @suppress("Class members should be properly initialized")

	public:
	    // Implicit conversion operator for contained pointer type
	    template <typename U> 
		operator BRef<U>() const 
		{
			// Create a new BRef with the new parameter type and coerce the original shared_ptr to it
			// Original and new BRef still hold the same shared_ptr, so reference counting is shared
			return BRef<U>( std::static_pointer_cast<U>(ptr) );
	    }


            bool operator==(const BRef & rhs) const
            {
                return false;
            }

	// Public member functions
	public:
		// Get element reference
		T* getRef() {return ptr->get();}

		// Get reference counter
		int getRefCnt() {return ptr.use_count();}

		// Check if the contained shared_ptr will free it's resource on destruction
		bool getFreeOnZero() 
		{
			return std::get_deleter<decltype(BRef::deleter_NOP)*>(ptr) == nullptr;
		}


	// Operator overloading for ease of access and for preventing misuse
	public:
	    // Member access operator. This class should never be used as pointer.
		// Therefore, member access points to contained pointer.
	    T* operator->() const {return ptr.get();}
};



#endif /* REF_BREF_H_ */
