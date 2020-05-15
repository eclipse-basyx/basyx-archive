#include <BaSyx/shared/object.h>

#include <BaSyx/shared/object/obj_function.h>

basyx::object::object()
	: content{ nullptr }, err{error::None} {};

basyx::object::object(const char * c)
	: object{ std::string(c) }
{
};

bool basyx::object::empty()
{
	if (!this->content)
		return true;

	auto object_type = this->GetObjectType();
	auto value_type = this->GetValueType();

	if (object_type == basyx::type::objectType::Primitive)
		return false;

	switch (this->content->object_type())
	{
	case basyx::type::objectType::List:
		switch (value_type)
		{
		case basyx::type::valueType::Bool:
			return this->Get<object::list_t<bool>&>().empty();
			break;
		case basyx::type::valueType::Int:
			return this->Get<object::list_t<int>&>().empty();
			break;
		case basyx::type::valueType::Float:
			return this->Get<object::list_t<double>&>().empty();
			break;
		case basyx::type::valueType::String:
			return this->Get<object::list_t<std::string>&>().empty();
			break;
		case basyx::type::valueType::Object:
			return this->Get<object::object_list_t&>().empty();
			break;
		};
	case basyx::type::objectType::Map:
		return this->Get<object::object_map_t&>().empty();
		break;
	}

	return true;
};

bool basyx::object::insert(basyx::object obj)
{
	if (!this->content)
		return false;

	auto object_type = obj.content->object_type();
	auto value_type = obj.content->value_type();

	//if (object_type != basyx::type::objectType::Primitive)
	//	return false;

	switch (this->content->object_type())
	{
	case basyx::type::objectType::List:
		if (this->content->value_type() == basyx::type::valueType::Object)
		{
			this->Get<basyx::object::object_list_t&>().emplace_back(obj);
			return true;
		}

		if (this->content->value_type() == value_type)
		{
			switch (value_type)
			{
			case basyx::type::valueType::Bool:
				return this->insert(obj.Get<bool&>());
				break;
			case basyx::type::valueType::Int:
				return this->insert(obj.Get<int&>());
				break;
			case basyx::type::valueType::Float:
				return this->insert(obj.Get<float&>());
				break;
			case basyx::type::valueType::String:
				return this->insert(obj.Get<std::string&>());
				break;
			};
		}
		break;
	};

	return false;
};

bool basyx::object::hasProperty(const std::string & propertyName)
{
	if (!this->content || this->GetObjectType() != basyx::type::objectType::Map)
		return false;

	auto & map = this->Get<basyx::object::object_map_t&>();

	if (map.find(propertyName) != map.end()) {
		return true;
	}

	return false;
};

basyx::object basyx::object::getProperty(const std::string & propertyName)
{
	if (!this->content || this->GetObjectType() != basyx::type::objectType::Map)
		return basyx::object::make_null();

	auto & map = this->Get<basyx::object::object_map_t&>();

	if (map.find(propertyName) != map.end()) {
		return map[propertyName];
	}

	return basyx::object::make_null();
};

bool basyx::object::removeProperty(const std::string & propertyName)
{
	if (!this->content)
		return false;

	if (this->InstanceOf<object::object_map_t>())
	{
		auto & map = this->Get<object::object_map_t&>();
		auto found = map.find(propertyName);
		if (found != map.end()) {
			map.erase(found);
			return true;
		}
	};

	return false;
};

bool basyx::object::operator==(const basyx::object& rhs) const
{
	if (rhs.IsNull())
		return false;

	return this->content->compare(rhs.content.get());
}

std::size_t basyx::object::size()
{
	if (!this->content)
		return 0;

	auto valueType = this->GetValueType();

	// Check if contained object is list or set
	switch (content->object_type())
	{
	case basyx::type::objectType::List:
		switch (valueType)
		{
		case basyx::type::valueType::Bool:
			return this->Get<object::list_t<bool>&>().size();
			break;
		case basyx::type::valueType::Int:
			return this->Get<object::list_t<int>&>().size();
			break;
		case basyx::type::valueType::Float:
			return this->Get<object::list_t<float>&>().size();
			break;
		case basyx::type::valueType::String:
			return this->Get<object::list_t<std::string>&>().size();
			break;
		case basyx::type::valueType::Object:
			return this->Get<object::object_list_t&>().size();
			break;
		};
		break;
	case basyx::type::objectType::Map:
		return this->Get<object::object_map_t&>().size();
		break;
	};

	return 1;
};

basyx::object basyx::object::make_null()
{
	return basyx::object{ nullptr };
};

basyx::object basyx::object::make_error(basyx::object::error error_code)
{
	basyx::object obj{ nullptr };
	obj.err = error_code;
	return obj;
};

basyx::object basyx::object::invoke()
{
	if (this->IsNull() || this->GetObjectType() != basyx::type::objectType::Function)
		return basyx::object::make_null();

	return this->Get<basyx::detail::functionWrapper>().invoke();
};

basyx::object basyx::object::invoke(basyx::object& obj)
{
	if (this->IsNull() || this->GetObjectType() != basyx::type::objectType::Function)
		return basyx::object::make_null();

	auto param = obj;

	auto object_type = obj.GetObjectType();
	auto value_type = obj.GetValueType();

	// Transpose to object list if necessary
	if (object_type == basyx::type::objectType::List)
	{
		if (value_type == basyx::type::valueType::Bool)
		{
			auto & list = obj.Get<basyx::object::list_t<bool>&>();
			param = basyx::object::make_list<basyx::object>();
			for (bool b : list) {
				param.insert(basyx::object{ b });
			};
		}
		else if (value_type == basyx::type::valueType::Int)
		{
			auto & list = obj.Get<basyx::object::list_t<int>&>();
			param = basyx::object::make_list<basyx::object>(list.begin(), list.end());
		}
		else if (value_type == basyx::type::valueType::Float)
		{
			auto & list = obj.Get<basyx::object::list_t<double>&>();
			param = basyx::object::make_list<basyx::object>(list.begin(), list.end());
		}
		else if (value_type == basyx::type::valueType::String)
		{
			auto & list = obj.Get<basyx::object::list_t<std::string>&>();
			param = basyx::object::make_list<basyx::object>();
			for (auto & str : list)	{
				param.insert(basyx::object::make_object_ref(&str));
			};
		}
	};

	return this->Get<basyx::detail::functionWrapper>().invoke(param);
};

void basyx::to_json(nlohmann::json& json, const basyx::object& object)
{
	object.content->to_json(json);
};



constexpr basyx::type::objectType basyx::type::basyx_type<std::nullptr_t>::object_type;
constexpr basyx::type::valueType basyx::type::basyx_type<std::nullptr_t>::value_type;

constexpr basyx::type::objectType basyx::type::basyx_type<bool>::object_type;
constexpr basyx::type::valueType basyx::type::basyx_type<bool>::value_type;

constexpr basyx::type::objectType basyx::type::basyx_type<std::string>::object_type;
constexpr basyx::type::valueType basyx::type::basyx_type<std::string>::value_type;

constexpr basyx::type::objectType basyx::type::basyx_type<basyx::detail::functionWrapper>::object_type;
constexpr basyx::type::valueType basyx::type::basyx_type<basyx::detail::functionWrapper>::value_type;
