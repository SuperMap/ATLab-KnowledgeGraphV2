package www.supermap.knowledge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.supermap.data.DatasourceConnectionInfo;

import www.supermap.knowledge.dao.JenaDao;
import www.supermap.knowledge.service.AppService;
import www.supermap.knowledge.service.KnowledgeSercive;
import www.supermap.knowledge.utils.ConfigUtil;

public class App {
	AppService appService;
	KnowledgeGraph knowledgeGraph;
	JenaDao jenDao;
	public App() {
		
	}
	
	public KnowledgeGraph createKnowledgeGraph(String storePath, int gridLevel){
		KnowledgeGraph knowledgeGraph = appService.createNewKnowledgeGraph(storePath,gridLevel);
		return knowledgeGraph;
	}
	
	public List<KnowledgeGraph> listAllKnowledgeGraph(){
		List<KnowledgeGraph> allKnowledgeGraph = appService.getAllKnowledgeGraph();
		//读配置文件得出
		return null;
	}
	public App loadKnowledgeGraph(String storePath){
		
		return new App();
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
