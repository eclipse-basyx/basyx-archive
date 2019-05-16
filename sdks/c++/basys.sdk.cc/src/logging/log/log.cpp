#include "log.h"

#include <string>
#include <chrono>
#include <ctime>

namespace basyx
{
	log::Level log::logLevel = log::Level::Info;

	const char * log::printLevel(log::Level level)
	{
		switch (level)
		{
		case Level::Debug:
			return "[debug] ";
		case Level::Trace:
			return "[trace] ";
		case Level::Info:
			return "[info] ";
		case Level::Warn:
			return "[warn] ";
		case Level::Error:
			return "[error] ";
		case Level::Critical:
			return "[CRITICAL] ";
		};

		return "[] ";
	}

	std::string basyx::log::timestamp()
	{
		std::string formatted_time = "[";

		auto current_time = std::chrono::system_clock::now();

		std::time_t t = std::chrono::system_clock::to_time_t(current_time);
		formatted_time += std::ctime(&t);

		//Remove trailing newline
		formatted_time.erase(formatted_time.end() - 1);
		formatted_time += "] ";

		return formatted_time;
	};


	std::string basyx::log::buildMessage(const std::string & format, Level level)
	{
		std::string message = "";
		message += timestamp();
		message += printLevel(level);
		message += "[" + std::to_string(basyx::thread::currentThreadId()) + "] ";
		message += sourceName;
		message += format + "\r\n";
		return message;
	};
};