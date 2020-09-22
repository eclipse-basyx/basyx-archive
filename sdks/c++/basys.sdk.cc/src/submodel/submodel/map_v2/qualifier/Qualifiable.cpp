#include <BaSyx/submodel/map_v2/qualifier/Qualifiable.h>
#include <BaSyx/submodel/map_v2/constraint/Formula.h>
#include <BaSyx/submodel/map_v2/constraint/Qualifier.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

constexpr char Qualifiable::Path::Qualifier[];

Qualifiable::Qualifiable(const std::vector<simple::Formula> & formulas, const std::vector<simple::Qualifier> & qualifiers)
{
	this->map.insertKey(Path::Qualifier, basyx::object::make_object_list());
};

void Qualifiable::addFormula(const api::IFormula & formula)
{
	map::Formula f{ formula };

	auto & objectList = this->map.getProperty(Path::Qualifier).Get<basyx::object::object_list_t&>();

	objectList.emplace_back(f.getMap());
}

void Qualifiable::addQualifier(const api::IQualifier & qualifier)
{
	map::Qualifier q{ qualifier };

	auto & objectList = this->map.getProperty(Path::Qualifier).Get<basyx::object::object_list_t&>();

	objectList.emplace_back(q.getMap());
}

std::vector<simple::Formula> Qualifiable::getFormulas() const
{
	std::vector<simple::Formula> formulas;

	auto & objectList = this->map.getProperty(Path::Qualifier).Get<basyx::object::object_list_t&>();
	for (auto & object : objectList)
	{
		if(ModelType<ModelTypes::Constraint>(object).GetModelType() == ModelTypes::Formula)
		{
			map::Formula formula(object);
			formulas.emplace_back(formula);
		};
	};

	return formulas;
}

std::vector<simple::Qualifier> Qualifiable::getQualifiers() const
{
	std::vector<simple::Qualifier> qualifiers;

	auto & objectList = this->map.getProperty(Path::Qualifier).Get<basyx::object::object_list_t&>();
	for (auto & object : objectList)
	{
		if (ModelType<ModelTypes::Constraint>(object).GetModelType() == ModelTypes::Qualifier)
		{
			map::Qualifier qualifier(object);
			qualifiers.emplace_back(qualifier);
		};
	};

	return qualifiers;
}
