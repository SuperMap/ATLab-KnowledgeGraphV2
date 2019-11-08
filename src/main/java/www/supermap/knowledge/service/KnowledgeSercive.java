package www.supermap.knowledge.service;

import java.util.ArrayList;
import java.util.Map;

import com.supermap.data.DatasourceConnectionInfo;

import www.supermap.knowledge.controller.KnowledgeGraph;

public interface KnowledgeSercive {
	
	/**
	 * 将数据源中指定类型的实体存入知识图谱中
	 * @param dataFile 数据源文件、工作空间或udb文件
	 * @param arTypes 要添加到知识图谱的数据类型
	 * @return 存入成功则返回True，反之为False
	 */
	public boolean addDataByType(String dataFile, String[] arTypes);
	
	/**
	 * 将数据源中所有的类型存入知识图谱
	 * @param dataFile 数据源文件
	 * @return 存入成功则返回True，反之为False
	 */
	public boolean addAllData(String dataFile);
	
	public Map<String, ArrayList<String>> query(KnowledgeGraph targetKnowledgeGraph, double dLatitude, double dLongitude, double iRadius, String[] arType, String time);



	
}
