package www.supermap.knowledge.beans;

import com.supermap.data.Geometry;
/**
 * 用来定义从数据源中读出的实体类
 * @author SunYasong
 *
 */
public class MyGeometry {

	//图形的信息
	private Geometry geometry;
//	private String databaseName;
	//在recordset中的序号，数据表中的主键
	private int primaryIndex;
//	private String dataSourceName;
//	private String datasourcePath;
	//该图形所在的网格id
	private long gridId;
	
	// private String time;

	public MyGeometry() {
		// TODO Auto-generated constructor stub
	}	
	
	public MyGeometry(Geometry geometry, int primaryIndex) {
		super();
		this.geometry = geometry;
		this.primaryIndex = primaryIndex;
	}

	public Geometry getGeometry() {
		return geometry;
	}
	
	public int getPrimaryIndex() {
		return primaryIndex;
	}


	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public void setPrimaryIndex(int primaryIndex) {
		this.primaryIndex = primaryIndex;
	}

	public long getGridId() {
		return gridId;
	}

	public void setGridId(long gridId) {
		this.gridId = gridId;
	}
	
	
	
}

