package www.supermap.knowledge.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.supermap.data.DatasourceConnectionInfo;

import www.supermap.knowledge.service.KnowledgeSercive;
import www.supermap.knowledge.beans.Grid;
import www.supermap.knowledge.beans.MyGeometry;
import www.supermap.knowledge.controller.KnowledgeGraph;
import www.supermap.knowledge.dao.DataSourceDao;
import www.supermap.knowledge.dao.JenaDao;
import www.supermap.knowledge.utils.*;

public class KnowledgeServiceImpl implements KnowledgeSercive {
	DataSourceDao dataBaseDao;
	JenaDao jenaDao;
	
	public KnowledgeServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean addDataSetByName(DatasourceConnectionInfo dataSourceConnectionInfo, KnowledgeGraph targetKnowledgeGraph, String[] arType) {
		//1. 首先对比DatasourceConnectionInfo，获得DatasourceConnectionInfo在配置文件中的id
		ConfigUtil configuration = ConfigUtil.getInstance();
		int dataSourceId = configuration.getDataSourceConnectionId(dataSourceConnectionInfo);
		//2. 获得数据库中所有的geometry
		ArrayList<MyGeometry> myGeometrys = dataBaseDao.selectGeometrysByType(dataSourceConnectionInfo, arType);
		//3. 将所有geometry分到不同的网格里面
		List<Grid> girds = S2ServiceUtil.getGridsWithMyGeometry(myGeometrys);
		//4. 将geometry存入知识图谱中
		boolean bool = jenaDao.insertGeometrys(targetKnowledgeGraph, girds, dataSourceId);
		return bool;
	}
	
	
	public boolean addAllDataSet(DatasourceConnectionInfo dataSourceConnectionInfo, KnowledgeGraph targetKnowledgeGraph) {
		// TODO Auto-generated method stub
		//待完善
		//1. 首先对比DatasourceConnectionInfo，获得DatasourceConnectionInfo在配置文件中的id
		ConfigUtil configuration = ConfigUtil.getInstance();
		int dataSourceId = configuration.getDataSourceConnectionId(dataSourceConnectionInfo);
		//2. 获得所有geometry
		ArrayList<MyGeometry> myGeometrys = dataBaseDao.selectAllGeometry(dataSourceConnectionInfo);
		//3. 将所有geometry分到不同的网格里面
		List<Grid> girds = S2ServiceUtil.getGridsWithMyGeometry(myGeometrys);
		//4. 将geometry存入知识图谱中
		boolean bool = jenaDao.insertGeometrys(targetKnowledgeGraph, girds, dataSourceId);
		return bool;
	}
	

	

	
	@Override
	public boolean addDataByType(String dataFile, String[] arTypes) {
		//1. 首先对比DatasourceConnectionInfo，获得DatasourceConnectionInfo在配置文件中的id
		ConfigUtil configuration = ConfigUtil.getInstance();
		
		return false;
	}


	@Override
	public boolean addAllData(String dataFile) {
		
		return false;
	}

	
	@Override
	public Map<String, ArrayList<String>> query(KnowledgeGraph targetKnowledgeGraph, double dLatitude, double dLongitude, double iRadius, String[] arType, String time) {
		// TODO Auto-generated method stub
		//待完善
		Map<String, ArrayList<String>> infos = jenaDao.selectInfo(targetKnowledgeGraph, dLatitude, dLongitude, iRadius, arType, time);
		return infos;
	}
	
}
