package jp.co.infitech.java.astah;

import java.util.Locale;
import java.util.ResourceBundle;

public class HtmlWord {
	private static ResourceBundle RESOURCE_HTMLWORD = ResourceBundle.getBundle("htmlword", Locale.getDefault(), HtmlWord.class.getClassLoader());
	/** テスト用 html.properties読込*/
//	private static ResourceBundle RESOURCE_HTMLWORD = ResourceBundle.getBundle("htmlword", new Locale(""), HtmlWord.class.getClassLoader());
	
	public static String getWord(String key) {
		String result = RESOURCE_HTMLWORD == null ? "" : RESOURCE_HTMLWORD.getString(key); 
		System.out.println("[Key:" + key + "][Value:" + result + "]");
		return result;
	}
}
