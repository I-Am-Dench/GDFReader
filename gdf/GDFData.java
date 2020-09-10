package gdf;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GDFData {
	private Map<String, String> rawData = new HashMap<>();
	
	public <E extends Enum<E> & GDFCompatible<E>> E getCorrectEnum(String id, E dflt, Class<E> clazz) {
		for (E t : clazz.getEnumConstants())
			if (id.equals(t.getGDFID()))
				return t;
		return dflt;
	}
	
	public void add(String key, String value) {
		this.rawData.put(key, value);
	}
	
	public boolean isEmpty() {
		return this.rawData.isEmpty();
	}
	
	public boolean contains(String key) {
		return this.rawData.containsKey(key);
	}
	
	public Set<String> getKeys() {
		return this.rawData.keySet();
	}
	
	public String getString(String key, String dflt) {
		return (this.contains(key)) ? rawData.get(key) : dflt;
	}
	
	public String getString(String key) {
		return this.getString(key, "");
	}
	
	public int getInt(String key, int dflt) {
		return (this.contains(key)) ? Integer.valueOf(rawData.get(key)) : dflt;
	}
	
	public int getInt(String key) {
		return this.getInt(key, 0);
	}
	
	public float getFloat(String key, float dflt) {
		return (this.contains(key)) ? Float.valueOf(rawData.get(key)) : dflt;
	}
	
	public float getFloat(String key) {
		return this.getFloat(key, 0f);
	}
	
	public double getDouble(String key, double dflt) {
		return (this.contains(key)) ? Double.valueOf(rawData.get(key)) : dflt;
	}
	
	public double getDouble(String key) {
		return this.getDouble(key, 0.0);
	}
	
	public boolean getBool(String key, boolean dflt) {
		return (this.contains(key)) ? Boolean.valueOf(rawData.get(key)) : dflt;
	}
	
	public boolean getBoolean(String key) {
		return this.getBool(key, false);
	}
	
	public <E extends Enum<E> & GDFCompatible<E>> E getEnum(String key, E dflt, Class<E> clazz) {
		if (!this.contains(key))
			return dflt;
		String id = rawData.get(key);
		return this.getCorrectEnum(id, dflt, clazz);
	}
	
	public <E extends Enum<E> & GDFCompatible<E>> E getEnum(String key, Class<E> clazz) {
		E defaultEnum = clazz.getEnumConstants()[0].getGDFDefaultEnum();
		return this.getEnum(key, defaultEnum, clazz);
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Enum<E> & GDFCompatible<E>> E[] getEnumArray(String key, E dflt, Class<E> clazz) {
		String[] strs = this.getString(key).split(",");
		E[] arr = (E[])Array.newInstance(clazz, strs.length);
		for (int i=0; i < arr.length; i++)
			arr[i] = this.getCorrectEnum(strs[i], dflt, clazz);
		return arr;
	}
	
	public <E extends Enum<E> & GDFCompatible<E>> E[] getEnumArray(String key, Class<E> clazz) {
		E defaultEnum = (E)clazz.getEnumConstants()[0].getGDFDefaultEnum();
		return this.getEnumArray(key, defaultEnum, clazz);
	}
	
	@Override
	public String toString() {
		return this.rawData.toString();
	}
}



















