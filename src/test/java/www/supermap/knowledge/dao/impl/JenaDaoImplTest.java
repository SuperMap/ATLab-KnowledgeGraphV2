package www.supermap.knowledge.dao.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.assembler.RuleSet;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.junit.Test;

import com.supermap.data.DatasourceAliasModifiedListener;

import junit.framework.Assert;
import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.utils.Parameter;
import www.supermap.knowledge.utils.Prefixes;

public class JenaDaoImplTest {

	@Test
	public void testCreateDataSet() {
		JenaDaoImpl jenaDaoImpl = new JenaDaoImpl();
		String dataSetName = "test";
		boolean actual = jenaDaoImpl.createDataSet(dataSetName);
		Assert.assertEquals(true, actual);
	}

	@Test
	public void testSelectDataSourceIdByName() {
		String dataStorePath = "test";
		int gridLevel = 13;
		String dataSourceName = "SampleData\\sample.udb";
		JenaDaoImpl jenaDaoImpl = new JenaDaoImpl();
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(dataStorePath, gridLevel);
		
		//1.测试jena数据库中没有默认存储数据源、数据集的model
//		int result = jenaDaoImpl.selectDataSourceIdByName(knowledgeGraph, dataSourceName);
//		System.out.println(result);
//		Assert.assertEquals(-1, result);
		
		//2. 测试jena数据库中有该数据源,输出id
		//注意：首先得使用testInsertDataSource将dataSourceName添加到图谱中
		int result = jenaDaoImpl.selectDataSourceIdByName(knowledgeGraph, dataSourceName);
		System.out.println(result);
		Assert.assertEquals(0, result);
	}

	@Test
	public void testInsertDataSource() {
		String dataStorePath = "test";
		int gridLevel = 13;
		String dataSourceName = "SampleData\\sample.udb";
		JenaDaoImpl jenaDaoImpl = new JenaDaoImpl();
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(dataStorePath, gridLevel);
		int sourceId = jenaDaoImpl.insertDataSource(knowledgeGraph, dataSourceName);
		System.out.println(sourceId);
	}

	@Test
	public void testSelectDataSetIdByName() {
		String dataStorePath = "test";
		int gridLevel = 13;
		JenaDaoImpl jenaDaoImpl = new JenaDaoImpl();
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(dataStorePath, gridLevel);
		String dataSourceName = "SampleData\\sample.udb";
		int sourceId = jenaDaoImpl.selectDataSourceIdByName(knowledgeGraph, dataSourceName);
		int dataSetId = jenaDaoImpl.selectDataSetIdByName(knowledgeGraph, sourceId, "停车场");
		System.out.println(dataSetId);
	}

	@Test
	public void testInsertDataSet() {
		String dataStorePath = "test";
		int gridLevel = 13;
		JenaDaoImpl jenaDaoImpl = new JenaDaoImpl();
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(dataStorePath, gridLevel);
		String dataSourceName = "SampleData\\sample.udb";
		int sourceId = jenaDaoImpl.selectDataSourceIdByName(knowledgeGraph, dataSourceName);
		int dataSetId = jenaDaoImpl.insertDataSet(knowledgeGraph, sourceId, "停车场");
		System.out.println(dataSetId);
	}

	@Test
	public void testInsertModelToKnowledge() {
		String dataStorePath = "test";
		int gridLevel = 13;
		JenaDaoImpl jenaDaoImpl = new JenaDaoImpl();
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(dataStorePath, gridLevel);
		String dataSourceName = "SampleData\\sample.udb";
	}

	@Test
	public void testSelectInfoByType() {
		//待测 3931774364550168576
		String dataStorePath = "test";
		int gridLevel = 13;
		JenaDaoImpl jenaDaoImpl = new JenaDaoImpl();
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(dataStorePath, gridLevel);
		String dataSourceName = "SampleData\\sample.udb";
		String[] arTypes = {"停车场"};
		List<Long> coverCellIds = new ArrayList<>();
		coverCellIds.add(Long.valueOf("3931774364550168576"));
		Map<String, List<String>> infos = jenaDaoImpl.selectInfoByType(knowledgeGraph, coverCellIds, arTypes);
		for (Entry<String, List<String>> entry : infos.entrySet()) {
			System.out.println(entry.getKey());
			for (String name : entry.getValue()) {
				System.out.print(name+"--");
			}
			System.out.println();
		}
	}

	@Test
	public void testSelectDataSourceNameById() {
		String dataStorePath = "test";
		int gridLevel = 13;
		JenaDaoImpl jenaDaoImpl = new JenaDaoImpl();
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(dataStorePath, gridLevel);
		String dataSourceName = "SampleData\\sample.udb";
		String actualDataSourceName = jenaDaoImpl.selectDataSourceNameById(knowledgeGraph, 0);
		System.out.println(actualDataSourceName);
	}

	@Test
	public void testSelectDataSetNameById() {
		String dataStorePath = "test";
		int gridLevel = 13;
		JenaDaoImpl jenaDaoImpl = new JenaDaoImpl();
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(dataStorePath, gridLevel);
		String dataSourceName = "SampleData\\sample.udb";
		String dataSetName = jenaDaoImpl.selectDataSetNameById(knowledgeGraph, 0, 0);
		System.out.println(dataSetName);
	}
	
	
	
	@Test
	public void listModels() {
		Dataset dataset = TDBFactory.createDataset("test");
        dataset.begin(ReadWrite.READ);
        List<String> uriList = new ArrayList<>();
        try {
            Iterator<String> names = dataset.listNames();
            String name;
            while (names.hasNext()) {
                name = names.next();
                uriList.add(name);
               //输出
//                System.out.println(name);
            }
            
        } finally {
            dataset.end();
            System.out.println(uriList.size());
            for (String string : uriList) {
				System.out.println(string);
			}
        }
    }
	
	@Test
	public void printAllStatement(){
		Dataset dataSet = TDBFactory.createDataset("test");
        dataSet.begin(ReadWrite.READ);
        Model model = dataSet.getNamedModel("3957763486536171520");
        model.write(System.out, "N-TRIPLES");
	}
}
