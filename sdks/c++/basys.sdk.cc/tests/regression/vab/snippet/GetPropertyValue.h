




class GetPropertyValue
{
public:
	template<typename Model>
	static void test(Model & model) 
	{
		model.readElementValue("property1");
	}
};




