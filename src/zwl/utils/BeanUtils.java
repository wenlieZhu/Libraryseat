package zwl.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanUtils {
	public static void copyProperties(Object targetObj,Object srcObj) throws Exception{
		Class srcClass=srcObj.getClass();
		Class targetClass=targetObj.getClass();
		Field[] targetFields=targetClass.getDeclaredFields();
		if(targetFields!=null && targetFields.length>0){
			for(Field targetField:targetFields){
				String targetFieldName=targetField.getName();
				Class targetFieldType=targetField.getType();
				String srcMethodName="get"+targetFieldName.substring(0, 1).toUpperCase()
						+targetFieldName.substring(1);
				Method srcMethod=srcClass.getDeclaredMethod(srcMethodName, null);
				if(srcMethod!=null){
					Object srcMethodvalue=srcMethod.invoke(srcObj, null);
					if(srcMethodvalue!=null){
						String tagetMethodName="set"+targetFieldName.substring(0, 1).toUpperCase()
								+targetFieldName.substring(1);
						Method targetMethod=targetClass.getDeclaredMethod(tagetMethodName, targetFieldType);
						if(targetMethod!=null){
							targetMethod.invoke(targetObj, srcMethodvalue);
						}
					}
				}
			}
		}
	}
}
