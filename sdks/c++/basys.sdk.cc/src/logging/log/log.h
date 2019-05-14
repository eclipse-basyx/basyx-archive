#ifndef LOGGING_LOG_H_
#define LOGGING_LOG_H_

#include <Thread.h>

namespace basyx {
	class log {
	public:
		enum class Level
		{
			Debug = 0,
			Trace = 1,
			Info = 2,
			Warn = 3,
			Error = 4,
			Critical = 5
		};
	public:
		explicit log(const std::string& sourceName)
			: sourceName{ "[" + sourceName + "] " }
		{
		}

		explicit log()
			: sourceName{ "" } {};

	private:
		const char * printLevel(Level level);
	private:
		static Level logLevel;
	public:
		static void setLogLevel(Level level)
		{
			logLevel = level;
		};
	private:
		std::string sourceName;

		std::string timestamp();
		std::string buildMessage(const std::string & format, Level level);

		template<typename... Args>
		inline void log_message(Level level, const std::string & msg, Args && ... args)
		{
			if (level >= logLevel)
				printf(buildMessage(msg, level).c_str(), args...);
		};

	public:
		template <typename... Args>
		inline void trace(const std::string& msg, Args&&... args)
		{
			log_message(Level::Trace, msg, args...);
		}

		template <typename... Args>
		inline void debug(const std::string& msg, Args&&... args)
		{
			log_message(Level::Debug, msg, args...);
		}

		template <typename... Args>
		inline void info(const std::string& msg, Args&&... args)
		{
			log_message(Level::Info, msg, args...);
		}

		template <typename... Args>
		inline void warn(const std::string& msg, Args&&... args)
		{
			log_message(Level::Warn, msg, args...);
		}

		template <typename... Args>
		inline void error(const std::string& msg, Args&&... args)
		{
			log_message(Level::Error, msg, args...);
		}

		template <typename... Args>
		inline void crit(const std::string& msg, Args&&... args)
		{
			log_message(Level::Critical, msg, args...);
		}
	};
};

#endif