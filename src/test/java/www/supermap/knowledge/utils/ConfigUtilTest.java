package www.supermap.knowledge.utils;

import static org.junit.Assert.*;

import java.io.Writer;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import www.supermap.knowledge.beans.KnowledgeGraph;

public class ConfigUtilTest {

	@Test
	public void test() {
		System.out.println("3".equals(String.valueOf(3)));
	}

	@Test
	public void testFastJson() {
		//字符串转成json对象
		String jsonsds = "{\"name\":\"XiaoMing\"}";
		JSONObject obj1 = JSONObject.parseObject(jsonsds);
		
		//json对象转字符串
		JSON.toJSONString(obj1);

		//获取JSONObject中的键值对个数
        int size = obj1.size();
        
		//添加键值对
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("haha", 2);
		
//		//bean序列化
//		KnowledgeGraph know = new KnowledgeGraph("", 13);
//		String jsonStr = JSON.toJSONString(know);	
//		//bean的反序列化
//		KnowledgeGraph jsonknow= JSON.parseObject(jsonStr, KnowledgeGraph.class);
//		System.out.println(jsonknow);
	}
}
