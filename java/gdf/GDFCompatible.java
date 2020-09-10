package gdf;

/**
 * Using this interface enables the ability to retrieve Enum values from
 * a file based on its id.
 */
public interface GDFCompatible <T extends Enum<T>> {
	public String getGDFID();
	
	public default T getGDFDefaultEnum() {
		return null;
	}
}
