package www.supermap.knowledge.service.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class KnowledgeServiceImplTest {

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
	}

}
