package www.supermap.knowledge.beans;

import com.supermap.data.DatasourceConnectionInfo;
import java.util.ArrayList;
import java.util.Map;
import www.supermap.knowledge.service.KnowledgeService;

/**
 * 知识图谱控制类
 * @author SunYasong
 *
 */
public class KnowledgeGraph {
	
	//数据存储的根目录
	private String dataStorePath;
	//图谱构建使用的网格级别
	private int gridLevel;
	
	public KnowledgeGraph(){
		
	}

	public KnowledgeGraph(String dataStorePath, int gridLevel) {
		super();
		this.dataStorePath = dataStorePath;
		this.gridLevel = gridLevel;
	}

	public String getDataStorePath() {
		return dataStorePath;
	}

	public int getGridLevel() {
		return gridLevel;
	}

	@Override
	public String toString() {
		return "KnowledgeGraph [dataStorePath=" + dataStorePath + ", gridLevel=" + gridLevel + "]";
	}

	
}
