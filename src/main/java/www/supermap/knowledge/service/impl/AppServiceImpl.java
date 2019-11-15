package www.supermap.knowledge.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.beans.Parameter;
import www.supermap.knowledge.dao.JenaDao;
import www.supermap.knowledge.service.AppService;
import www.supermap.knowledge.utils.ConfigUtil;

public class AppServiceImpl implements AppService {
	private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);
	JenaDao jenaDao;
	
	public AppServiceImpl(){
		
	}
	@Override
	public List<KnowledgeGraph> getAllKnowledgeGraph() {
		// 1. 读取配置文件,转换成json对象
		// 2. 遍历json对象，获得所有的knowledge对象
		String jsonString = new ConfigUtil().getInstance().readJsonFile(Parameter.KNOWLEDGE_CONFIG_FILE_PATH);
		List<KnowledgeGraph> knowledgeGraphList = new ArrayList<KnowledgeGraph>();
		// 处理空文件的情况
		if (jsonString == null) {
			logger.debug("json文件转换后的对象为空");
			return knowledgeGraphList;
		} else {
			JSONObject jsonObject = JSONObject.parseObject(jsonString);
				if(jsonObject==null){
					return knowledgeGraphList;
				}
			// 遍历jsonobject
			Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String knowledgeValue = String.valueOf(entry.getValue());
				KnowledgeGraph knowledge = JSON.parseObject(knowledgeValue, KnowledgeGraph.class);
				knowledgeGraphList.add(knowledge);
			}
		}
		return knowledgeGraphList;
	}

	@Override
	public KnowledgeGraph getKnowledgeGraph(String storePath, int gridLevel) {
		// 1. 检查配置文件，有的话则返回，没有的话则进行两个事情，配置文件中添加该knowledgeGraph,创建数据库
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(storePath, gridLevel);
		boolean exist = getAllKnowledgeGraph().contains(knowledgeGraph);
		if(getAllKnowledgeGraph() == null || !exist){
			boolean isAdded = new ConfigUtil().getInstance().addKnowledgeGraphToConfigFile(knowledgeGraph);
			boolean isCreated = jenaDao.createDataSet(storePath);
			return (isCreated && isAdded) ? knowledgeGraph : null;
		}else {
			return knowledgeGraph;
		}
	}

}
