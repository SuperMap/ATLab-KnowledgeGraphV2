package www.supermap.knowledge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.supermap.data.DatasourceConnectionInfo;

import www.supermap.knowledge.beans.GeoInfo;
import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.controller.App;


public interface KnowledgeService {
	
	/**
	 * 将数据源中以数据集名称为类型条件，将整个数据集添加到知识图谱中
	 * @param targetKnowledgeGraph 要存入的知识图谱
	 * @param dataFile 数据源文件、工作空间或udb文件
	 * @param arTypes 要添加到知识图谱的数据类型
	 * @return 存入成功则返回True，反之为False
	 */
	public boolean addDataByType(KnowledgeGraph targetKnowledgeGraph, String dataFile, String[] arTypes);
	
	public Map<String, List<GeoInfo>> queryByType(KnowledgeGraph targetKnowledgeGraph, double dLongitude, double dLatitude, double iRadius, String[] arType);



	
}
