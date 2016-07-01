/**
 * 
 */
package com.sa.pic.rest.framework;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	private static App instance;
	private ApplicationContext context;
	private transient Map<Class<?>, Object> instanceMap;

	/**
	 * 
	 */
	private App() {
		context = new ClassPathXmlApplicationContext("/app-config.xml", App.class);
	}

	public static App getInstance() {
		if (instance == null) {
			synchronized (App.class) {
				if (instance == null) {
					instance = new App();
				}
			}
		}
		return instance;
	}

	/**
	 * @return the context
	 */
	public ApplicationContext getContext() {
		return context;
	}

	/**
	 * @return the instanceMap
	 */
	public Map<Class<?>, Object> getInstanceMap() {
		if (instanceMap == null) {
			instanceMap = Collections.synchronizedMap(new HashMap<Class<?>, Object>());
		}
		return instanceMap;
	}
}
