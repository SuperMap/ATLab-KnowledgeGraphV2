package www.supermap.knowledge.dao.impl;

import static org.junit.Assert.*;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Bag;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;
import org.apache.jena.vocabulary.VCARD4;
import org.junit.Test;

import www.supermap.knowledge.beans.Parameter;
import www.supermap.knowledge.dao.JenaDao;

public class JenaDaoImplTest {
	JenaDao jenaDao;
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void createDataSetTest(){
		Model model = ModelFactory.createDefaultModel();
		Bag bag = model.createBag();
		bag.addProperty(VCARD.NAME, "学校");
		bag.add("美视国际学校");
//		Resource sfsdf = model.createResource("dskfdf");
//		model.add(sfsdf, (Property) RDF.B ag, bag);
		model.add(bag,RDF.type, RDF.Bag);
		model.write(System.out);
	}

	@Test
	public void jenatest(){
		Dataset dataSet = TDBFactory.createDataset("test");
		try {
			Model dataSourceModel = dataSet.getNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME);
			dataSet.containsNamedModel(Parameter.DATA_SOURCE_SET_MODEL_NAME);
			//列出所有model
			dataSet.listNames();
			StmtIterator iter = dataSourceModel.listStatements(
				    new SimpleSelector(null, VCARD.FN, (RDFNode) null) {
				        public boolean selects(Statement s)
				            {return s.getString().endsWith("Smith");}
				    });
			boolean b = dataSourceModel.containsAny(iter);
			System.out.println(b);
//			StmtIterator iter = dataSourceModel.listStatements();
//			if (iter.hasNext()){
//				System.out.println(iter.next());
//			}else{
//				System.out.println("NUll");
//			}
			dataSet.commit();
		} finally {
			dataSet.end();
		}
	}
}
