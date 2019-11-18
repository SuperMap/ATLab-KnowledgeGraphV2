package www.supermap.knowledge.beans;

import com.supermap.data.Geometry;
import com.supermap.data.GeometryType;
import com.supermap.data.Point2D;
import com.supermap.data.Recordset;

/**
 * 承载前端展示数据的模型，主要数据源来自RecordSet
 * 
 * @author SunYasong
 *
 */
public class GeoInfo {

	private String id;
	
	private GeometryType geometryType;
	
	// 记录集在图谱中的id，通过id可以去对应数据源、数据集查看记录集
	private String recordId;
	// 记录集所在数据集的路径
	private String DataSourcePath;
	// 记录的类型，即数据集的名称
	private String entityType;
	// 记录集所代表的图形的内点
	private Point2D point;
	// 记录集中的一个字段，名称
	private String mingCheng;
	// 时间
	private String shiJian;

	public GeoInfo(Recordset recordSet) {
		getRequiredInfo(recordSet);
	}
	
	public String getId() {
		return id;
	}

	public GeometryType getGeometryType() {
		return geometryType;
	}

	public String getDataSourcePath() {
		return DataSourcePath;
	}

	public String getEntityType() {
		return entityType;
	}

	public Point2D getPoint() {
		return point;
	}

	public String getMingCheng() {
		return mingCheng;
	}

	public String getShiJian() {
		return shiJian;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDataSourcePath(String dataSourcePath) {
		DataSourcePath = dataSourcePath;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public void setPoint(Point2D point) {
		this.point = point;
	}

	public void setMingCheng(String mingCheng) {
		this.mingCheng = mingCheng;
	}

	public void setShiJian(String shiJian) {
		this.shiJian = shiJian;
	}



	/**
	 * 通过recordset来获取类似名称字段值，有的记录集没有名称字段，则返回类似的字段，如：位置、区县
	 * 
	 * @param recordSet
	 */
	private void getRequiredInfo(Recordset recordSet) {
		// TODO Auto-generated method stub
		// 取出实体位于的经纬度
		Geometry geometry = recordSet.getGeometry();
		this.geometryType = geometry.getType();
		this.point = geometry.getInnerPoint();
		this.shiJian = recordSet.getFieldValue("sj").toString();
		// 获取位置信息
		// 不是所有的实体都有名称字段，因此首先检查名称字段，没有的话检查位置，再检查区县，再没有就直接用null代替
		try {
			this.mingCheng = (String) recordSet.getFieldValue("mc");
		} catch (Exception e) {
			// TODO: handle exception
			try {
				this.mingCheng = (String) recordSet.getFieldValue("wz");
			} catch (Exception e2) {
				// TODO: handle exception
				try {
					this.mingCheng = (String) recordSet.getFieldValue("qx");
				} catch (Exception e3) {
					// TODO: handle exception
					this.mingCheng = null;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "GeoInfo [id=" + id + ", geometryType=" + geometryType + ", DataSourcePath="
				+ DataSourcePath + ", entityType=" + entityType + ", point=" + point + ", mingCheng=" + mingCheng
				+ ", shiJian=" + shiJian + "]";
	}

	
}
