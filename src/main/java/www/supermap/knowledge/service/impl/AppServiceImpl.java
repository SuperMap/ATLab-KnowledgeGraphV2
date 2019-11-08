package www.supermap.knowledge.service.impl;

import java.util.ArrayList;
import java.util.List;

import www.supermap.knowledge.controller.KnowledgeGraph;
import www.supermap.knowledge.dao.JenaDao;
import www.supermap.knowledge.service.AppService;
import www.supermap.knowledge.utils.ConfigUtil;

public class AppServiceImpl implements AppService {
	JenaDao jenaDao;
	public AppServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KnowledgeGraph createNewKnowledgeGraph(String storePath, int gridLevel) {
		//检查所有知识图谱存储配置，不存在则创建
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(storePath, gridLevel);
		boolean exist = getAllKnowledgeGraph().contains(knowledgeGraph);
		boolean boo = exist?false:jenaDao.createDataSet();
		return boo?knowledgeGraph:null;
	}

	@Override
	public List<KnowledgeGraph> getAllKnowledgeGraph() {
		//读取配置文件
		//待完善
		return null;
	}

}
