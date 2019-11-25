#include <basyx/object.h>

#include <basyx/object/obj_function.h>

basyx::object::object()
	: content{ nullptr } {};

basyx::object::object(const char * c)
	: object{ std::string(c) }
{
};

bool basyx::object::insert(basyx::object obj)
{
	if (!this->content)
		return false;

	auto object_type = obj.content->object_type();
	auto value_type = obj.content->value_type();

	if (object_type != basyx::type::objectType::Primitive)
		return false;

	switch (this->content->object_type())
	{
	case basyx::type::objectType::List:
	case basyx::type::objectType::Set:
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

basyx::object basyx::object::make_null()
{
	return basyx::object{ nullptr };
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

	return this->Get<basyx::detail::functionWrapper>().invoke(obj);
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