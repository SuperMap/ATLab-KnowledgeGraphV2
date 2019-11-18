package www.supermap.knowledge.service;

import java.util.List;

import www.supermap.knowledge.beans.KnowledgeGraph;

public interface AppService {

	/**
	 * 新建一个知识图谱
	 * 
	 * @param storePath
	 *            存储路径
	 * @param gridLevel
	 *            网格等级
	 * @return 成功了返回该KnowledgeGraph,失败了返回null
	 */
	public KnowledgeGraph getKnowledgeGraph(String storePath, int gridLevel);

	public List<KnowledgeGraph> getAllKnowledgeGraph();

}
