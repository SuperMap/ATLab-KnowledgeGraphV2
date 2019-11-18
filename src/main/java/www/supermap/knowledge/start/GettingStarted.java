package www.supermap.knowledge.start;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import www.supermap.knowledge.beans.GeoInfo;
import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.controller.App;

public class GettingStarted {

	public static void main(String[] args) {
		App app = new App();
		// 知识图谱存储的文件夹
		String storePath = "test";
		// 知识图谱网格层级
		int gridLevel = 13;

		KnowledgeGraph knowledgeGraph = app.getKnowledgeGraph(storePath, gridLevel);

		// 将停车场类型的数据存入知识图谱
		String sourceDataFile = "SampleData\\sample.udb";
		String[] arTypes = { "停车场" };
		boolean isSaved = app.addDataByType(knowledgeGraph, sourceDataFile, arTypes);

		// 查询经纬度为(106.553833, 29.6001)方圆1000米的所有停车场类型的实体
		// Map<String, List<GeoInfo>>中k为类型，v为该类型的所有实体
		Map<String, List<GeoInfo>> result = app.query(knowledgeGraph, 106.553833, 29.6001, 1000, arTypes);
		// 打印
		for (Entry<String, List<GeoInfo>> entry : result.entrySet()) {
			System.out.println(entry.getKey());
			for (GeoInfo geoInfo : entry.getValue()) {
				System.out.println(geoInfo);
			}
			System.out.println();
		}
	}

}
