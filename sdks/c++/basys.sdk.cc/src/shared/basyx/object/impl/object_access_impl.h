#ifndef SHARED_BASYX_OBJECT_IMPL_OBJECT_ACCESS_IMPL_H
#define SHARED_BASYX_OBJECT_IMPL_OBJECT_ACCESS_IMPL_H

template<typename T>
bool basyx::object::insert(const T & t)
{
	if (!this->content)
		return false;

	// Check if contained object is list or set
	switch (content->object_type())
	{
	case basyx::type::objectType::List:
		if (this->InstanceOf<object::list_t<T>>())
		{
			auto & vec = this->Get<object::list_t<T>&>();
			vec.push_back(t);
			return true;
		};
		break;
	case basyx::type::objectType::Set:
		if (this->InstanceOf<object::set_t<T>>())
		{
			auto & set = this->Get<object::set_t<T>&>();
			return set.emplace(t).second;
		};
		break;
	};
	return false;
};



template<typename T>
bool basyx::object::insertKey(const std::string & key, const T & t, bool override)
{
	if (!this->content)
		return false;

	// Check if contained object is hashmap
	if (content->object_type() == basyx::type::objectType::Map)
	{
		// Check contained type
		if (this->InstanceOf<object::object_map_t>())
		{
			auto & map = this->Get<object::object_map_t&>();

			// Check if key already exists
			if (map.find(key) != map.end())
			{
				// if override, set new value, else return false
				if (override) {
					map[key] = t;
					return true;
				};

				return false;
			}
			else // key doesn't exist, just insert object
			{
				map.emplace(key, t);
				return true;
			}
		}
	};

	return false;
};

template<typename T>
bool basyx::object::remove(const T & t)
{
	if (!this->content)
		return false;

	// Check if contained object is list or set
	switch (content->object_type())
	{
	case basyx::type::objectType::Set:
		if (this->InstanceOf<object::set_t<T>>())
		{
			auto & set = this->Get<object::set_t<T>&>();
			return set.erase(t) > 0;
		};
		break;
	case basyx::type::objectType::List:
		auto & list = this->Get<object::list_t<T>&>();
		list.erase(std::remove(list.begin(), list.end(), t), list.end());
		break;
	};
	return false;
}

inline bool basyx::object::remove(basyx::object & obj)
{
	auto valueType = obj.GetValueType();
	switch(valueType)
	{
	case basyx::type::valueType::Bool:
		return this->remove(obj.Get<const bool>());
		break;
	case basyx::type::valueType::Int:
		return this->remove(obj.Get<const int>());
		break;
	case basyx::type::valueType::String:
		return this->remove(obj.Get<const std::string>());
		break;
	case basyx::type::valueType::Float:
		return this->remove(obj.Get<const double>());
		break;

	};

	return false;
};

#endif /* SHARED_BASYX_OBJECT_IMPL_OBJECT_ACCESS_IMPL_H */
