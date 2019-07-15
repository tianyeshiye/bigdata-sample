package sample.tianye.tool.common.utils;

import com.alibaba.fastjson.JSON;

import sample.tianye.tool.common.utils.bean.TestBean;

public class JsonUtils {

	public static TestBean convertToBean(String jsonStr) {

		TestBean bean = JSON.parseObject(jsonStr, TestBean.class);

		return bean;
	}

	public static <T> T convertToBean(String jsonStr, Class<T> clazz) {

		T bean = JSON.parseObject(jsonStr, clazz);

		return bean;
	}
}
