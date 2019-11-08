package www.supermap.knowledge.controller;

import com.supermap.data.DatasourceConnectionInfo;
import java.util.ArrayList;
import java.util.Map;
import www.supermap.knowledge.service.KnowledgeSercive;

/**
 * 知识图谱控制类
 * @author SunYasong
 *
 */
public class KnowledgeGraph {
	KnowledgeSercive knowledgeservice;
	
	//数据存储的根目录
	private String dataStorePath;
	//图谱构建使用的网格级别
	private int gridLevel = 13;
	
	public KnowledgeGraph(){
		
	}

	public KnowledgeGraph(String dataStorePath, int gridLevel) {
		super();
		this.dataStorePath = dataStorePath;
		this.gridLevel = gridLevel;
	}

	public String getDataStorePath() {
		return dataStorePath;
	}

	public int getGridLevel() {
		return gridLevel;
	}

	public void setDataStorePath(String dataStorePath) {
		this.dataStorePath = dataStorePath;
	}

	public void setGridLevel(int gridLevel) {
		this.gridLevel = gridLevel;
	}
	
	public boolean addDataByType(String dataFile, String...arTypes){
		return knowledgeservice.addDataByType(dataFile, arTypes);
	}
	
	public boolean addAllData(String dataFile){
		return knowledgeservice.addAllData(dataFile);
	}
	
	public Map<String, ArrayList<String>> query(double dLatitude, double dLongitude, double iRadius, String[] arType, String time){
		//待处理
//		knowledgeservice.query(this, dLatitude, dLongitude, iRadius, arType, time);
		return null;
	}
	
}
