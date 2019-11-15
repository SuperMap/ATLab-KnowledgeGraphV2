package www.supermap.knowledge.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.tdb.TDBFactory;

import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.google.common.geometry.S2CellId;

import www.supermap.knowledge.beans.GeoInfo;
import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.beans.Parameter;
import www.supermap.knowledge.beans.Prefixes;
import www.supermap.knowledge.dao.JenaDao;

public class JenaDaoImpl implements JenaDao {

	@Override
	public boolean createDataSet(String dataSetName) {
		try {
			TDBFactory.createDataset(dataSetName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public int selectDataSourceIdByName(KnowledgeGraph targetKnowledgeGraph, String dataSourceName) {
		//1. 首先检查数据库中有没有存数据源、数据集的model，没有则返回-1，让主程序处理
		//2. 有的话按谓语 have_data_set 搜索语句，返回迭代器
		//3. 搜索迭代器中每一条语句的客体，与当前要查询的dataSource名称进行对比，相同则取出该条语句的主体
		//4. 将主体字符串切掉前缀部分，剩下的就是id，返回该id
		//5. 如果迭代器都查完了，没有发现该数据源，则返回-1
		Dataset dataSet = TDBFactory.createDataset(targetKnowledgeGraph.getDataStorePath());
		try {
			dataSet.begin(ReadWrite.READ);
			// 图谱中没有存数据源、数据集的model，代表一个数据源都没有，直接返回-1
			if(!dataSet.containsNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME)){
				return -1;
			}
			else{
				Model dataSourceModel = dataSet.getNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME);
				Property haveSetProperty = dataSourceModel.createProperty(Prefixes.HAVE_DATA_SET);
				StmtIterator statementeIter = dataSourceModel.listStatements(null, haveSetProperty, (RDFNode)null);
				while(statementeIter.hasNext()){
					Statement statement = statementeIter.nextStatement();
					String objectName = statement.getObject().toString();
					if(objectName.equals(dataSourceName)){
						String subjectName = statement.getSubject().toString();
						String dataSourceIdString = subjectName.replace(Prefixes.DATA_SOURCE_PREFIX, "");
						return Integer.valueOf(dataSourceIdString);
					}
				}
				return -1;
			}
		} finally {
			dataSet.end();
		}
	}

	@Override
	public int insertDataSource(KnowledgeGraph targetKnowledgeGraph, String dataSourceName) {
		//1.判断数据源存不存在，不存在则创建
		//2.以有没有数据源名字谓语为条件，查询所有的主语，获得所有数据源中目前最大的id值
		//3.插入一条语句，主体为DATA_SOURCE_PREFIX+(id+1),谓语为Prefixs.DATA_SOURCE_NAME,客体为dataSourceName
		//4.返回该数据源的id
		Dataset dataSet = TDBFactory.createDataset(targetKnowledgeGraph.getDataStorePath());
		try {
			dataSet.begin(ReadWrite.WRITE);
			
			int maxDataSourceId = -1;
			// 获得当前数据库中最大的数据源id
			if(!dataSet.containsNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME)){
				dataSet.addNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME, dataSet.getDefaultModel());
				maxDataSourceId = 0;
			}else{
				Model dataSourceModel = dataSet.getNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME);
				ResIterator subjectIter = dataSourceModel.listSubjectsWithProperty(dataSourceModel.createProperty(Prefixes.DATA_SOURCE_NAME));
				while(subjectIter.hasNext()){
					String dataSourceSubjectString = subjectIter.nextResource().toString();
					String idString = dataSourceSubjectString.replace(Prefixes.DATA_SOURCE_PREFIX, "");
					int id = Integer.valueOf(idString);
					if(id > maxDataSourceId){
						maxDataSourceId = id;
					}
				}
			}
			// 插入语句
			Model dataSourceModel = dataSet.getNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME);
			int dataSourceId = maxDataSourceId + 1;
			Resource dataSource = dataSourceModel.createResource(Prefixes.DATA_SOURCE_PREFIX + dataSourceId);
			dataSourceModel.add(dataSource, dataSourceModel.createProperty(Prefixes.DATA_SOURCE_NAME), dataSourceName);
			
			dataSet.commit();
			return dataSourceId;
		} finally {
			dataSet.end();
		}	
	}

	@Override
	public int selectDataSetIdByName(KnowledgeGraph targetKnowledgeGraph, int dataSourceId, String dataSetName) {
		//注意：此方法默认数据源已经存在了。
		//1. 从配置类中得到数据源在jena中的model名，打开model
		//2. 用dataSourceId拼成数据源语句的主语、haveSet这两个条件查询客体，得到的客体迭代器即为该数据源下的数据集
		//3. 以迭代器中的客体为数据集主体，搜索其客体是否为dataSetName，是则返回主体id，没有则返回-1
		Dataset dataSet = TDBFactory.createDataset(targetKnowledgeGraph.getDataStorePath());
		try {
			dataSet.begin(ReadWrite.READ);
			
			Model dataSourceModel = dataSet.getNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME);
			Resource dataSource = dataSourceModel.createResource(Prefixes.DATA_SOURCE_PREFIX + dataSourceId);
			Property haveSetProperty = dataSourceModel.createProperty(Prefixes.HAVE_DATA_SET);
			// 二次查询
			NodeIterator dataSetIter = dataSourceModel.listObjectsOfProperty(dataSource, haveSetProperty);
			while(dataSetIter.hasNext()){
				Resource dataSetResource = (Resource) dataSetIter.nextNode();
				Property dataSetNameProperty = dataSourceModel.createProperty(Prefixes.DATA_SET_NAME);
				NodeIterator dataSetNameIter = dataSourceModel.listObjectsOfProperty(dataSetResource, dataSetNameProperty);
				while(dataSetNameIter.hasNext()){
					String dataSetNameString = dataSetNameIter.nextNode().toString();
					if(dataSetName.equals(dataSetNameString)){
						String dataSetIdString = dataSetResource.toString().replace(Prefixes.DATA_SET_PREFIX, "");
						int dataSetId = Integer.valueOf(dataSetIdString);
						return dataSetId;
					}
				}
			}
			return -1;
		} finally {
			dataSet.end();
		}
	}

	@Override
	public int insertDataSet(KnowledgeGraph targetKnowledgeGraph, int dataSourceId, String dataSetName) {
		//此方法默认数据源已经存在，不然也不会有dataSourceId
		//1. 找到指定数据源
		//2. 以haveDataSet为谓语条件，查询所有的客体（数据集id）
		//3. 从查询出的所有数据集id中找出最大的id
		//4. id+1 作为当前数据集的id，存入知识图谱
		Dataset dataSet = TDBFactory.createDataset(targetKnowledgeGraph.getDataStorePath());
		
		try{
			dataSet.begin(ReadWrite.WRITE);
			
			Model dataSourceModel = dataSet.getNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME);
			Resource dataSource = dataSourceModel.createResource(Prefixes.DATA_SOURCE_PREFIX + dataSourceId);
			Property haveSetProperty = dataSourceModel.createProperty(Prefixes.HAVE_DATA_SET);
			NodeIterator dataSetNodeIter = dataSourceModel.listObjectsOfProperty(dataSource, haveSetProperty);
			// 当没有查到数据集时，表示还没有数据集，直接返回0
			int maxDataSetId = 0;
			if(!dataSetNodeIter.hasNext()){
				return maxDataSetId;
			}
			else{
				//在数据集迭代器中找到id最大值
				while(dataSetNodeIter.hasNext()){
					Resource dataSetResource = (Resource)dataSetNodeIter.nextNode();
					String dataSetIdString = dataSetResource.toString().replace(Prefixes.DATA_SET_PREFIX, "");
					int dataSetId = Integer.valueOf(dataSetIdString);
					if(dataSetId > maxDataSetId){
						maxDataSetId = dataSetId;
					}
				}		
			}
			int dataSetId = maxDataSetId + 1;
			
			//将数据集信息写入model
			Resource dataSetResource = dataSourceModel.createResource(Prefixes.DATA_SET_PREFIX + dataSetId);
			dataSourceModel.add(dataSource, haveSetProperty, dataSetResource);
			Property dataSetNameProperty = dataSourceModel.createProperty(Prefixes.DATA_SET_NAME);
			dataSourceModel.add(dataSetResource, dataSetNameProperty, dataSetName);
			dataSet.commit();
			return dataSetId;
		}finally{
			dataSet.end();
		}
	}

	
	@Override
	public boolean insertModelToKnowledge(KnowledgeGraph targetKnowledgeGraph, Model model, String modelName) {
		Dataset dataSet = TDBFactory.createDataset(targetKnowledgeGraph.getDataStorePath());
		try {
			dataSet.addNamedModel(modelName, model);
			dataSet.commit();
		} finally {
			dataSet.end();
		}
		return true;
	}


	@Override
	public Map<String, List<String>> selectInfoByType(KnowledgeGraph targetKnowledgeGraph, List<Long> coverCellIds, String[] arType) {
		Dataset dataSet = TDBFactory.createDataset(targetKnowledgeGraph.getDataStorePath());
		try {
			dataSet.begin(ReadWrite.READ);
			
			//将类型放入map的key中，方便后面添加
			Map<String, List<String>> geoTypeAndIds = new HashMap<String, List<String>>();
			for (String geoType : arType) {
				geoTypeAndIds.put(geoType, new ArrayList<String>());
			}
			for (Long cellId : coverCellIds) {
				Model dataSourceModel = dataSet.getNamedModel(String.valueOf(cellId));
				for (Entry<String, List<String>> entry : geoTypeAndIds.entrySet()) {
					String type = entry.getKey();
					List<String> geoIdList = entry.getValue();
					Resource searchGeoType = dataSourceModel.createResource(Prefixes.GEO_TYPE_PREFIX + type);
					Property haveGeoProperty = dataSourceModel.createProperty(Prefixes.HAVE_GEO);
					NodeIterator nodeIter = dataSourceModel.listObjectsOfProperty(searchGeoType, haveGeoProperty);
					while(nodeIter.hasNext()){
						RDFNode geoId = nodeIter.nextNode();
						String geoIdString = geoId.toString().replace(Prefixes.GEO_ENTITY_PREFIX, "");
						geoIdList.add(geoIdString);
					}
				}
			}
			return geoTypeAndIds;
		} finally {
			dataSet.end();
		}
	}


	@Override
	public String selectDataSourceNameById(KnowledgeGraph targetKnowledgeGraph, int dataSourceId) {
		Dataset dataSet = TDBFactory.createDataset(targetKnowledgeGraph.getDataStorePath());
		try {
			dataSet.begin(ReadWrite.READ);
			Model dataSourceModel = dataSet.getNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME);
			Resource dataSource = dataSourceModel.createResource(Prefixes.DATA_SOURCE_PREFIX + dataSourceId);
			Property dataSourceNameProperty = dataSourceModel.createProperty(Prefixes.DATA_SOURCE_NAME);
			NodeIterator dataSourceNameIter = dataSourceModel.listObjectsOfProperty(dataSource, dataSourceNameProperty);
			if(dataSourceNameIter.hasNext()){
				RDFNode dataSourceName = dataSourceNameIter.nextNode();
				return dataSourceName.toString();
			}else{
				return null;
			}
		} finally {
			dataSet.end();
		}
	}


	@Override
	public String selectDataSetNameById(KnowledgeGraph targetKnowledgeGraph, int dataSourceId, int dataSetId) {
		//1. 先查出对应dataSourceId为主语，haveset为谓语的所有宾语，即所有数据集id的集合
		//2. 循环对比id，直到找到一样的id
		Dataset dataSet = TDBFactory.createDataset(targetKnowledgeGraph.getDataStorePath());
		try {
			dataSet.begin(ReadWrite.READ);
			Model dataSourceModel = dataSet.getNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME);
			Resource dataSetResource = dataSourceModel.createResource(Prefixes.DATA_SET_PREFIX + dataSetId);
			Resource dataSourceResource = dataSourceModel.createResource(Prefixes.DATA_SOURCE_PREFIX + dataSourceId);
			Property dataSetNameProperty = dataSourceModel.createProperty(Prefixes.DATA_SET_NAME);
			Property haveDataSetProperty = dataSourceModel.createProperty(Prefixes.HAVE_DATA_SET);
		
			//查询
			String queryString = "SELECT ?dataSetName WHERE {\n" +
				     "  ?dataSource ?haveSetProperty ?dataSet .\n" +
				     "  ?dataSet ?dataSetNameProperty ?dataSetName .\n" +
				     "}";
			  Query query = QueryFactory.create(queryString) ;
			  QuerySolutionMap initialBinding = new QuerySolutionMap();
			   initialBinding.add("dataSource", dataSourceResource);
			   initialBinding.add("haveSetProperty", haveDataSetProperty);
			   initialBinding.add("dataSet", dataSetResource);
			   initialBinding.add("dataSetNameProperty", dataSetNameProperty);
			   try (QueryExecution qexec = QueryExecutionFactory.create(query, dataSourceModel, initialBinding)) {
				    ResultSet results = qexec.execSelect() ;
				    for ( ; results.hasNext() ; )
				    {
				      QuerySolution soln = results.nextSolution() ;
				      RDFNode dataSetName = soln.get("dataSetName") ;
				      return dataSetName.toString();
				    }
				  }
			
		} finally {
			dataSet.end();
		}
		// 没有搜索到则返回null
		return null;
	}



}
