#include "VABPath.h"

#include <algorithm>

const std::string VABPath::emptyElement{ "" };
const char VABPath::delimiter{'/'};

VABPath::VABPath(const std::string & path)
{
	auto start = path.begin();
	auto end = path.begin();
	while (start != path.end() && end != path.end())
	{
		end = std::find(start, path.end(), delimiter);
		if (start != end)
			elements.emplace_back(start, end);

		if (elements.size() && elements.back().compare("operations") == 0)
			isOperation = true;

		if(end != path.end())
			start = ++end;
	}
}

const std::string & VABPath::getLastElement() const {
	if (elements.size())
		return elements.back();

	return VABPath::emptyElement;
}

const std::vector<std::string>& VABPath::getElements() const
{
	return elements;
}

std::string VABPath::toString() const {
	std::string str;

	for (const auto & element : elements)
		str += delimiter + element;

	return str;
};
