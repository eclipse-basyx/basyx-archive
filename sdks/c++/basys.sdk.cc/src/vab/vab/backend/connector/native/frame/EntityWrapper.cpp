#include <BaSyx/vab/backend/connector/native/frame/EntityWrapper.h>

using basyx::vab::EntityWrapper;


namespace {
	std::string prepareErrorCode(basyx::object::error errorCode)
	{
		std::string error;
		switch (errorCode)
		{
		case basyx::object::error::PropertyNotFound:
			error = "ResourceNotFoundException";
			break;
		case basyx::object::error::ObjectAlreadyExists:
			error = "ResourceAlreadyExistsException";
			break;
		case basyx::object::error::MalformedRequest:
			error = "MalformedRequestException";
			break;
		default:
			error = "ProviderException";
			break;
		};
		return error;
	};

	std::string prepareErrorMessage(basyx::object::error errorCode, const std::string & message)
	{
		return prepareErrorCode(errorCode) + ": " + message;
	};
}

basyx::object build_exception(const std::string & type, const std::string & message)
{
	basyx::object::error error = basyx::object::error::ProviderException;

	if (type == "ResourceNotFoundException")
	{
		error = basyx::object::error::PropertyNotFound;
	}
	else if (type == "ResourceAlreadyExistsException")
	{
		error = basyx::object::error::ObjectAlreadyExists;
	}
	else if (type == "MalformedRequestException")
	{
		error = basyx::object::error::MalformedRequest;
	}
	else if (type == "ProviderException")
	{
		error = basyx::object::error::ProviderException;
	};

	return basyx::object::make_error(error, message);
};

basyx::json_t EntityWrapper::build_from_error(basyx::object::error error, const std::string & message)
{
	json_t msg;
	msg["messageType"] = 6;
	msg["text"] = prepareErrorMessage(error, message);
	msg["code"] = nullptr;

	basyx::json_t j_obj;
	j_obj["success"] = false;
	j_obj["isException"] = true;
	j_obj["messages"] = json_t::array({ msg });
	j_obj["entityType"] = prepareErrorCode(error);
	return j_obj;
};

basyx::json_t EntityWrapper::build_from_object(const basyx::object & object)
{
	basyx::json_t j_obj;
	if (object.IsError())
	{
		return build_from_error(object.getError(), object.getErrorMessage());
	}
	else
	{
		j_obj["success"] = true;
//		j_obj["isException"] = false;
		j_obj["entityType"] = "entity";
		j_obj["entity"] = basyx::serialization::json::serialize(object);
	}
	return j_obj;
};


basyx::object EntityWrapper::from_json(const basyx::json_t & json)
{
	bool success = json["success"];
	// everyhing okay, deserialize entity
	if (success)
	{
		if (json.contains("entity"))
			return basyx::serialization::json::deserialize(json["entity"]);
		else
			return basyx::object::make_null();
	}
	// something went wrong, check for exception
	else if (json.contains("isException") && json.contains("messages"))
	{
		return build_exception(json["entityType"], json["messages"][0]["text"]);
	}
	// error and no exception; create one
	else
	{
		return basyx::object::make_error(basyx::object::error::MalformedRequest);
	};
};