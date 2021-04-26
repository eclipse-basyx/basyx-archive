#ifndef API_RESPONSE_H
#define API_RESPONSE_H

#include <string>
#include <BaSyx/shared/object.h>

namespace basyx
{
	namespace opcua
	{
		namespace aas
		{
			enum ApiResponse
			{
				OK = 0,
				NOT_OK_MALFORMED_REQ,
				NOT_OK_MALFORMED_BODY,
				NOT_OK_INTERNAL_ERROR,
				NOT_OK_NOT_FOUND,
				NOT_OK_EMPTY_DATA,
				NOT_OK_NODE_EXISTS,
				NOT_IMPLEMENTED,
				NOT_SUPPORTED,
				UNKNOWN
			};

			struct ApiResponse_
			{
				static std::string toString(ApiResponse t_value)
				{
					if (t_value == ApiResponse::OK)
					{
						return "OK";
					}
					else if(t_value == ApiResponse::NOT_OK_MALFORMED_REQ)
					{
						return "Maformed request";
					}
					else if (t_value == ApiResponse::NOT_OK_MALFORMED_BODY)
					{
						return "Maformed request";
					}
					else if (t_value == ApiResponse::NOT_OK_INTERNAL_ERROR)
					{
						return "Internal error occured";
					}
					else if (t_value == ApiResponse::NOT_OK_NOT_FOUND)
					{
						return "Element not found";
					}
					else if (t_value == ApiResponse::NOT_OK_EMPTY_DATA)
					{
						return "Response data is empty";
					}
					else if (t_value == ApiResponse::NOT_OK_NODE_EXISTS)
					{
						return "OPC UA node already exists for the object";
					}
					else if (t_value == ApiResponse::NOT_IMPLEMENTED)
					{
						return "Not implemented";
					}
					else if (t_value == ApiResponse::NOT_SUPPORTED)
					{
						return "Not supported";
					}
					else
					{
						return "Unknown";
					}
				}

				static basyx::object ApiResponseToError(ApiResponse t_value, const std::string& t_message )
				{
					if (t_value == ApiResponse::OK)
					{
						return basyx::object::make_error(basyx::object::error::None);
					}
					else if (t_value == ApiResponse::NOT_OK_MALFORMED_REQ)
					{
						return basyx::object::make_error(basyx::object::error::MalformedRequest);
					}
					else if (t_value == ApiResponse::NOT_OK_MALFORMED_REQ)
					{
						return basyx::object::make_error(basyx::object::error::MalformedRequest, t_message);
					}
					else if (t_value == ApiResponse::NOT_OK_MALFORMED_BODY)
					{
						return basyx::object::make_error(basyx::object::error::MalformedRequest, t_message);
					}
					else if (t_value == ApiResponse::NOT_OK_NOT_FOUND)
					{
						return basyx::object::make_error(basyx::object::error::PropertyNotFound, t_message);
					}
					else 
					{
						return basyx::object::make_error(basyx::object::error::ProviderException, t_message);
					}
				}
			};
		}
	}
}
#endif