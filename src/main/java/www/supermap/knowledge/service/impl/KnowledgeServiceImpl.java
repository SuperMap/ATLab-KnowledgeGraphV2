package www.supermap.knowledge.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.google.common.geometry.S2CellId;
import com.google.common.geometry.S2LatLng;
import com.supermap.data.GeoPoint;
import com.supermap.data.Recordset;

import www.supermap.knowledge.service.KnowledgeService;
import www.supermap.knowledge.beans.GeoInfo;
import www.supermap.knowledge.beans.Grid;
import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.beans.MyGeometry;
import www.supermap.knowledge.beans.Parameter;
import www.supermap.knowledge.beans.Prefixes;
import www.supermap.knowledge.dao.DataSourceDao;
import www.supermap.knowledge.dao.JenaDao;
import www.supermap.knowledge.utils.*;

public class KnowledgeServiceImpl implements KnowledgeService {
	DataSourceDao dataSourceDao;
	JenaDao jenaDao;

	public KnowledgeServiceImpl() {
	}

	@Override
	public Map<String, List<GeoInfo>> queryByType(KnowledgeGraph targetKnowledgeGraph, double dLongitude, double dLatitude, double iRadius, String[] arType) {
		// 1. 计算经纬度和半径所构成的面积
		// 2. 找出格子里符合条件的id
		// 3. 利用数据源、数据集id查找源文件的数据源、数据集名称，
		// 4. 再加上记录集id找出对应的的信息
		// 5. 填充GeoInfo类
		
		// 1.1 判断经纬度位于哪个网格
		S2LatLng laln = S2LatLng.fromDegrees(dLatitude, dLongitude);
		S2CellId cell = S2CellId.fromLatLng(laln).parent(targetKnowledgeGraph.getGridLevel());
		// 1.2 使用S2缓冲分析，得到缓冲区内的所有网格
		List<Long> coverCellIds = S2ServiceUtil.getCoveringCellIdsFromCell(cell, iRadius, targetKnowledgeGraph.getGridLevel());
		//2. 得到符合类型的所有id，key为类型，value为对应的id的集合
		Map<String, List<String>> geoTypeAndIds = jenaDao.selectInfoByType(targetKnowledgeGraph, coverCellIds, arType);
		
		Map<String, List<GeoInfo>> result = new HashMap<String, List<GeoInfo>>();
		
		//3.1 字符串分离出数据源、数据集、记录集index
		for (Entry<String, List<String>> entry : geoTypeAndIds.entrySet()) {
			ArrayList<GeoInfo> geoInfoList = new ArrayList<GeoInfo>();
			result.put(entry.getKey(), geoInfoList);
			for (String wholeId : entry.getValue()) {
				String[] wholeIdSplit = wholeId.split(Parameter.DATA_SOURCE_SET_SEGMENT);
				// 验证分离之后是否包括数据源id，数据集id，记录集index三部分
				if(wholeIdSplit.length != Parameter.ID_PART_NUMBER){
					new Exception("分离ID错误");
				}
				int dataSourceId = Integer.valueOf(wholeIdSplit[0]);
				int dataSetId = Integer.valueOf(wholeIdSplit[1]);
				int recordId = Integer.valueOf(wholeIdSplit[2]);
				//3.2 找出id对应的数据源与数据集
				String dataSourceName = jenaDao.selectDataSourceNameById(targetKnowledgeGraph, dataSourceId);
				String dataSetName = jenaDao.selectDataSetNameById(targetKnowledgeGraph, dataSourceId, dataSetId);
				//4. 获得指定记录集
				GeoInfo geoInfo  = dataSourceDao.selectRecordById(dataSourceName, dataSetName, recordId);
				geoInfoList.add(geoInfo);
 			}
		}
		
		
		return result;
	}

	@Override
	public boolean addDataByType(KnowledgeGraph targetKnowledgeGraph, String dataFile, String[] arTypes) {
		// 1. 获得数据源中的所有dataSource与数据集名称
		HashMap<String, ArrayList<String>> dataSourceAndDataSetNames = dataSourceDao
				.selectAllDataSourceByFile(dataFile);
		// 1.1 以据集名称名称为条件，留下指定类型
		if (arTypes != null) {
			ArrayList<String> dataSets = new ArrayList<String>(Arrays.asList(arTypes));
			for (Entry<String, ArrayList<String>> entry : dataSourceAndDataSetNames.entrySet()) {
				if (!entry.getValue().retainAll(dataSets)) {
					dataSourceAndDataSetNames.remove(entry.getKey());
				}
			}
		}
		//1.3 处理完之后要存到图谱中的所有数据集
		for (Entry<String, ArrayList<String>> entry : dataSourceAndDataSetNames.entrySet()) {
			// 2. 从jena中获得dataSource的ID
			String dataSourceName = entry.getKey();
			int dataSourceId;
			dataSourceId = jenaDao.selectDataSourceIdByName(targetKnowledgeGraph, dataSourceName);
			// 2.1 处理dataSourceId为-1的情况
			if (dataSourceId < 0) {
				dataSourceId = jenaDao.insertDataSource(targetKnowledgeGraph, dataSourceName);
			}

			for (String dataSetName : entry.getValue()) {
				// 3. 从jena中获得dataSet的ID
				int dataSetId;
				dataSetId = jenaDao.selectDataSetIdByName(targetKnowledgeGraph, dataSourceId, dataSetName);
				// 3.1 处理dataSourceId为null的情况
				if (dataSetId < 0) {
					dataSetId = jenaDao.insertDataSet(targetKnowledgeGraph, dataSourceId, dataSetName);
				}

				// 4. 打开数据源，设置myGeometry的geometry和type属性
				List<MyGeometry> myGeometrys = dataSourceDao.selectDataByDataSetName(dataSourceName, dataSetName);

				// 5.
				// 完善myGeometry信息，从数据源中读取时，已经设置了type和geometry属性，此步骤对剩下的datasourceId,datasetId,gridIds进行设置
				for (MyGeometry myGeometry : myGeometrys) {
					// 由于是将数据集分开处理的，这一批数据所有的都一样，所以没必要进行下面两行操作，仍旧得传值，暂时就这样吧
					myGeometry.setDataSourceId(dataSourceId);
					myGeometry.setDataSetId(dataSetId);
					myGeometry.setGridIds(S2ServiceUtil.getGeometryCoveringCells(myGeometry.getGeometry(),
							targetKnowledgeGraph.getGridLevel()));
				}

				// 6. 生成Grid数据模型
				List<Grid> grids = createGridsFromMyGeometry(myGeometrys);

				// 5. 生成model，并将其存入知识图谱
				for (Grid grid : grids) {
					Model model = createModelUseGrid(grid);
					String modelName = String.valueOf(grid.getGridId());
					jenaDao.insertModelToKnowledge(targetKnowledgeGraph, model, modelName);
				}
			}
		}
		return true;
	}

	/**
	 * 生成grid模型
	 * 
	 * @param myGeometrys
	 * @return
	 */
	private List<Grid> createGridsFromMyGeometry(List<MyGeometry> myGeometrys) {
		// 1. 获得所有geometry所占的网格id
		HashSet<Long> ids = new HashSet<Long>();
		for (MyGeometry myGeometry : myGeometrys) {
			for (Long cellId : myGeometry.getGridIds()) {
				ids.add(cellId);
			}
		}

		// 2. 对比cellId，将所有myGeometry划分到不同的cell中
		List<Grid> grids = new ArrayList<Grid>();
		for (Long id : ids) {
			ArrayList<MyGeometry> entityies = new ArrayList<MyGeometry>();
			for (MyGeometry myGeometry : myGeometrys) {
				for (Long cellId : myGeometry.getGridIds()) {
					if (cellId.longValue() == id.longValue()) {
						entityies.add(myGeometry);
						break;
					}
				}
			}
			Grid grid = new Grid(id, entityies);
			grids.add(grid);
		}
		return grids;
	}

	/**
	 * 使用grid生成model
	 * 
	 * @param grids
	 * @return
	 */
	private Model createModelUseGrid(Grid grid) {
		Model model = ModelFactory.createDefaultModel();
		// 创建网格实体类型的rdf语句
		String modelName = String.valueOf(grid.getGridId());
		Resource gridResource = model.createResource(Prefixes.GRID_PREFIX + modelName);
		Property haveTypeProperty = model.createProperty(Prefixes.HAVE_TYPE);
		Property haveGeoProperty = model.createProperty(Prefixes.HAVE_GEO);
		for (MyGeometry myGeometry : grid.getMyGeometrys()) {
			// 获得地理实体所属的类型-- 按照数据集名称区分的，同一个数据集的类型就是数据集名称
			String geometryType = myGeometry.getType();
			// 获得地理实体的id
			String geoEntityId = myGeometry.getDataSourceId() + Parameter.DATA_SOURCE_SET_SEGMENT
					+ myGeometry.getDataSetId() + Parameter.DATA_SOURCE_SET_SEGMENT + myGeometry.getPrimaryId();
			Resource geoTypeResource = model.createResource(Prefixes.GEO_TYPE_PREFIX + geometryType);
			Resource geoEntityResource = model.createResource(Prefixes.GEO_ENTITY_PREFIX + geoEntityId);

			// 构造statement，将其存入model
			model.add(gridResource, haveTypeProperty, geoTypeResource);
			model.add(geoTypeResource, haveGeoProperty, geoEntityResource);
		}
		return model;
	}

}
