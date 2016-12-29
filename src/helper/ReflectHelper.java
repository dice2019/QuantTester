package helper;

import java.lang.reflect.Field;

public final class ReflectHelper {
	public final static Object getPrivateField(final Object obj, final String field_name) {
		Field field = null;
		Object field_obj = null;
		try {
			field = obj.getClass().getDeclaredField(field_name);
			field.setAccessible(true);
			field_obj = field.get(obj);
		} catch (Exception e) {
			return null;
		}
		return field_obj;
	}

	public final static void setPrivateField(final Object obj, final String field_name, final Object value) {
		Field field = null;
		try {
			field = obj.getClass().getDeclaredField(field_name);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			return;
		}
	}
}
