package www.supermap.knowledge.beans;

/**
 * 知识图谱前缀
 * @author SunYasong
 *
 */
public final class Prefixes {
	
	/**
	 * 1. 网格model的实体前缀或关系
	 */
	//网格实体
	public static final String GRID_PREFIX = "http://Grid#";
	
	//网格包含地理类型关系
	public static final String HAVE_TYPE = "http://haveType";
	
	//地理类型实体
	public static final String GEO_TYPE_PREFIX = "http://GeoType#";
	
	//地理类型下附属地理实体关系
	public static final String HAVE_GEO = "http://haveGeo";
	
	//地理实体
	public static final String GEO_ENTITY_PREFIX = "http://GeoEntity#";
	
	
	/**
	 * 2. 数据源、数据集model的实体前缀及关系
	 */
	//数据源实体
	public static final String DATA_SOURCE_PREFIX = "http://DataSource#";
	
	//数据源名称关系
	public static final String DATA_SOURCE_NAME = "http://dataSourceName";
	
	//数据集实体
	public static final String DATA_SET_PREFIX = "http://DataSet#";
	
	//数据集名称关系
	public static final String DATA_SET_NAME = "http://dataSetName";
	
	// 数据源下属数据集关系
	public static final String HAVE_DATA_SET = "http://haveDataSet";
	
	
}
