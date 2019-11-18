package www.supermap.knowledge.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.supermap.data.CursorType;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.Datasources;
import com.supermap.data.EngineType;
import com.supermap.data.Recordset;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;

import www.supermap.knowledge.beans.GeoInfo;
import www.supermap.knowledge.beans.MyGeometry;
import www.supermap.knowledge.dao.DataSourceDao;

public class DataSourceDaoImpl implements DataSourceDao {
	
	@Override
	public HashMap<String, ArrayList<String>> selectAllDataSourceByFile(String dataFile) {
		HashMap<String, ArrayList<String>> dataSourceAndDataSetNames = new HashMap<String, ArrayList<String>>();
		// 处理smwu文件
		if (dataFile.endsWith("smwu")) {
			// Workspace workspace = openWorkSpace(dataFile);
			// 打开工作空间
			Workspace workspace = new Workspace();
			WorkspaceConnectionInfo workspaceConnectionInfo = new WorkspaceConnectionInfo();
			workspaceConnectionInfo.setType(WorkspaceType.SMWU);
			workspaceConnectionInfo.setServer(dataFile);
			workspace.open(workspaceConnectionInfo);

			// 处理
			Datasources dataSources = workspace.getDatasources();
			for (int i = 0; i < dataSources.getCount(); i++) {
				Datasource dataSource = dataSources.get(i);
				String dataSourceName = dataSource.getConnectionInfo().getServer();
				ArrayList<String> dataSetNames = new ArrayList<String>();
				for (int j = 0; j < dataSource.getDatasets().getCount(); j++) {
					Dataset dataSet = dataSource.getDatasets().get(j);
					String dataSetName = dataSet.getName();
					dataSetNames.add(dataSetName);
					dataSet.close();
				}
				dataSourceAndDataSetNames.put(dataSourceName, dataSetNames);
			}

			// 关闭工作空间
			workspaceConnectionInfo.dispose();
			workspace.close();
			workspace.dispose();
		} else if (dataFile.endsWith("udb")) {
			// 打开数据源
			Workspace workSpace = new Workspace();
			DatasourceConnectionInfo dataSourceConnectionInfo = new DatasourceConnectionInfo();
			dataSourceConnectionInfo.setServer(dataFile);
			dataSourceConnectionInfo.setEngineType(EngineType.UDB);
			Datasource dataSource = workSpace.getDatasources().open(dataSourceConnectionInfo);

			// 处理
			ArrayList<String> dataSetNames = new ArrayList<String>();
			for (int j = 0; j < dataSource.getDatasets().getCount(); j++) {
				Dataset dataSet = dataSource.getDatasets().get(j);
				String dataSetName = dataSet.getName();
				dataSetNames.add(dataSetName);
				dataSet.close();
			}
			dataSourceAndDataSetNames.put(dataFile, dataSetNames);

			// 关闭数据源
			dataSource.close();
		} else {
			new Exception("目前只支持smwu工作空间与udb");
			System.exit(1);
		}
		return dataSourceAndDataSetNames;
	}

	@Override
	public List<MyGeometry> selectDataByDataSetName(String dataSourceName, String dataSetName) {
		List<MyGeometry> myGeometrys = new ArrayList<MyGeometry>();
		// 打开数据源
		Workspace workSpace = new Workspace();
		DatasourceConnectionInfo dataSourceConnectionInfo = new DatasourceConnectionInfo();
		dataSourceConnectionInfo.setServer(dataSourceName);
		dataSourceConnectionInfo.setEngineType(EngineType.UDB);
		Datasource dataSource = workSpace.getDatasources().open(dataSourceConnectionInfo);

		// 处理
		Dataset dataSet = dataSource.getDatasets().get(dataSetName);
		DatasetVector dataSetVector = (DatasetVector) dataSet;
		Recordset recordSet = dataSetVector.getRecordset(false, CursorType.STATIC);
		for (int i = 0; i < recordSet.getRecordCount(); i++) {
			MyGeometry myGeometry = new MyGeometry();
			myGeometry.setGeometry(recordSet.getGeometry());
			myGeometry.setPrimaryId(i);
			myGeometrys.add(myGeometry);
			recordSet.moveNext();
		}
		// 关闭数据集与数据源
		dataSet.close();
		dataSource.close();

		return myGeometrys;
	}

	@Override
	public GeoInfo selectRecordById(String dataSourceName, String dataSetName, int recordId) {
		// 打开数据源
		Workspace workSpace = new Workspace();
		DatasourceConnectionInfo dataSourceConnectionInfo = new DatasourceConnectionInfo();
		dataSourceConnectionInfo.setServer(dataSourceName);
		dataSourceConnectionInfo.setEngineType(EngineType.UDB);
		Datasource dataSource = workSpace.getDatasources().open(dataSourceConnectionInfo);
		Dataset dataSet = dataSource.getDatasets().get(dataSetName);
		DatasetVector dataSetVector = (DatasetVector) dataSet;
		Recordset recordSet = dataSetVector.getRecordset(false, CursorType.STATIC);
		recordSet.moveTo(recordId);

		// 构造GeoInfo类
		GeoInfo geoInfo = new GeoInfo(recordSet);

		// 关闭数据
		dataSet.close();
		dataSource.close();
		return geoInfo;
	}
}
