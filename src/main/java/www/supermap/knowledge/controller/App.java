package www.supermap.knowledge.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import www.supermap.knowledge.beans.GeoInfo;
import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.service.AppService;
import www.supermap.knowledge.service.KnowledgeService;
import www.supermap.knowledge.service.impl.AppServiceImpl;
import www.supermap.knowledge.service.impl.KnowledgeServiceImpl;
import www.supermap.knowledge.utils.Parameter;

public class App {
	
	KnowledgeService knowledgeService = new KnowledgeServiceImpl();
	AppService appService = new AppServiceImpl();
	
	// 前置工作：检查默认图谱配置json文件，没有则创建
	static {
		File knowledgeConfigFile = new File(Parameter.KNOWLEDGE_CONFIG_FILE_NAME);
		if (!knowledgeConfigFile.exists()) {
			try {
				knowledgeConfigFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public App() {

	}

	public KnowledgeGraph getKnowledgeGraph(String storePath, int gridLevel) {
		KnowledgeGraph knowledgeGraph = appService.getKnowledgeGraph(storePath, gridLevel);
		return knowledgeGraph;
	}

	public List<KnowledgeGraph> listAllKnowledgeGraph() {
		List<KnowledgeGraph> allKnowledgeGraph = appService.getAllKnowledgeGraph();
		return allKnowledgeGraph;
	}
	
	public boolean addDataByType(KnowledgeGraph targetKnowledgeGraph, String sourceDataFile, String[] arTypes) {
		return knowledgeService.addDataByType(targetKnowledgeGraph, sourceDataFile, arTypes);
	}

	public boolean addAllData(KnowledgeGraph targetKnowledgeGraph, String sourceDataFile) {
		return addDataByType(targetKnowledgeGraph, sourceDataFile, null);
	}

	public Map<String, List<GeoInfo>> query(KnowledgeGraph targetKnowledgeGraph, double dLongitude, double dLatitude,
			double iRadius, String[] arType) {
		// 后期要加入时间属性 String time
		return knowledgeService.queryByType(targetKnowledgeGraph, dLongitude, dLatitude, iRadius, arType);
	}
}
