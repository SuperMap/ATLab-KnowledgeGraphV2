package www.supermap.knowledge.service;

import java.util.ArrayList;
import java.util.Map;

import com.supermap.data.DatasourceConnectionInfo;

import www.supermap.knowledge.beans.KnowledgeGraph;

public interface KnowledgeSercive {

	public boolean addDataSetByName(DatasourceConnectionInfo dataSourceConnectionInfo, KnowledgeGraph targetKnowledgeGraph, String[] arType);
	
	public boolean addAllDataSet(DatasourceConnectionInfo dataSourceConnectionInfo, KnowledgeGraph targetKnowledgeGraph);
	
	public Map<String, ArrayList<String>> query(KnowledgeGraph targetKnowledgeGraph, double dLatitude, double dLongitude, double iRadius, String[] arType, String time);
}
