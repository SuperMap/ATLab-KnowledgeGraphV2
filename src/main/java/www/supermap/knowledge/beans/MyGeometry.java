package www.supermap.knowledge.beans;

import com.supermap.data.Geometry;
/**
 * 用来定义从数据源中读出的实体类
 * @author SunYasong
 *
 */
public class MyGeometry {

	private Geometry geometry;
	private String databaseName;
	private int primaryIndex;
	private String dataSourceName;
	private String datasourcePath;

	// private String time;

	public MyGeometry() {
		// TODO Auto-generated constructor stub
	}

	public MyGeometry(Geometry geometry, String databaseName, int primaryIndex, String dataSourceName,
			String datasourcePath) {
		super();
		this.geometry = geometry;
		this.databaseName = databaseName;
		this.primaryIndex = primaryIndex;
		this.dataSourceName = dataSourceName;
		this.datasourcePath = datasourcePath;
	}
	
	

	public MyGeometry(Geometry geometry, int primaryIndex) {
		super();
		this.geometry = geometry;
		this.primaryIndex = primaryIndex;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public int getPrimaryIndex() {
		return primaryIndex;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public String getDatasourcePath() {
		return datasourcePath;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setPrimaryIndex(int primaryIndex) {
		this.primaryIndex = primaryIndex;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public void setDatasourcePath(String datasourcePath) {
		this.datasourcePath = datasourcePath;
	}

	
}
