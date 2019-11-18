package www.supermap.knowledge.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.jena.query.Dataset;
import org.apache.jena.tdb.TDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.dao.JenaDao;
import www.supermap.knowledge.dao.impl.JenaDaoImpl;
import www.supermap.knowledge.service.AppService;
import www.supermap.knowledge.utils.ConfigUtil;
import www.supermap.knowledge.utils.Parameter;

public class AppServiceImpl implements AppService {
	private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);
	JenaDao jenaDao = new JenaDaoImpl();

	public AppServiceImpl() {

	}

	@Override
	public List<KnowledgeGraph> getAllKnowledgeGraph() {
		// 1. 读取配置文件,转换成json对象
		// 2. 遍历json对象，获得所有的knowledge对象
		String jsonString = new ConfigUtil().getInstance().readJsonFile(Parameter.KNOWLEDGE_CONFIG_FILE_NAME);
		List<KnowledgeGraph> knowledgeGraphList = new ArrayList<KnowledgeGraph>();
		// 处理空文件的情况
		if (jsonString == null) {
			logger.debug("json文件转换后的对象为空");
			return knowledgeGraphList;
		} else {
			JSONObject jsonObject = JSONObject.parseObject(jsonString);
			if (jsonObject == null) {
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
		if (getAllKnowledgeGraph() == null || !exist) {
			boolean isAdded = new ConfigUtil().getInstance().addKnowledgeGraphToConfigFile(knowledgeGraph);
			boolean isCreated = jenaDao.createDataSet(storePath);
			return (isCreated && isAdded) ? knowledgeGraph : null;
		} else {
			return knowledgeGraph;
		}
	}

	/**
	 * 删除知识图谱的功能 
	 * 暂时不写了
	 * sj:2019年11月18日 16:21:38
	 * @param knowledgeGraph
	 * @return
	 */
	private boolean deleteKnowledge(KnowledgeGraph knowledgeGraph) {
		//1. 判断该图谱是否存在
		//2. 删除文件夹
		//3. 删除config里面的信息
//		boolean isExist = new ConfigUtil().getInstance().existKnowledge(knowledgeGraph);
		try {
			// 删除数据文件夹
			String dataStorePath =  knowledgeGraph.getDataStorePath();
//			boolean folderdeleted = new ConfigUtil().getInstance().deleteFolder(dataStorePath);
//			boolean jsondeleted = new ConfigUtil().getInstance().deleteKnowledgeGraphFromConfigFile(knowledgeGraph);
//			return folderdeleted&&jsondeleted;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//出错的返回
		return false;
	}

}
