package www.supermap.knowledge.beans;

import java.util.List;

import com.supermap.data.Geometry;

/**
 * 用来定义从数据源中读出的实体类
 * 
 * @author SunYasong
 *
 */
public class MyGeometry {

	// 图形的信息
	private Geometry geometry;

	// datasource的id
	private int dataSourceId;

	// dataSet的id
	private int dataSetId;

	// 在recordset中的序号，类似数据表中的主键
	private int primaryId;

	// 类型,也可以理解为数据集名称--2019年11月12日 09:53:18
	private String type;

	// 该图形所在的网格id
	private List<Long> gridIds;

	public MyGeometry() {

	}

	public Geometry getGeometry() {
		return geometry;
	}

	public int getDataSourceId() {
		return dataSourceId;
	}

	public int getDataSetId() {
		return dataSetId;
	}

	public int getPrimaryId() {
		return primaryId;
	}

	public String getType() {
		return type;
	}

	public List<Long> getGridIds() {
		return gridIds;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public void setDataSourceId(int dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public void setDataSetId(int dataSetId) {
		this.dataSetId = dataSetId;
	}

	public void setPrimaryId(int primaryId) {
		this.primaryId = primaryId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setGridIds(List<Long> gridIds) {
		this.gridIds = gridIds;
	}

	@Override
	public String toString() {
		return "MyGeometry [geometry=" + geometry + ", dataSourceId=" + dataSourceId + ", dataSetId=" + dataSetId
				+ ", primaryId=" + primaryId + ", type=" + type + ", gridIds=" + gridIds + "]";
	}

}
