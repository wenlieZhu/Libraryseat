package zwl.utils;

import java.text.SimpleDateFormat;
import java.text.ParseException;

public class StringUtils {
	public static boolean strIsNull(String str) {
		return str==null || str.isEmpty();
	}
	
	public static Integer str2Integer(String str,boolean isThrow){
		Integer defValue=null;
		if(strIsNull(str)){
			if(isThrow) throw new RuntimeException("字符串为空");
			return defValue;
		}
		try {
			return new Integer(str);
		} catch (NumberFormatException e) {
			if(isThrow)
				throw e;
		}
		return defValue;
	}
	
	public static Integer str2Integer(String str,boolean isThrow,Integer defValue){
		if(strIsNull(str)){
			if(isThrow) throw new RuntimeException("字符串为空");
			return defValue;
		}
		try {
			return new Integer(str);
		} catch (NumberFormatException e) {
			if(isThrow)
				throw e;
		}
		return defValue;
	}
	
	public static Double str2Double(String str,boolean isThrow){
		Double defValue=null;
		if(strIsNull(str)){
			if(isThrow) throw new RuntimeException("字符串为空");
			return defValue;
		}
		try {
			return new Double(str);
		} catch (NumberFormatException e) {
			if(isThrow)
				throw e;
		}
		return defValue;
	}
	
	public static java.sql.Date str2DateBySql(String str,boolean isThrow,String pattern) {
		java.sql.Date dd=null;
		if(strIsNull(str)){
			if(isThrow) throw new RuntimeException("字符串为空");
			return dd;
		}
		try {
			String tmpPattern="yyyy-MM-dd HH:mm:ss";
			if(!strIsNull(pattern)) tmpPattern=pattern;
			SimpleDateFormat sf=new SimpleDateFormat(tmpPattern);
			java.util.Date date1=sf.parse(str);
			return new java.sql.Date(date1.getTime());
		} catch (NumberFormatException | ParseException e) {
			if(isThrow)
				throw new RuntimeException(e.getMessage());
		}
		return dd;
	}
}
