package www.supermap.knowledge.dao;

import java.util.ArrayList;

import com.supermap.data.DatasourceConnectionInfo;

import www.supermap.knowledge.beans.MyGeometry;

public interface DataSourceDao {

	public ArrayList<MyGeometry> selectGeometrysByType(DatasourceConnectionInfo dataSourceConnectionInfo, String[] arType);

	public ArrayList<MyGeometry> selectAllGeometry(DatasourceConnectionInfo dataSourceConnectionInfo);
	

}
