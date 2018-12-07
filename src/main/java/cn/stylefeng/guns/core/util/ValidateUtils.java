package cn.stylefeng.guns.core.util;

import cn.stylefeng.roses.core.util.ToolUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证字符串是否是数字
 * 
 * @author kimi
 * @dateTime 2012-6-20 下午2:54:45
 */
public class ValidateUtils {

	/**
	 * 判断是否是数字
	 * 
	 * @author kimi
	 * @dateTime 2012-6-20 下午2:53:36
	 * @param str
	 * @return true表示是数字，false表示不是数字
	 */
	public static boolean isNumeric(String str) {
		if (ToolUtil.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	/**
	 * 校验手机号(简易校验)
	* @author chenbiao
	* @date 2017年2月23日 上午10:57:46
	* 参数说明 
	* 
	* @param str
	* @return
	 */
	public static boolean isMobile(String str) {
		if (!isNumeric(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^1[3,4,5,6,7,8,9][0-9]{9}$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 是否为邮箱
	 */
	public static boolean isMail(String mail) {
		if (!isEmpty(mail)) {
			String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
			return matText(regex, mail);
		}
		return false;
	}
	
	/**
	 * 是否是图片后缀
	 * @param imgPath
	 * @return
	 */
	public static boolean isImage(String imgPath) {
		if (!isEmpty(imgPath)) {
			imgPath = imgPath.toLowerCase();
			String regex = ".*[^a][^b][^c]\\.(?:png|jpg|bmp|gif|jpeg)";
			return matText(regex, imgPath);
		}
		return false;
	}

	/**
	 * 判断是否为金额，保留2为小数
	 */
	public static boolean isMoney(String money) {
		if (!isEmpty(money)) {
			Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
			Matcher match = pattern.matcher(money);
			return match.matches();
		}
		return  false;
	}
	
	/**
	 * 字符串是否为空
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
	
	/**
     * 密码
     *
     * @param password
     *
     * @return
     */
    public static boolean validatePassword(String password) {
        return matText("^[_0-9a-zA-Z]{6,20}$",password);
    }
    /**
     * 校验图片后缀是否是图片格式
    * @author chenbiao
    * @date 2017年7月15日 下午5:54:18
    * 参数说明 
    * 
    * @param fileName
    * @return
     */
    public static boolean validateImageBuffix(String fileName){
    	String buffix = fileName.substring(fileName.lastIndexOf("."));
    	return matText("(.JPEG|.JPG|.GIF|.BMP|.PNG|.WEBP|.)$", buffix.toUpperCase());
    }
	
	private static boolean matText(String expression, String text) {
		Pattern p = Pattern.compile(expression);
		Matcher m = p.matcher(text);
		boolean b = m.matches();
		return b;
	}
}
