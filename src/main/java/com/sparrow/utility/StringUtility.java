/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sparrow.utility;

import com.sparrow.constant.CONFIG;
import com.sparrow.constant.CONFIG_KEY_LANGUAGE;
import com.sparrow.constant.CONSTANT;
import com.sparrow.constant.magic.CHAR_SYMBOL;
import com.sparrow.constant.magic.SYMBOL;
import com.sparrow.container.Container;
import com.sparrow.core.spi.ApplicationContext;
import com.sparrow.core.TypeConverter;
import com.sparrow.core.Pair;
import com.sparrow.cg.MethodAccessor;
import com.sparrow.cryptogram.ThreeDES;
import com.sparrow.support.Entity;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author harry
 */
public class StringUtility {

    public static String newUuid() {
        return UUID.randomUUID().toString().replace(SYMBOL.HORIZON_LINE, SYMBOL.EMPTY);
    }

    /**
     * @param array
     * @param key
     * @return
     */
    public static boolean existInArray(Object[] array, Object key) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (Object s : array) {
            if (s == null) {
                continue;
            }
            if (s.toString().trim().equalsIgnoreCase(key.toString().trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean existInArray(String array, String key) {
        return existInArray(array.split(","), key);
    }

    /**
     * 拆分数组的关键字编码 例如:key1:value1,key2:value2 先对边界字进行编码,以防原码有出来边界字符而无法显示 该函数代码顺序不能变
     *
     * @param str 需要折分的字符串 格式如:key1:value1,key2:value2
     * @param onlyDot
     * @return
     */
    public static String encodeSplitKey(String str, boolean onlyDot) {
        if (str.contains(SYMBOL.POUND_SIGN)) {
            str = str.replace(SYMBOL.POUND_SIGN, "#limit");
        }

        if (!onlyDot) {
            if (str.contains(SYMBOL.COLON)) {
                str = str.replace(SYMBOL.COLON, "#colon#");
            }
        }

        if (str.contains(SYMBOL.COMMA)) {
            str = str.replace(SYMBOL.COMMA, "#dot#");
        }
        return str;
    }

    /**
     * 对split拆分数组的关键字解码 解码过程与编码过程相反
     *
     * @param str
     * @param onlyDot
     * @return
     */
    public static String decodeSplitKey(String str, boolean onlyDot) {
        if (!onlyDot) {
            if (str.contains("#colon#")) {
                str = str.replace("#colon#", SYMBOL.COLON);
            }
        }
        if (str.contains("#dot#")) {
            str = str.replace("#dot#", SYMBOL.COMMA);
        }

        if (str.contains("#limit")) {
            str = str.replace("#limit", SYMBOL.POUND_SIGN);
        }
        return str;
    }

    /**
     * 按字节剪切字符串
     *
     * @param str
     * @param len
     * @return
     */
    public static String subStringByByte(String str, int len) {
        if (len <= -1) {
            return str;
        }
        return subStringByByte(str, len, "...");
    }

    /**
     * 按字节剪切字符串，超过长度显示elide
     *
     * @param str
     * @param len
     * @param elide
     * @return
     */
    public static String subStringByByte(String str, int len, String elide) {
        if (str == null) {
            return SYMBOL.EMPTY;
        }
        int strLen = length(str);
        if (len >= strLen || len < 1) {
            return str;
        }
        char[] charArray = str.toCharArray();
        int length = 0;
        StringBuilder descBuilder = new StringBuilder();
        for (char c : charArray) {
            try {
                length += String.valueOf(c).getBytes(CONSTANT.CHARSET_UTF_8).length;
            } catch (UnsupportedEncodingException ignore) {
            }
            if (length > len) {
                break;
            }
            descBuilder.append(c);
        }
        return descBuilder.toString() + elide.trim();
    }

    public static int length(String str) {
        if (isNullOrEmpty(str)) {
            return 0;
        }
        try {
            return str.getBytes(CONSTANT.CHARSET_UTF_8).length;
        } catch (UnsupportedEncodingException ignore) {
            return 0;
        }
    }

    /**
     * 获取字符串中的img标签
     *
     * @param str
     * @return
     */
    public static String getImageUrlFromString(String str) {
        str = str.toLowerCase();
        int a = str.indexOf("<img");
        int b = str.substring(a).indexOf(">");
        String imageURL = str.substring(a, b);
        a = imageURL.indexOf("src=\"");
        b = imageURL.lastIndexOf("\"");
        imageURL = imageURL.substring(a + 5, b - a - 5).trim();
        return imageURL;
    }

    /**
     * 通过get方法获取字段名称
     *
     * @param getMethod
     * @return
     */
    public static String getFieldByGetMethod(String getMethod) {
        return getMethod.substring("get".length());
    }

    /**
     * 通过set方法获取字段名称
     *
     * @param setMethod
     * @return
     */
    public static String getFieldBySetMethod(String setMethod) {
        return getFieldByGetMethod(setMethod);
    }

    /**
     * 通过field获取其set方法
     *
     * @param beanName
     * @return
     */
    public static String getSetMethodNameByField(String beanName) {
        return "set" + setFirstByteUpperCase(beanName.trim());
    }

    /**
     * 通过field获取其get方法
     *
     * @param beanName
     * @return
     */
    public static String getGetMethodNameByField(String beanName) {
        return "get" + setFirstByteUpperCase(beanName.trim());
    }

    /**
     * 设置首字母大写
     *
     * @param srcString
     * @return
     */
    public static String setFirstByteUpperCase(String srcString) {
        if (srcString == null || srcString.length() == 0) {
            return SYMBOL.EMPTY;
        }
        char[] s = srcString.toCharArray();
        int firstCase = s[0];
        if (firstCase >= 'a' && firstCase <= 'z') {
            firstCase -= 32;
            s[0] = (char) firstCase;
        }
        return new String(s);
    }

    /**
     * 设置首字母小写
     *
     * @param srcString
     * @return
     */
    public static String setFirstByteLowerCase(String srcString) {
        if (srcString == null || srcString.length() == 0) {
            return SYMBOL.EMPTY;
        }
        char[] s = srcString.toCharArray();
        int firstCase = s[0];
        if (firstCase >= 'A' && firstCase <= 'Z') {
            firstCase += 32;
            s[0] = (char) firstCase;
        }
        return new String(s);
    }

    public static String getEntityNameByClass(Class entity) {
        String entityName = entity.getSimpleName();
        String toLowerCase = entityName.substring(0, 2);
        return entityName.replaceFirst(toLowerCase, toLowerCase.toLowerCase());
    }

    /**
     * 获取缩进字符
     *
     * @param indentCount
     * @return
     */
    public static String getIndent(int indentCount) {
        return generateSomeCharacter(indentCount, SYMBOL.BLANK);
    }

    /**
     * 获取缩进字符不足用c代替
     *
     * @param characterCount
     * @param c
     * @return
     */
    public static String generateSomeCharacter(int characterCount, String c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < characterCount; i++) {
            sb.append(c == null ? SYMBOL.UNDERLINE : c);
        }
        return sb.toString();
    }

    /**
     * null或""为true 否则为false
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(Object str) {
        return str == null || SYMBOL.EMPTY.equals(str.toString().trim());
    }

    /**
     * 获取在线QQ字符串
     *
     * @param qq
     * @return
     */
    public static String getOnlineQQ(String qq) {
        return "<a target=blank href=\"http://wpa.qq.com/msgrd?V=1&Uin={0}&Exe=QQ&Site="
            + Config.getLanguageValue(CONFIG_KEY_LANGUAGE.WEBSITE_NAME,
            "zh_cn")
            + "&Menu=No\"><img border=\"0\" src=\"http://wpa.qq.com/pa?p=1:"
            + qq + ":1\" alt=\"给我发消息\"></a>";
    }

    /**
     * 将HTML中的br转换成回车文本
     *
     * @param html
     * @return
     */
    public static String convertHtmlBrToEnterText(String html) {
        if (!html.contains("<br/>")) {
            return html;
        }
        return html.toLowerCase().replace("<br/>", CONSTANT.ENTER_TEXT);
    }

    /**
     * 将回车文本转换成html中的br
     *
     * @param text
     * @return
     */
    public static String convertEnterTextToHtmlBr(String text) {
        if (!text.contains(CONSTANT.ENTER_TEXT)) {
            return text;
        }
        return text.replace(CONSTANT.ENTER_TEXT, "<br/>");
    }

    /**
     * 一定是双位数
     *
     * @param bytes byte[]
     * @return String
     */
    public static String bytes2HexString(byte[] bytes) {
        String ret = "";
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" byte[]{0x2B, 0x44, 0xEF,0xD9}
     *
     * @param src String
     * @return byte[]
     */
    public static byte[] hexString2Bytes(String src) {
        byte[] tmp = src.getBytes();
        byte[] ret = new byte[tmp.length / 2];
        for (int i = 0; i < tmp.length / 2; i++) {
            byte src0 = tmp[i * 2];
            byte src1 = tmp[i * 2 + 1];
            byte b0 = Byte.decode("0x" + new String(new byte[] {src0}));
            b0 = (byte) (b0 << 4);
            byte b1 = Byte.decode("0x" + new String(new byte[] {src1}));
            ret[i] = (byte) (b0 ^ b1);
        }
        return ret;
    }

    /**
     * 从数组array中排除exceptArray并拼接成数组 用于标签删除时的帖子标签更新
     *
     * @param array
     * @param joinChar
     * @param exceptArray
     * @return
     */
    public static String join(Object[] array, char joinChar,
        Object[] exceptArray) {
        StringBuilder sb = new StringBuilder();
        for (Object object : array) {
            if (existInArray(exceptArray, object)) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(joinChar);
            }
            sb.append(object);
        }
        return sb.toString();
    }

    public static String join(String joinChar, Object... array) {
        StringBuilder sb = new StringBuilder();
        for (Object object : array) {
            if (object == null) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(joinChar);
            }
            sb.append(object);
        }
        return sb.toString();
    }

    public static String join(Map<Integer, String> map) {
        return join(map, SYMBOL.COMMA);
    }

    public static String join(Map<Integer, String> map, String joinChar) {
        StringBuilder sb = new StringBuilder();
        for (Integer key : map.keySet()) {
            if (sb.length() > 0) {
                sb.append(joinChar);
            }
            sb.append(map.get(key));
        }
        return sb.toString();
    }

    public static String join(Iterable<?> collection) {
        if (collection == null) {
            return SYMBOL.EMPTY;
        }
        return join(collection, SYMBOL.COMMA);
    }

    public static String join(Iterable<?> collection, String joinChar) {
        StringBuilder sb = new StringBuilder();
        for (Object object : collection) {
            if (sb.length() > 0) {
                sb.append(joinChar);
            }
            sb.append(object.toString().trim());
        }
        return sb.toString();
    }

    public static String join(List<List<String>> collections, String outerJoin, String innerJoin) {
        StringBuilder sb = new StringBuilder();
        for (Collection<?> collection : collections) {
            if (sb.length() > 0) {
                sb.append(outerJoin);
            }
            sb.append(join(collection, innerJoin));
        }
        return sb.toString();
    }

    public static String decodeForGet(String text) {
        try {
            return new String(text.getBytes(CONSTANT.CHARSET_ISO_8859_1), CONSTANT.CHARSET_UTF_8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 驼峰格式变小写_分隔串
     *
     * @param source
     * @return
     */
    public static String humpToLower(String source) {
        return humpToLower(source, CHAR_SYMBOL.UNDERLINE);
    }

    public static String humpToLower(String source, char split) {
        source = source.replaceAll("(?<=[a-z])(?=[A-Z])", String.valueOf(split));
        return source.toLowerCase();
    }

    public static String underlineToHump(String source) {
        return toHump(source, SYMBOL.UNDERLINE);
    }

    public static String toHump(String source, String split) {
        if (isNullOrEmpty(split)) {
            return setFirstByteLowerCase(source);
        }
        String[] underlineArray = source.split(split);
        StringBuilder stringBuilder = new StringBuilder();
        for (String underline : underlineArray) {
            stringBuilder.append(setFirstByteUpperCase(underline));
        }
        return setFirstByteLowerCase(stringBuilder.toString());
    }

    public static String leftPad(String s, char c, int maxLength) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = s.length(); i < maxLength; i++) {
            sb.insert(0, c);
        }
        return sb.toString();
    }

    public static InputStream inputStream(String s) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(s.getBytes(CONSTANT.CHARSET_UTF_8));
    }

    public static String replace(String source, Map<?, String> rep) {
        for (Object key : rep.keySet()) {
            if (source.contains(key.toString())) {
                source = source.replace(key.toString(), rep.get(key));
            }
        }
        return source;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public static String byteToStr(byte[] byteArray) {
        String strDigest = SYMBOL.EMPTY;
        for (byte b : byteArray) {
            strDigest += byteToHexStr(b);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] digit = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];
        return new String(tempArr);
    }

    public static String serialParameters(Map<String, String> parameters) {
        return serialParameters(parameters, true);
    }

    public static Map<String, String> deSerialParameters(String requestToken) {
        Map<String, String> tokens = new TreeMap<String, String>();

        if (StringUtility.isNullOrEmpty(requestToken)) {
            return null;
        }

        requestToken = requestToken.trim();
        if (requestToken.startsWith(SYMBOL.QUESTION_MARK) || requestToken.startsWith(SYMBOL.AND)) {
            requestToken = requestToken.substring(1);
        }

        String[] requestArray = requestToken.split(SYMBOL.AND);
        for (String pair : requestArray) {
            Pair<String, String> p = Pair.split(pair, SYMBOL.EQUAL);
            tokens.put(p.getFirst(), p.getSecond());
        }
        return tokens;
    }

    public static String serialParameters(Map<String, String> parameters,
        boolean isEncode) {
        return serialParameters(parameters, isEncode, null);
    }

    public static String serialParameters(Map<String, String> parameters,
        boolean isEncode, List<String> exceptKeyList) {
        StringBuilder serialParameters = new StringBuilder();
        for (String key : parameters.keySet()) {
            String v = parameters.get(key);
            if (StringUtility.isNullOrEmpty(v)) {
                continue;
            }
            if (exceptKeyList != null) {
                if (exceptKeyList.contains(key)) {
                    continue;
                }
            }
            if (serialParameters.length() > 0) {
                serialParameters.append(SYMBOL.AND);
            }
            if (isEncode) {
                try {
                    serialParameters.append(key + SYMBOL.EQUAL
                        + URLEncoder.encode(v, CONSTANT.CHARSET_UTF_8));
                } catch (UnsupportedEncodingException ignore) {
                }

            } else {
                serialParameters.append(key + SYMBOL.EQUAL + v);
            }
        }
        return serialParameters.toString();
    }

    public static String replaceParameter(String queryString, String currentQuery) {
        if (queryString == null) {
            queryString = SYMBOL.EMPTY;
        }
        if (isNullOrEmpty(currentQuery)) {
            if (!isNullOrEmpty(queryString)) {
                queryString = "?" + queryString;
            }
            return queryString;
        }
        Map<String, String> map = new HashMap<String, String>();

        if (!StringUtility.isNullOrEmpty(queryString)) {
            map = deSerialParameters(queryString);
        }

        Pair<String, String> pair = Pair.split(currentQuery, "=");
        if (map == null) {
            return SYMBOL.EMPTY;
        }
        if (map.containsKey(pair.getFirst())) {
            map.remove(pair.getFirst());
        }
        if (!isNullOrEmpty(pair.getSecond())) {
            map.put(pair.getFirst(), pair.getSecond());
        }
        return serialParameters(map);
    }

    public static String getParameter(Entity entity) {
        Container container = ApplicationContext.getContainer();
        List<TypeConverter> fieldList = container.getFieldList(entity.getClass());
        MethodAccessor methodAccessor = container.getProxyBean(entity.getClass());
        StringBuilder sb = new StringBuilder();
        for (TypeConverter field : fieldList) {
            Object o = methodAccessor.get(entity, field.getName());
            if (StringUtility.isNullOrEmpty(o)) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(SYMBOL.DOLLAR);
            }
            try {
                sb.append(StringUtility.setFirstByteLowerCase(field.getName())
                    + SYMBOL.EQUAL
                    + URLEncoder.encode(String.valueOf(o), CONSTANT.CHARSET_UTF_8));
            } catch (UnsupportedEncodingException ignore) {
            }
        }
        return sb.toString();
    }

    public static String wrap(String source, String wrap, String lineSplit) {
        if (isNullOrEmpty(source)) {
            return SYMBOL.EMPTY;
        }

        if (isNullOrEmpty(wrap)) {
            wrap = "<p>%1$s</p>";
        }

        if (isNullOrEmpty(lineSplit)) {
            lineSplit = CONSTANT.ENTER_TEXT;
        }

        String[] lineArray = source.split(lineSplit);
        StringBuilder builder = new StringBuilder();
        for (String line : lineArray) {
            builder.append(String.format(wrap, line));
        }
        return builder.toString();
    }

    public static String wrap(String source, String wrap) {
        return wrap(source, wrap, CONSTANT.ENTER_TEXT);
    }

    public static String wrap(String source) {
        return wrap(source, "<p>%1$s</p>", CONSTANT.ENTER_TEXT);
    }

    public static String subString(String source, String c) {
        if (isNullOrEmpty(source)) {
            return SYMBOL.EMPTY;
        }
        if (source.contains(c)) {
            source = source.substring(0, source.indexOf(c));
        }
        return source;
    }

    public static boolean matchUrl(String source, String desc) {
        return matchUrl(source, desc, false);
    }

    public static boolean matchUrlWithParameter(String source, String desc) {
        return matchUrl(source, desc, true);
    }

    private static boolean matchUrl(String source, String target, boolean withParameter) {
        if (isNullOrEmpty(source) || isNullOrEmpty(target)) {
            return false;
        }
        if (source.equalsIgnoreCase(target)) {
            return true;
        }
        String rootPath = Config.getValue(CONFIG.ROOT_PATH);
        if (source.startsWith(rootPath)) {
            source = source.substring(rootPath.length());
        }
        if (target.startsWith(rootPath)) {
            target = target.substring(rootPath.length());
        }
        source = subString(source, SYMBOL.POUND_SIGN);
        target = subString(target, SYMBOL.POUND_SIGN);
        if (!withParameter) {
            source = subString(source, SYMBOL.QUESTION_MARK);
            target = subString(target, SYMBOL.QUESTION_MARK);
        }

        String extension = Config.getValue(CONFIG.DEFAULT_PAGE_EXTENSION);
        if (source.endsWith(extension)) {
            source = source.replace(extension, SYMBOL.EMPTY);
        }

        if (target.endsWith(extension)) {
            target = target.replace(extension, SYMBOL.EMPTY);
        }

        if (source.startsWith(SYMBOL.SLASH)) {
            source = source.replace(SYMBOL.SLASH, SYMBOL.EMPTY);
        }
        if (target.startsWith(SYMBOL.SLASH)) {
            target = target.replace(SYMBOL.SLASH, SYMBOL.EMPTY);
        }
        return source.equals(target);
    }

    public static Pair<String, String> secretMobile(String mobile) {
        if (isNullOrEmpty(mobile)) {
            return Pair.create(SYMBOL.EMPTY, SYMBOL.EMPTY);
        }
        String firstSegment = mobile.substring(0, 3);
        String secondSegment = mobile.substring(3, 7);
        String thirdSegment = mobile.substring(7);

        mobile = firstSegment + "****" + thirdSegment;
        String secretMobile = ThreeDES.getInstance().encrypt(secondSegment, Config.getValue(CONFIG.MOBILE_SECRET_3DAS_KEY));
        return Pair.create(mobile, secretMobile);
    }

    public static Boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static String format(String format, Object... args) {
        if (format == null) {
            return SYMBOL.EMPTY;
        }

        if (!format.contains("{}") || args == null || args.length == 0) {
            return format;
        }

        for (Object arg : args) {
            format = format.replaceFirst("\\{\\}", String.valueOf(arg));
        }
        return format;
    }

    public static String printStackTrace(String msg, Throwable t) {
        PrintWriter pw = null;
        try {
            StringWriter sw = new StringWriter();
            pw = new PrintWriter(sw);
            t.printStackTrace(pw);

            StringBuilder exceptionString = new StringBuilder();
            if (!StringUtility.isNullOrEmpty(msg)) {
                exceptionString.append(msg);
                exceptionString.append(CONSTANT.ENTER_TEXT);
            }
            exceptionString.append(sw.toString());
            return exceptionString.toString();
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }

    public static int ignoreCaseIndexOf(String str, String subStr) {
        if (str == null || subStr == null) {
            return -1;
        }
        return str.toLowerCase().indexOf(subStr.toLowerCase());
    }

    public static String getDigit(String str, int start) {
        char c;
        StringBuilder digit = new StringBuilder(10);
        while (start<str.length()&&Character.isDigit(c = str.charAt(start++))) {
            digit.append(c);
        }
        return digit.toString();
    }

    public static int getPrefixCount(String str, String prefix) {
        int count = 0;
        StringBuilder prefixBuilder = new StringBuilder();
        while (str.startsWith(prefixBuilder.append(prefix).toString())) {
            count++;
        }
        return count;
    }
}
