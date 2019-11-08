package www.supermap.knowledge.service.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import www.supermap.knowledge.utils.ConfigUtil;

public class KnowledgeServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(KnowledgeServiceImplTest.class);
	
	@Test
	public void testAddAllDataSet() {
		String path1 = KnowledgeServiceImplTest.class.getClassLoader().getResource("\\").getPath();
//		System.out.println(path1);
//		String path = "C:\\sdsdsd\\dfdf\\dfasd\\aswerg\\123.udb";
//		String s = "\\\\";
//		System.out.println(s);
//		String[] a = path.split(s);
//		for (String string : a) {
//			System.out.println(string);
//		}
//		System.out.println(a[a.length-1]);
		String jsonStr = "{\"data\":{\"pow\":100,\"net\":99,\"dev\":69},\"success\":true,\"message\":\"成功\"}";
		JSONObject obj = JSONObject.parseObject(jsonStr);
//		JSONArray a = JSON.parseArray(jsonStr);
		obj.getJSONObject("data").put("dev", 40);
//		System.out.println(obj.getJSONObject("data"));
		String jsonsds = "";
		JSONObject obj1 = JSONObject.parseObject(jsonsds);
		System.out.println(obj1);
		
	}
	
	@Test
	/**
	 * 可以直接将空字符串或者空文件读取出来，转成空json对象
	 */
	public void test2(){
		String path = "config\\haha.json";
		File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String jsonString = new ConfigUtil().getInstance().readJsonFile(path);
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		if(jsonObject == null){
			logger.debug("json文件转换后的对象为空");
		}
	}

}
