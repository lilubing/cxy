package com.llb.cxy.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.llb.cxy.core.SystemContext;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串常用方法
 *
 * @ClassName: MyStringUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author LiLuBing
 * @date 2016年1月12日 上午8:39:41
 *
 */
public class MyStringUtils extends StringUtils {

    private static final char SEPARATOR = '_';
    private static final String CHARSET = "UTF-8";

    /**
     *
     * <B>方法名称：数字转 sql in 条件</B><BR>
     * <B>概要说明：ids="1,2,3,4,5,6"</B><BR>
     * @param ids
     * @return "1,2,3,4,5,6"
     */
    public static String LongToSqlIn(List<Long> ids) {
        if(ids.isEmpty()) {
            return "";
        }
        StringBuilder sbf = new StringBuilder();
        for (Object id : ids) {
            if(null != id && "" != id)
                sbf.append(id + ",");
        }
        String ids_sql = sbf.toString();
        if(!ids_sql.isEmpty()) {
            ids_sql = ids_sql.substring(0, ids_sql.length() - 1);
        }
        return ids_sql;
    }

    /**
     *
     * <B>方法名称：数字转 sql in 条件</B><BR>
     * <B>概要说明：ids="1,2,3,4,5,6"</B><BR>
     * @param ids
     * @return "1,2,3,4,5,6"
     */
    public static String LongToSqlIn(String ids) {
        if(StringUtils.isBlank(ids)) {
            return "";
        }
        StringBuilder sbf = new StringBuilder();
        String[] arr = ids.split(",");
        for (String id : arr) {
            if(null != id && "" != id)
                sbf.append(id + ",");
        }
        String ids_sql = sbf.toString();
        if(!ids_sql.isEmpty()) {
            ids_sql = ids_sql.substring(0, ids_sql.length() - 1);
        }
        return ids_sql;
    }

    /**
     *
     * <B>方法名称： 字符串转 sql in 条件</B><BR>
     * <B>概要说明：ids="1,2,3,4,5,6"</B><BR>
     * @param ids
     * @return "'1','2','3','4','5','6'"
     */
    public static String StringToSqlIn(String ids) {
        if(StringUtils.isBlank(ids)) {
            return "";
        }
        StringBuilder sbf = new StringBuilder();
        String[] arr = ids.split(",");
        for (String id : arr) {
            if(null != id && "" != id)
                sbf.append("'" + id + "',");
        }
        String ids_sql = sbf.toString();
        if(!ids_sql.isEmpty()) {
            ids_sql = ids_sql.substring(0, ids_sql.length() - 1);
        }
        return ids_sql;
    }

    /**
     * 根据字段名获取值
     * @Title: getValueByName
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author LiLuBing
     * @param conditions
     * @param parameName
     * @return
     * @throws
     */
    public static String getValueByName(String conditions, String parameName) {
        if (null != conditions && "" != conditions) {

            String[] parameters = conditions.split(",");
            for (String parameter : parameters) {

                String[] val = parameter.split("-\\$");
                String _field = val[0];
                // 需要注意数据类型
                if (val[1].equals(parameName)
                        && (null != _field && "" != _field)) {
                    return val[0];
                }

            }
        }
        return "";
    }

    /**
     * 获取第一个角色ID
     * @Title: getFirstRoleId
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author LiLuBing
     * @return
     * @throws
     */
    public static Long getFirstRoleId() {
        /*String rid = SystemContext.getUserinfo().getRoleids().split(",")[0];
        if(null != rid && "" != rid) {
            return Long.valueOf(rid);
        }*/
        JSONArray authorities = SystemContext.getUserInfo().getJSONArray("authorities");
        if(!authorities.isEmpty()) {
            return authorities.getLong(0);
        }
        return 0L;
    }

    public static String getCreateRoleSql() {
        String result = "";
        Long firstRoleId = getFirstRoleId();
        //判断是否是超级管理员
        if(110 == firstRoleId) {
            //如果是超级管理员就额外加上 CREATEROLEID = '1'
            result += " (T.createroleid = '" + firstRoleId + "' OR "
                    + "T.createroleid = '1')";
        } else {
            result += " T.createroleid = '" + firstRoleId + "' ";
        }
        return result;
    }

    /**
     * 设置下载文件中文件的名称
     *
     * @param filename
     * @param request
     * @return
     */
    public static String encodeFilename(String filename,
                                        HttpServletRequest request) {
        /**
         * 获取客户端浏览器和操作系统信息 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE
         * 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1;
         * zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");

                newFileName = new String(newFileName).replace("+", "%20");;
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"),
                            "ISO8859-1");
                    newFileName = new String(newFileName).replace(" ", "%20");
                }
                return newFileName;
            }
			/*if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
				return MimeUtility.encodeText(filename, "UTF-8", "B");*/

            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }

    /**
     * 替换特殊字符串，window 创建文件夹用
     * @Title: replaceSpecialStr
     * @Description: TODO(替换:\\/?*\<>|)
     * @author LiLuBing
     * @param sourceStr
     * @return
     * @throws
     */
    public static String replaceSpecialStr(String sourceStr) {
        String special_str = ",：,:,\\\\,/,\\?,\\*,<,>,\\|";
        String[] special_arr = special_str.split(",");
        for (String special : special_arr) {
            sourceStr = sourceStr.replaceAll(special, "");
        }
        return sourceStr;
    }

    /**
     * 一次性判断多个或单个对象为空。
     * @param objects
     * @author zhou-baicheng
     * @return 只要有一个元素为Blank，则返回true
     */
    public static boolean isBlank(Object...objects){
        Boolean result = false ;
        for (Object object : objects) {
            if(null == object || "".equals(object.toString().trim())
                    || "null".equals(object.toString().trim())){
                result = true ;
                break ;
            }
        }
        return result ;
    }

    public static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val.toLowerCase();
    }

    /**
     * 一次性判断多个或单个对象不为空。
     * @param objects
     * @author zhou-baicheng
     * @return 只要有一个元素不为Blank，则返回true
     */
    public static boolean isNotBlank(Object...objects){
        return !isBlank(objects);
    }
    public static boolean isBlank(String...objects){
        Object[] object = objects ;
        return isBlank(object);
    }
    public static boolean isNotBlank(String...objects){
        Object[] object = objects ;
        return !isBlank(object);
    }

    public static boolean isBlank(String str){
        Object object = str ;
        return isBlank(object);
    }

    public static boolean isNotBlank(String str){
        Object object = str ;
        return !isBlank(object);
    }

    /**
     * 判断一个字符串在数组中存在几个
     * @param baseStr
     * @param strings
     * @return
     */
    public static int indexOf(String baseStr,String[] strings){

        if(null == baseStr || baseStr.length() == 0 || null == strings)
            return 0;

        int i = 0;
        for (String string : strings) {
            boolean result = baseStr.equals(string);
            i = result ? ++i : i;
        }
        return i ;
    }

    /**
     * 判断一个字符串是否为JSONObject,是返回JSONObject,不是返回null
     * @param args
     * @return
     */
    public static JSONObject isJSONObject(String args) {
        JSONObject result = null;
        if(isBlank(args)){
            return result ;
        }
        try {
            return JSONObject.parseObject(args.trim());
        } catch (Exception e) {
            return result ;
        }
    }

    /**
     * 判断一个字符串是否为JSONArray,是返回JSONArray,不是返回null
     * @param args
     * @return
     */
    public static JSONArray isJSONArray(Object args) {
        JSONArray result = new JSONArray();
        if(isBlank(args)){
            return null ;
        }
        if(args instanceof JSONArray){
            JSONArray arr = (JSONArray) args;
            for (Object json : arr) {
                if(json != null && json instanceof JSONObject){
                    result.add(json);
                    continue;
                }else{
                    result.add((JSONObject) json);
                }
            }
            return result;
        }else{
            return null ;
        }

    }

    public static String trimToEmpty(Object str){
        return (isBlank(str) ? "" : str.toString().trim());
    }

    /**
     * 将 Strig  进行 BASE64 编码
     * @param str [要编码的字符串]
     * @param bf  [true|false,true:去掉结尾补充的'=',false:不做处理]
     * @return
     * @throws Exception
     */
    public static String getBASE64(String str,boolean...bf) throws Exception {
        if (null != str && "" != str) {
            return null;
        }
        String base64 = Base64Util.encodeBuffer(str);
        //去掉 '='
        if(isBlank(bf) && bf[0]){
            base64 = base64.replaceAll("=", "");
        }
        return base64;
    }


    /**
     * 把Map转换成get请求参数类型,如 {"name"=20,"age"=30} 转换后变成 name=20&age=30
     * @param map
     * @return
     */
    public static String mapToGet(Map<? extends Object,? extends Object> map){
        String result = "" ;
        if(map == null || map.size() ==0){
            return result ;
        }
        Set<? extends Object> keys = map.keySet();
        for (Object key : keys ) {
            result += ((String)key + "=" + (String)map.get(key) + "&");
        }

        return isBlank(result) ? result : result.substring(0,result.length() - 1);
    }

    /**
     * 把一串参数字符串,转换成Map 如"?a=3&b=4" 转换为Map{a=3,b=4}
     * @param args
     * @return
     */
    public static Map<String, ? extends Object> getToMap(String args){
        if(isBlank(args)){
            return null ;
        }
        args = args.trim();
        //如果是?开头,把?去掉
        if(args.startsWith("?")){
            args = args.substring(1,args.length());
        }
        String[] argsArray = args.split("&");

        Map<String,Object> result = new HashMap<String,Object>();
        for (String ag : argsArray) {
            if(!isBlank(ag) && ag.indexOf("=")>0){

                String[] keyValue = ag.split("=");
                //如果value或者key值里包含 "="号,以第一个"="号为主 ,如  name=0=3  转换后,{"name":"0=3"}, 如果不满足需求,请勿修改,自行解决.

                String key = keyValue[0];
                String value = "" ;
                for (int i = 1; i < keyValue.length; i++) {
                    value += keyValue[i]  + "=";
                }
                value = value.length() > 0 ? value.substring(0,value.length()-1) : value ;
                result.put(key,value);

            }
        }

        return result ;
    }

    /**
     * 转换成Unicode
     * @param str
     * @return
     */
    public static String toUnicode(String str) {
        String as[] = new String[str.length()];
        String s1 = "";
        for (int i = 0; i < str.length(); i++) {
            int v = str.charAt(i);
            if(v >=19968 && v <= 171941){
                as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
                s1 = s1 + "\\u" + as[i];
            }else{
                s1 = s1 + str.charAt(i);
            }
        }
        return s1;
    }
    /**
     * 合并数据
     * @param v
     * @return
     */
    public static String merge(Object...v){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < v.length; i++) {
            sb.append(v[i]);
        }
        return sb.toString() ;
    }
    /**
     * 字符串转urlcode
     * @param value
     * @return
     */
    public static String strToUrlcode(String value){
        try {
            value = URLEncoder.encode(value,"utf-8");
            return value ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * urlcode转字符串
     * @param value
     * @return
     */
    public static String urlcodeToStr(String value){
        try {
            value = java.net.URLDecoder.decode(value,"utf-8");
            return value ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断字符串是否包含汉字
     * @param txt
     * @return
     */
    public static Boolean containsCN(String txt){
        if(isBlank(txt)){
            return false;
        }
        for (int i = 0; i < txt.length(); i++) {
            String bb = txt.substring(i, i + 1);
            boolean cc = Pattern.matches("[\u4E00-\u9FA5]", bb);
            if(cc)
                return cc ;
        }
        return false;
    }

    /**
     * 去掉HTML代码
     * @param news
     * @return
     */
    public static String removeHtml(String news) {
        String s = news.replaceAll("amp;", "").replaceAll("<","<").replaceAll(">", ">");

        Pattern pattern = Pattern.compile("<(span)?\\sstyle.*?style>|(span)?\\sstyle=.*?>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        String str = matcher.replaceAll("");

        Pattern pattern2 = Pattern.compile("(<[^>]+>)",Pattern.DOTALL);
        Matcher matcher2 = pattern2.matcher(str);
        String strhttp = matcher2.replaceAll(" ");

        String regEx = "(((http|https|ftp)(\\s)*((\\:)|：))(\\s)*(//|//)(\\s)*)?"
                + "([\\sa-zA-Z0-9(\\.|．)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|．)&%\\$\\-]+)*@(\\s)*)?"
                + "("
                + "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
                + "|([\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*[\\sa-zA-Z]*"
                + ")"
                + "((\\s)*(\\:)|(：)(\\s)*[0-9]+)?"
                + "(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";
        Pattern p1 = Pattern.compile(regEx,Pattern.DOTALL);
        Matcher matchhttp = p1.matcher(strhttp);
        String strnew = matchhttp.replaceAll("").replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");


        Pattern patterncomma = Pattern.compile("(&[^;]+;)",Pattern.DOTALL);
        Matcher matchercomma = patterncomma.matcher(strnew);
        String strout = matchercomma.replaceAll(" ");
        String answer = strout.replaceAll("[\\pP‘’“”]", " ")
                .replaceAll("\r", " ").replaceAll("\n", " ")
                .replaceAll("\\s", " ").replaceAll("　", "");


        return answer;
    }

    /**
     * 把数组的空数据去掉
     * @param array
     * @return
     */
    public static List<String> array2Empty(String[] array){
        List<String> list = new ArrayList<String>();
        for (String string : array) {
            if(null != string && "" != string){
                list.add(string);
            }
        }
        return list;
    }

    /**
     * 把数组转换成set
     * @param array
     * @return
     */
    public static Set<?> array2Set(Object[] array) {
        Set<Object> set = new TreeSet<Object>();
        for (Object id : array) {
            if(null != id){
                set.add(id);
            }
        }
        return set;
    }

    /**
     * serializable toString
     * @param serializable
     * @return
     */
    public static String toString(Serializable serializable) {
        if(null == serializable){
            return null;
        }
        try {
            return (String)serializable;
        } catch (Exception e) {
            return serializable.toString();
        }
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 转换为字节数组
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes(CHARSET);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 转换为字节数组
     *
     * @return
     */
    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET);
        } catch (UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    /**
     * 转换为Boolean类型
     * 'true', 'on', 'y', 't', 'yes' or '1' (case insensitive) will return true. Otherwise, false is returned.
     */
    public static Boolean toBoolean(final Object val) {
        if (val == null) {
            return false;
        }
        return BooleanUtils.toBoolean(val.toString()) || "1".equals(val.toString());
    }

    /**
     * 如果对象为空，则使用defaultVal值
     * see: ObjectUtils.toString(obj, defaultVal)
     *
     * @param obj
     * @param defaultVal
     * @return
     */
    public static String toString(final Object obj, final String defaultVal) {
        return obj == null ? defaultVal : obj.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String ellipsis(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * Html 转码.
     */
    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * Html 解码.
     */
    public static String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * Xml 转码.
     */
    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml11(xml);
    }

    /**
     * Xml 解码.
     */
    public static String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * url追加参数
     *
     * @param url   传入的url ex："http://exp.kunnr.com/so/index.html?kunnrId=16&userProfile=16#/app/home"
     * @param name  参数名
     * @param value 参数值
     * @return
     * @author: xg.chen
     * @date:2016年9月2日
     */
    public static String appendURIParam(String url, String name, String value) {
        url += (url.indexOf('?') == -1 ? '?' : '&');
        url += EncodeUtils.encodeUrl(name) + '=' + EncodeUtils.encodeUrl(value);
        return url;
    }

    /**
     * 组装新的URL
     *
     * @param url
     * @param map
     * @return
     */
    public static String appendURIParam(String url, Map<String, String> map) {
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            url = appendURIParam(url, entry.getKey(), entry.getValue());
        }
        return url;
    }


    /**
     * 驼峰转下划线
     * createTime > create_time
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(SEPARATOR);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     * create_time > createTime
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile(String.valueOf(SEPARATOR)).matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 格式化存储单位
     *
     * @param size byte 字节
     * @return
     */
    public static String formatBytes(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        int bytes = 1024;
        if (size < bytes) {
            return String.valueOf(size) + "Byte";
        } else {
            size = size / bytes;
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位 //因为还没有到达要使用另一个单位的时候 //接下去以此类推
        if (size < bytes) {
            return String.valueOf(size) + "K";
        } else {
            size = size / bytes;
        }
        if (size < bytes) {
            // 因为如果以MB为单位的话，要保留最后1位小数， //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "M";
        } else { // 否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / bytes;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "G";
        }
    }

    /**
     * 匿名手机号
     *
     * @param mobile
     * @return 152****4799
     */
    public static String formatMobile(String mobile) {

        if (isEmpty(mobile)) {
            return null;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 匿名银行卡号
     *
     * @param bankCard
     * @return
     */
    public static String formatBankCard(String bankCard) {
        if (isEmpty(bankCard)) {
            return null;
        }
        return bankCard.replaceAll("(\\d{5})\\d{5}\\d{2}(\\d{4})", "$1****$2");
    }

    /**
     * 匿名身份证
     *
     * @param idCard
     * @return 4304*****7733
     */
    public static String formatIdCard(String idCard) {

        if (isEmpty(idCard)) {
            return null;
        }
        return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
    }

    /**
     * 检测是否未手机号
     * 中国电信号段
     * 133、149、153、173、177、180、181、189、199
     * 中国联通号段
     * 130、131、132、145、155、156、166、175、176、185、186
     * 中国移动号段
     * 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     * 其他号段
     * 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
     * 虚拟运营商
     * 电信：1700、1701、1702
     * 移动：1703、1705、1706
     * 联通：1704、1707、1708、1709、171
     *
     * @param mobile
     * @return
     */
    public static boolean matchMobile(String mobile) {
        if (mobile == null) {
            return false;
        }
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 检测Email
     *
     * @param email
     * @return
     */
    public static boolean matchEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }


    /**
     * 检测域名
     * @param domain
     * @return
     */
    public static boolean matchDomain(String domain) {
        if (domain == null) {
            return false;
        }
        String regex = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
        return Pattern.matches(regex, domain);
    }

    /**
     * 检测IP
     * @param ip
     * @return
     */
    public static boolean matchIp(String ip) {
        if (ip == null) {
            return false;
        }
        String regex = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
        return Pattern.matches(regex, ip);
    }

    /**
     * 检测HttpUrl
     * @param url
     * @return
     */
    public static boolean matchHttpUrl(String url) {
        if (url == null) {
            return false;
        }
        String regex = "^(?=^.{3,255}$)(http(s)?:\\/\\/)?(www\\.)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\\d+)*(\\/\\w+\\.\\w+)*([\\?&]\\w+=\\w*)*$";
        return Pattern.matches(regex, url);
    }

    /**
     * 校验银行卡卡号
     * 校验过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     */
    public static boolean matchBankCard(String bankCard) {
        if (bankCard == null) {
            return false;
        }
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeBankCard
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }


    /**
     * 处理非法字符
     */
    private static List<Pattern> patterns = null;

    private static List<Object[]> getXssPatternList() {
        List<Object[]> ret = new ArrayList<Object[]>();
        ret.add(new Object[]{"<(no)?script[^>]*>.*?</(no)?script>", Pattern.CASE_INSENSITIVE});
        ret.add(new Object[]{"eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"(javascript:|vbscript:|view-source:)*", Pattern.CASE_INSENSITIVE});
        ret.add(new Object[]{"<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatuschange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        return ret;
    }

    private static List<Pattern> getPatterns() {

        if (patterns == null) {

            List<Pattern> list = new ArrayList<Pattern>();

            String regex = null;
            Integer flag = null;
            int arrLength = 0;

            for (Object[] arr : getXssPatternList()) {
                arrLength = arr.length;
                for (int i = 0; i < arrLength; i++) {
                    regex = (String) arr[0];
                    flag = (Integer) arr[1];
                    list.add(Pattern.compile(regex, flag));
                }
            }

            patterns = list;
        }

        return patterns;
    }

    public static String stripXss(String value) {
        if (StringUtils.isNotBlank(value)) {
            Matcher matcher = null;
            for (Pattern pattern : getPatterns()) {
                matcher = pattern.matcher(value);
                if (matcher.find()) {
                    value = matcher.replaceAll("");
                }
            }
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
        return value;
    }

    /**
     * 密码强度
     *
     * @return Z = 字母 S = 数字 T = 特殊字符
     */
    public static String checkPassword(String passwordStr) {
        String regexZ = "\\d*";
        String regexS = "[a-zA-Z]+";
        String regexT = "\\W+$";
        String regexZT = "\\D*";
        String regexST = "[\\d\\W]*";
        String regexZS = "\\w*";
        String regexZST = "[\\w\\W]*";

        if (passwordStr.matches(regexZ)) {
            return "弱";
        }
        if (passwordStr.matches(regexS)) {
            return "弱";
        }
        if (passwordStr.matches(regexT)) {
            return "弱";
        }
        if (passwordStr.matches(regexZT)) {
            return "中";
        }
        if (passwordStr.matches(regexST)) {
            return "中";
        }
        if (passwordStr.matches(regexZS)) {
            return "中";
        }
        if (passwordStr.matches(regexZST)) {
            return "强";
        }
        return passwordStr;
    }


    /**
     * 将 Exception 转化为 String
     */
    public static String getExceptionToString(Throwable e) {
        if (e == null){
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static void main(String[] args) {
        System.out.println(MyStringUtils.matchDomain("8366419@qq.com"));
        System.out.println(MyStringUtils.matchDomain("www.qq.com"));
        System.out.println(MyStringUtils.matchDomain("qq.com"));
        System.out.println(MyStringUtils.matchIp("qq.com"));
        System.out.println(MyStringUtils.matchIp("192.168.0.1"));
//        System.out.println("test");
//        System.out.println(checkPassword("f0a2adfdf56241bf839d714f7f74f4d1"));
     /*   String value = null;
        value = stripXss("<script language=text/javascript>alert(document.cookie);</script>");
        System.out.println("type-1: '" + value + "'");

        value = stripXss("<script src='' onerror='alert(document.cookie)'></script>");
        System.out.println("type-2: '" + value + "'");

        value = stripXss("</script>");
        System.out.println("type-3: '" + value + "'");

        value = stripXss(" eval(abc);");
        System.out.println("type-4: '" + value + "'");

        value = stripXss(" expression(abc);");
        System.out.println("type-5: '" + value + "'");

        value = stripXss("<img src='' onerror='alert(document.cookie);'></img>");
        System.out.println("type-6: '" + value + "'");

        value = stripXss("<img src='' onerror='alert(document.cookie);'/>");
        System.out.println("type-7: '" + value + "'");

        value = stripXss("<img src='' onerror='alert(document.cookie);'>");
        System.out.println("type-8: '" + value + "'");

        value = stripXss("<script language=text/javascript>alert(document.cookie);");
        System.out.println("type-9: '" + value + "'");

        value = stripXss("<script>window.location='url'");
        System.out.println("type-10: '" + value + "'");

        value = stripXss(" onload='alert(\"abc\");");
        System.out.println("type-11: '" + value + "'");

        value = stripXss("<img src=x<!--'<\"-->>");
        System.out.println("type-12: '" + value + "'");

        value = stripXss("<=img onstop=");
        System.out.println("type-13: '" + value + "'");*/

        // System.out.println(formatBytes(1222));
        //System.out.println(underlineToCamel("create_time"));
    }
}