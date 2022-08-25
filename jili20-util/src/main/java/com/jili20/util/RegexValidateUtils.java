package com.jili20.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用正则表达式进行表单验证
 *
 * @author bing_  @create 2022/1/8-23:33
 */
public class RegexValidateUtils {


    /**
     * 检查 - 邮箱地址 - 正则
     */
    public static final String emailRegEx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static boolean checkEmailAddressOk(String emailAddress) {
        Pattern p = Pattern.compile(emailRegEx);
        Matcher m = p.matcher(emailAddress);
        return m.matches();
    }

    /**
     * 校验 - 手机号码
     * <p>
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static final String phoneRegEx = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";

    public static boolean checkCellphone(String cellphone) {
        Pattern p = Pattern.compile(phoneRegEx);
        Matcher m = p.matcher(cellphone);
        return m.matches();
    }

    // ====================== 校验：用户名、密码、url ============================>


    /**
     * 校验 - 用户名
     * 限 1 到 20个字符，支持中英文、数字、减号或下划线
     */
    public static final String usernameRegEx = "^[\\u4e00-\\u9fa5_a-zA-Z0-9-@.]{1,20}$";

    public static boolean checkUsername(String username) {
        Pattern p = Pattern.compile(usernameRegEx);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    /**
     * 校验 - 密码 - （6-20位字母、数字、中划线、下划线）
     */
    public static final String pwdRegEx = "^[0-9A-Za-z-_]{6,20}$";

    public static boolean checkPwd(String pwd) {
        if (StringUtils.isEmpty(pwd)) {
            return Boolean.FALSE;
        }
        return Pattern.matches(pwdRegEx, pwd);
    }


    /**
     * 校验 - URL规则，http、www、ftp
     */
    public static final String urlRegEx = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    public static boolean checkUrl(final String url) {
        if (StringUtils.isEmpty(url)) {
            return Boolean.FALSE;
        }
        return url.matches(urlRegEx);
    }


    /**
     * 校验 - 字符串长度 - 限 1 个字符
     * 支持中英文、数字、减号或下划线、/、:
     */
    public static final String length1RegEx = "^[0-9]{1}$";

    public static boolean checkLength1(String text) {
        Pattern p = Pattern.compile(length1RegEx);
        Matcher m = p.matcher(text);
        return m.matches();
    }


}
