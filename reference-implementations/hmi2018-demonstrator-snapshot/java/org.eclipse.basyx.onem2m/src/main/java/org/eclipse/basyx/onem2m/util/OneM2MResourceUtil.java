package org.eclipse.basyx.onem2m.util;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.basyx.onem2m.xml.protocols.Acp;
import org.eclipse.basyx.onem2m.xml.protocols.Ae;
import org.eclipse.basyx.onem2m.xml.protocols.Cb;
import org.eclipse.basyx.onem2m.xml.protocols.Cin;
import org.eclipse.basyx.onem2m.xml.protocols.Cnt;
import org.eclipse.basyx.onem2m.xml.protocols.Notification;
import org.eclipse.basyx.onem2m.xml.protocols.Resource;
import org.eclipse.basyx.onem2m.xml.protocols.Sub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class OneM2MResourceUtil implements JsonDeserializer<Object> {
	
	private static Map<String, Pair<Integer, Class>> resourceMap = new HashMap<String, Pair<Integer, Class>>() {{
	    put("m2m:acp", Pair.of(1, Acp.class));
	    put("m2m:ae", Pair.of(2, Ae.class));
	    put("m2m:cnt", Pair.of(3, Cnt.class));
	    put("m2m:cin", Pair.of(4, Cin.class));
	    put("m2m:cb", Pair.of(5, Cb.class));
	    put("m2m:sub", Pair.of(23, Sub.class));
	    put("m2m:sgn", Pair.of(null, Notification.class));
	}};
	
    public static boolean isResource(String xsdTypeName) {
        return OneM2MResourceUtil.resourceMap.containsKey(xsdTypeName)
        		&& (getTyFromXsdTypeName(xsdTypeName) != null);
    }

    public static boolean isOtherPrimitiveContent(String xsdTypeName) {
        switch(xsdTypeName) {
            case "m2m:uril":
                return true;                
        }
        return false;
    }

    
    public static Object createOtherPrimitiveContent(String xsdTypeName, JsonElement any)  {        
        switch (xsdTypeName) {
            case "m2m:uril":
            	Type listType = new TypeToken<ArrayList<String>>(){}.getType();
                return (ArrayList<String>) new Gson().fromJson(any, listType);
            default:
                throw new IllegalArgumentException("Unknown xsd type name.");
        }
    } 
       
    

    public static Integer getTyFromXsdTypeName(String xsdTypeName) {
        return OneM2MResourceUtil.resourceMap.get(xsdTypeName).getLeft();
    }

    public static String getXsdTypeNameFromTy(Integer ty) {    	
    	for (String key : OneM2MResourceUtil.resourceMap.keySet()) {
    		Pair<Integer, Class> pair = OneM2MResourceUtil.resourceMap.get(key);
    		if (pair.getLeft().equals(ty)) {
    			return key;
    		}
    	}   
    	return null;
    }

    public static Integer getTyFromResource(Resource res) {
    	for (String key : OneM2MResourceUtil.resourceMap.keySet()) {
    		Pair<Integer, Class> pair = OneM2MResourceUtil.resourceMap.get(key);
    		Class c = pair.getRight();
    		if (c.isInstance(res)) {
    			return pair.getLeft();
    		}
    	}    	
    	return null;
    }

    public static String getXsdTypeNameFromResource(Resource res) {
    	for (String key : OneM2MResourceUtil.resourceMap.keySet()) {
    		Pair<Integer, Class> pair = OneM2MResourceUtil.resourceMap.get(key);
    		Class c = pair.getRight();
    		if (c.isInstance(res)) {
    			return key;
    		}
    	}    	
    	return null;    	
    }

    public static Class getClazzFromXsdTypeName(String xsdTypeName) {
        return OneM2MResourceUtil.resourceMap.get(xsdTypeName).getRight();
    }

    public static Class getClazzFromTy(Integer ty) {
    	for (String key : OneM2MResourceUtil.resourceMap.keySet()) {
    		Pair<Integer, Class> pair = OneM2MResourceUtil.resourceMap.get(key);
    		if (pair.getLeft().equals(ty)) {
    			return pair.getRight();
    		}
    	}   
    	return null;
    }
        
    public static Resource createResource(String xsdTypeName) throws InstantiationException, IllegalAccessException {
        Class clazz = OneM2MResourceUtil.getClazzFromXsdTypeName(xsdTypeName);
        Object o = clazz.newInstance();
        Resource r = (Resource) o;
        r.setTy(BigInteger.valueOf(OneM2MResourceUtil.getTyFromXsdTypeName(xsdTypeName)));        
        return r;
    }
    
    private static Resource findResourceByRi(List<? extends Resource> list, String ri) {
    	for (Resource r : list) {
    		if (ri.equals(r.getRi())) {
    			return r;
    		}
    	}
    	return null;
    }

    public static void addChildToParent(Resource parent, Resource child) {
    	
        if (parent instanceof Cb) {
        	if (child instanceof Ae) {
            	List<Ae> childs = ((Cb) parent).getAe();
        		Resource r = OneM2MResourceUtil.findResourceByRi(childs, child.getRi());
        		if (r != null) {
        			childs.set(childs.indexOf(r), (Ae) child);
        		} else {
        			childs.add((Ae) child);
        		}
        	} else if (child instanceof Cnt) {
            	List<Cnt> childs = ((Cb) parent).getCnt();
        		Resource r = OneM2MResourceUtil.findResourceByRi(childs, child.getRi());
        		if (r != null) {
        			childs.set(childs.indexOf(r), (Cnt) child);
        		} else {
        			childs.add((Cnt) child);
        		}
        	} else if (child instanceof Acp) {
            	List<Acp> childs = ((Cb) parent).getAcp();
        		Resource r = OneM2MResourceUtil.findResourceByRi(childs, child.getRi());
        		if (r != null) {
        			childs.set(childs.indexOf(r), (Acp) child);
        		} else {
        			childs.add((Acp) child);
        		}
        	} else if (child instanceof Sub) {
            	List<Sub> childs = ((Cb) parent).getSub();
        		Resource r = OneM2MResourceUtil.findResourceByRi(childs, child.getRi());
        		if (r != null) {
        			childs.set(childs.indexOf(r), (Sub) child);
        		} else {
        			childs.add((Sub) child);
        		}
        	}  else {
        		throw new NotImplementedException("Type pair not found... probably not implemented yet!");
        	}
        	    
        } else if (parent instanceof Ae) {
        	if (child instanceof Cnt) {
            	List<Cnt> childs = ((Ae) parent).getCnt();
        		Resource r = OneM2MResourceUtil.findResourceByRi(childs, child.getRi());
        		if (r != null) {
        			childs.set(childs.indexOf(r), (Cnt) child);
        		} else {
        			childs.add((Cnt) child);
        		}
        	} else if (child instanceof Acp) {
            	List<Acp> childs = ((Ae) parent).getAcp();
        		Resource r = OneM2MResourceUtil.findResourceByRi(childs, child.getRi());
        		if (r != null) {
        			childs.set(childs.indexOf(r), (Acp) child);
        		} else {
        			childs.add((Acp) child);
        		}
        	} else if (child instanceof Sub) {
            	List<Sub> childs = ((Ae) parent).getSub();
        		Resource r = OneM2MResourceUtil.findResourceByRi(childs, child.getRi());
        		if (r != null) {
        			childs.set(childs.indexOf(r), (Sub) child);
        		} else {
        			childs.add((Sub) child);
        		}
        	}  else {
        		throw new NotImplementedException("Type pair not found... probably not implemented yet!");
        	}
        } else if (parent instanceof Cnt) {
        	if (child instanceof Cin) {
            	List<Cin> childs = ((Cnt) parent).getCin();
        		Resource r = OneM2MResourceUtil.findResourceByRi(childs, child.getRi());
        		if (r != null) {
        			childs.set(childs.indexOf(r), (Cin) child);
        		} else {
        			childs.add((Cin) child);
        		}
        	} else if (child instanceof Cnt) {
            	List<Cnt> childs = ((Cnt) parent).getCnt();
        		Resource r = OneM2MResourceUtil.findResourceByRi(childs, child.getRi());
        		if (r != null) {
        			childs.set(childs.indexOf(r), (Cnt) child);
        		} else {
        			childs.add((Cnt) child);
        		}
        	} else if (child instanceof Sub) {
            	List<Sub> childs = ((Cnt) parent).getSub();
        		Resource r = OneM2MResourceUtil.findResourceByRi(childs, child.getRi());
        		if (r != null) {
        			childs.set(childs.indexOf(r), (Sub) child);
        		} else {
        			childs.add((Sub) child);
        		}
        	}  else {
        		throw new NotImplementedException("Type pair not found... probably not implemented yet!");
        	}
        } else {    	    	
        	throw new NotImplementedException("Type pair not found... probably not implemented yet!");
        }
    }	

    public static List<Resource> getChildren(Resource parent) {
    	List<Resource> childs = new ArrayList<Resource>();
        if (parent instanceof Cb) {
        	Cb cb = (Cb) parent;
        	childs.addAll(cb.getCsrA());
        	childs.addAll(cb.getNod());
        	childs.addAll(cb.getAe());
        	childs.addAll(cb.getCnt());
        	childs.addAll(cb.getGrp());
        	childs.addAll(cb.getAcp());
        	childs.addAll(cb.getSub());
        	childs.addAll(cb.getMgc());
        	childs.addAll(cb.getLcp());
        	childs.addAll(cb.getStcg());
        	childs.addAll(cb.getReq());
        	childs.addAll(cb.getDlv());
        	childs.addAll(cb.getSch());
        	childs.addAll(cb.getMssp());
        	childs.addAll(cb.getAsar());
        	childs.addAll(cb.getRol());
        	childs.addAll(cb.getTk());
        	// childs.addAll(cb.getSgFlexContainerResource()); TODO not supported yet
            return childs;
        } else if (parent instanceof Ae) {
        	Ae ae = (Ae) parent;        	
        	childs.addAll(ae.getCnt());
        	childs.addAll(ae.getGrp());
        	childs.addAll(ae.getAcp());
        	childs.addAll(ae.getSub());
        	childs.addAll(ae.getPch());
        	childs.addAll(ae.getSch());
        	childs.addAll(ae.getSmd());
        	childs.addAll(ae.getTs());
        	childs.addAll(ae.getTrpt());
        	// childs.addAll(cb.getSgFlexContainerResource()); TODO not supported yet
        	return childs;
        } else if (parent instanceof Cnt) {
        	Cnt cnt = (Cnt) parent;        	
        	childs.addAll(cnt.getCin());
        	childs.addAll(cnt.getCnt());
        	childs.addAll(cnt.getSub());
        	childs.addAll(cnt.getSmd());
        	// childs.addAll(cb.getSgFlexContainerResource()); TODO not supported yet
        	return childs;
        } else {
        	return null; // not found
        }
    }	
    
    public static Object jsonToObject(String json) {
    	
    	JsonObject jsonO = new JsonParser().parse(json).getAsJsonObject();
		Set<Entry<String, JsonElement>> entrySet = jsonO.entrySet();
		if (entrySet.size() != 1) {
			return null;
		}
		Entry<String, JsonElement> entry = entrySet.iterator().next();    	
    	Class<?> c = getClazzFromXsdTypeName(entry.getKey());
    	if (c == null) {
	    	try{
	        	String className = entry.getKey().substring("m2m:".length());
	        	String fullClassName = "org.eclipse.basyx.onem2m.xml.protocols." + className.substring(0, 1).toUpperCase() + className.substring(1);
	    		c = Class.forName(fullClassName);	
	    	} catch (ClassNotFoundException e) {
	    		return null;
	    	}
    	}    	
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.registerTypeAdapter(Notification.Nev.class, new OneM2MResourceUtil());
    	
    	JsonElement cleanedJo = replaceKeys(jsonO.get(entry.getKey()).getAsJsonObject());
    	Object obj = gsonBuilder.create().fromJson(cleanedJo, c);
    	return obj;
    }

	private static JsonElement replaceKeys(JsonElement element) {
		
		if (element.isJsonObject()) {					
			JsonObject jobj = element.getAsJsonObject(); 
			JsonObject newJobj = new JsonObject();
			for (Entry<String, JsonElement> childEntry : jobj.entrySet()) {
				JsonElement value = null;
				if (childEntry.getValue().isJsonObject()) {
					value = replaceKeys(childEntry.getValue());
				} else if (childEntry.getValue().isJsonArray()) {
					value = replaceKeys(childEntry.getValue()); 
				} else {
					value = childEntry.getValue();
				}
				String key = childEntry.getKey();
				if (key.startsWith("m2m:") && !isResource(key)) {
					key = key.substring("m2m:".length());
				}
				newJobj.add(key, value);
	    	}
			return newJobj;
		} else if (element.isJsonArray()) {
			JsonArray jarr = element.getAsJsonArray();
			JsonArray newJarr = new JsonArray();
			for (JsonElement elem : jarr) {
				newJarr.add(replaceKeys(elem));
			}
			return newJarr;
		} else {
			return element;
		}		
	}

	@Override
	public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		if (typeOfT.equals(Notification.Nev.class)) {
			Notification.Nev nev = new Gson().fromJson(json, Notification.Nev.class);
			JsonObject jo = json.getAsJsonObject();
			if (jo.has("rep")) {
				JsonObject repContentJO = jo.get("rep").getAsJsonObject();
				Object repContentObj = jsonToObject(repContentJO.toString());
				nev.setRep(repContentObj);				
			}						
			return nev;
		}
		
		return null;
	}
	
	public static String extractLabel(List<String> labels, String key) {
		for (String label : labels) {
			if (label.startsWith(key + ":")) {
				return label.substring((key + ":").length());
			}
		}
		return null;
	}        

	public static boolean hasLabel(List<String> labels, String key) {
		for (String label : labels) {
			if (label.equals(key)) {
				return true;
			}
		}
		return false;
	}        

}
