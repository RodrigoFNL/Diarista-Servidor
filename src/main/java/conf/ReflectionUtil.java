package conf;

import java.lang.reflect.Method;

public class ReflectionUtil {

	public static Object execute(Object source, String methodName) throws Exception {
		if (source != null) {
			Class<? extends Object> c = source.getClass();
			Method m = c.getMethod(methodName);
			return m.invoke(source);
		}
		throw new RuntimeException("Reflection failed on null object");
	}

	public static Object execute(Object source, String methodName, Object param) throws Exception {
		if (param == null) {
			return execute(source, methodName);
		}
		if (source != null) {
			Class<? extends Object> c = source.getClass();
			Class<?> paramClass[] = { param.getClass() };
			Method m = c.getMethod(methodName, paramClass);
			return m.invoke(source, param);
		}
		throw new RuntimeException("Reflection failed on null object");
	}

	public static Object execute(Object source, String methodName, Object... params) throws Exception {
		if (params == null) {
			return execute(source, methodName);
		} else if (params.length == 1) {
			return execute(source, methodName, params[0]);
		}
		if (source != null) {
			Class<? extends Object> c = source.getClass();
			Class<?> paramClasses[] = new Class[params.length];
			for (int i = 0; i < params.length; i++) {
				if (params[i] == null)
					throw new RuntimeException("Can't get class of null param");
				paramClasses[i] = params[i].getClass();
			}
			Method method = c.getMethod(methodName, paramClasses);
			return method.invoke(source, params);
		}
		throw new RuntimeException("Reflection failed on null object");
	}

}