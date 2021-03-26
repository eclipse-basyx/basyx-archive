#ifndef BASYX_SHARED_OBJECT_IMPL_OBJECT_META_IMPL_H
#define BASYX_SHARED_OBJECT_IMPL_OBJECT_META_IMPL_H

template <typename T>
bool basyx::object::operator!=(const T& rhs) const
{
	return !this->operator==(rhs);
}

template <typename T>
bool basyx::object::operator==(const T& rhs) const
{
	if (this->type() == typeid(T)) {
		return const_cast<basyx::object*>(this)->Get<T&>() == rhs;
	}
	return false;
}


#endif /* BASYX_SHARED_OBJECT_IMPL_OBJECT_META_IMPL_H */
