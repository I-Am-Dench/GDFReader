package example;

import gdf.GDFCompatible;

public enum Element implements GDFCompatible<Element>{
	WATER("water"),
	EARTH("earth"),
	FIRE("fire"),
	AIR("air");
	
	private String id;
	
	Element(String id) {
		this.id = id;
	}
	
	public String getGDFID() {
		return this.id;
	}
	
	@Override
	public Element getGDFDefaultEnum() {
		return WATER;
	}
}
