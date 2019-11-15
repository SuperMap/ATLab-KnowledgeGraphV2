package www.supermap.knowledge.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;

import com.google.common.geometry.S2CellId;

import www.supermap.knowledge.beans.GeoInfo;
import www.supermap.knowledge.beans.KnowledgeGraph;

public interface JenaDao {
	
	public boolean createDataSet(String dataSetName);
	
	/**
	 * 将生成的Grid数据模型写入知识图谱
	 * @param targetKnowledgeGraph
	 * @param model 模型
	 * @param modelName 
	 * @return
	 */
	public boolean insertModelToKnowledge(KnowledgeGraph targetKnowledgeGraph, Model model, String modelName);

	/**
	 * 以数据源名称为条件查找该数据源的id，数据库中没有的话返回-1
	 * @param targetKnowledgeGraph
	 * @param dataSourceName
	 * @return 
	 */
	public int selectDataSourceIdByName(KnowledgeGraph targetKnowledgeGraph, String dataSourceName);
	
	public int insertDataSource(KnowledgeGraph targetKnowledgeGraph, String dataSourceName);
	
	/**
	 * 按照数据集名称查询数据集id，
	 * 注意：本方法不会验证数据集所属的数据源是否存在，因为主程序中先处理了数据源，之后才处理数据集
	 * @param targetKnowledgeGraph
	 * @param dataSourceId
	 * @param dataSetName
	 * @return
	 */
	public int selectDataSetIdByName(KnowledgeGraph targetKnowledgeGraph, int dataSourceId, String dataSetName);
	
	/**
	 * 按照数据源、数据集名称将数据集插入数据库，返回当前数据集在数据库中的id
	 * @param targetKnowledgeGraph 数据库名称
	 * @param dataSourceId 数据源名称
	 * @param dataSetName 数据集名称
	 * @return 数据集的id
	 */
	public int insertDataSet(KnowledgeGraph targetKnowledgeGraph, int dataSourceId, String dataSetName);

	/**
	 * 在指定知识图谱中按照类型条件进行查询,返回指定类型的id
	 * @param targetKnowledgeGraph
	 * @param coverCells 要查询的网格id
	 * @param arType 要查询的实体类型
	 * @return
	 */
	public Map<String, List<String>> selectInfoByType(KnowledgeGraph targetKnowledgeGraph, List<Long> coverCellIds, String[] arType);
	
	/**
	 * 通过数据源id查询数据源名称
	 * @param dataSourceId
	 * @return
	 */
	public String selectDataSourceNameById(KnowledgeGraph targetKnowledgeGraph, int dataSourceId);
	
	/**
	 * 通过数据源id、数据集id查询数据集的名称
	 * @param targetKnowledgeGraph
	 * @param dataSourceId
	 * @param dataSetId
	 * @return
	 */
	public String selectDataSetNameById(KnowledgeGraph targetKnowledgeGraph, int dataSourceId, int dataSetId);
	
	
	
	
}
