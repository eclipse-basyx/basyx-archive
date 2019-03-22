#ifndef BACKENDS_PROVIDER_VAB_VABPATH_H_
#define BACKENDS_PROVIDER_VAB_VABPATH_H_

#include <string>
#include <vector>

class VABPath
{
private:
	static const char delimiter;
	static const std::string emptyElement;
	std::vector<std::string> elements;

	bool isValid;
	bool isOperation;
public:
	explicit VABPath(const std::string & path);
	const std::string & getLastElement() const;
	const std::vector<std::string> & getElements() const;

	std::string toString() const;
};



#endif
