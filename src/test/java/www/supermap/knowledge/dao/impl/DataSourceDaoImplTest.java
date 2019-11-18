package www.supermap.knowledge.dao.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import www.supermap.knowledge.beans.GeoInfo;
import www.supermap.knowledge.beans.MyGeometry;


public class DataSourceDaoImplTest {
	
	@Test
	public void testSelectAllDataSourceByFile() {
		DataSourceDaoImpl dataSourceDaoImpl = new DataSourceDaoImpl();
		//1. 测试udb文件
		String dataFile = "SampleData\\sample.udb";
		//2. 测试smwu工作空间
//		String dataFile = "C:\\Users\\Administrator\\Desktop\\osm-shp\\knowledge.smwu";
		HashMap<String, ArrayList<String>> dataSourceAndDataSetNames = dataSourceDaoImpl.selectAllDataSourceByFile(dataFile);
		
		for (Entry<String, ArrayList<String>> entry : dataSourceAndDataSetNames.entrySet()) {
			System.out.println(entry.getKey());
			for (String name : entry.getValue()) {
				System.out.print(name+"--");
			}
			System.out.println();
		}
		
		
		
	}
	
	@Test
	public void testSelectDataByDataSetName() {
		// sample.udb 中的停车场数据集做测试
		DataSourceDaoImpl dataSourceDaoImpl = new DataSourceDaoImpl();
		String dataFile = "SampleData\\sample.udb";
		String dataSetName = "停车场";
		List<MyGeometry> myGeometrys = dataSourceDaoImpl.selectDataByDataSetName(dataFile, dataSetName);
		System.out.println(myGeometrys.get(327));
		System.out.println(myGeometrys.size());
		
	}

	@Test
	public void testSelectRecordById() {
		DataSourceDaoImpl dataSourceDaoImpl = new DataSourceDaoImpl();
		String dataFile = "SampleData\\sample.udb";
		String dataSetName = "停车场";
		int id = 327;
		GeoInfo geoInfo = dataSourceDaoImpl.selectRecordById(dataFile, dataSetName, id);
		System.out.println(geoInfo);
	}

}
