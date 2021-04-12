#ifndef NODE_DESCRIPTION_H
#define NODE_DESCRIPTION_H

#include <string>

class NodeDescription
{
public:
	NodeDescription():m_description(std::make_tuple("en-US", std::string())) {}

    ~NodeDescription() = default;

    NodeDescription(const std::string& t_langCode,
                    const std::string& t_text):
                    m_description{std::make_tuple(t_langCode, t_text)}{}

    const std::string& getLangCode() const{
        return std::get<0>(m_description);
    }

    const std::string& getText() const {
        return std::get<1>(m_description);
    }

    std::string toString(){
        return "("+ getLangCode()+")" + getText();
    }
    
private:
    std::tuple<std::string, std::string> m_description;
};

#endif