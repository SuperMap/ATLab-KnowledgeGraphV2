package www.supermap.knowledge.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.supermap.data.CursorType;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasets;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.Geometry;
import com.supermap.data.Recordset;
import com.supermap.data.Workspace;

import www.supermap.knowledge.beans.MyGeometry;
import www.supermap.knowledge.dao.DataBaseDao;

public class DataBaseDaoImpl implements DataBaseDao {

	public DataBaseDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<MyGeometry> selectGeometrysByType(DatasourceConnectionInfo dataSourceConnectionInfo, String[] arType) {
		List<String> arTypeList = Arrays.asList(arType);
		ArrayList<MyGeometry> myGeometrys = new ArrayList<MyGeometry>();
		//读取数据库
		Datasource dataSource = new Workspace().getDatasources().open(dataSourceConnectionInfo);
		Datasets allDataSets = dataSource.getDatasets();
		for(int dataSetIndex = 0; dataSetIndex < allDataSets.getCount(); dataSetIndex++){
			String currentDataDetName = allDataSets.get(dataSetIndex).getName();	
			if(!arTypeList.contains(currentDataDetName)){
				continue;
			}
			else{
				DatasetVector dataSetVector = (DatasetVector) allDataSets.get(dataSetIndex);
				Recordset recordSet = dataSetVector.getRecordset(false, CursorType.STATIC);
				for (int recordIndex = 0; recordIndex < recordSet.getRecordCount(); recordIndex++) {
					Geometry geometry = recordSet.getGeometry();	
					MyGeometry myGeometry = new MyGeometry(geometry, recordIndex);
					myGeometrys.add(myGeometry);
				}
			}
		}
		return myGeometrys;
	}

	@Override
	public ArrayList<MyGeometry> selectAllGeometry(DatasourceConnectionInfo dataSourceConnectionInfo){
		// 打开数据源并获得所有数据集
		Datasource dataSource = new Workspace().getDatasources().open(dataSourceConnectionInfo);
		Datasets allDataSets = dataSource.getDatasets();
		ArrayList<MyGeometry> myGeometrys = new ArrayList<MyGeometry>();
		//循环遍历数据集，取出所有实体
		for(int dataSetIndex = 0; dataSetIndex < allDataSets.getCount(); dataSetIndex++){
			Dataset dataSet = allDataSets.get(dataSetIndex);
			DatasetVector dataSetVector = (DatasetVector) dataSet;
			Recordset recordSet = dataSetVector.getRecordset(false, CursorType.STATIC);
			for (int recordIndex = 0; recordIndex < recordSet.getRecordCount(); recordIndex++) {
				Geometry geometry = recordSet.getGeometry();	
				MyGeometry myGeometry = new MyGeometry(geometry, recordIndex);
				myGeometrys.add(myGeometry);
			}
		}
		return myGeometrys;
	}
	
}
