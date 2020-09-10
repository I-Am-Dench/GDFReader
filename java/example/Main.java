package example;

import gdf.GDFData;
import gdf.GDFReader;

public class Main {
	public static void main(String[] args) {
		GDFReader.setPath("src/example/");
		GDFData data = GDFReader.getData("myFile", "data");
		System.out.println(data.getEnum("ele", Element.class));
	}
}
