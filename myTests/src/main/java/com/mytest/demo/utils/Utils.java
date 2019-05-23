package com.mytest.demo.utils;

public class Utils {
    /**
     * author wj
     * @param string
     * 采用拆分成字符串数组的方式转换成驼峰型的字符串
     * @return
     */
    public static String changeType(String string){
        if (string != null && !string.equals("")){
            String[] strings = string.split("_");
            if (strings.length > 1){
                for (int i=1;i<strings.length;i++){
                    strings[i] = strings[i].substring(0,1).toUpperCase() + strings[i].substring(1);
                }
            }
            StringBuilder builder = new StringBuilder();
            for (String str : strings){
                builder.append(str);
            }
            return builder.toString();
        }else {
            return "";
        }
    }
}
