package cc.home.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by root on 17-3-22.
 */
public class CastUtil {

    public static String castString(Object obj){
        return castString(obj,"");
    }

    private static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    public static boolean castBoolean(Object obj){
        return castBoolean(obj,false);
    }

    private static boolean castBoolean(Object obj, boolean defaultValue) {
        return obj != null ? Boolean.valueOf(castString(obj)) : defaultValue;
    }

    public static double castDouble(Object obj){
        return castDouble(obj,0d);
    }

    private static double castDouble(Object obj, double defaultValue) {
        double doubleValue= defaultValue;
        if (obj != null){
            String stringValue = castString(obj);
            if (StringUtils.isNotEmpty(stringValue)){
                try {
                    doubleValue = Double.parseDouble(stringValue);
                }catch (NumberFormatException e){
                    doubleValue =defaultValue;
                }

            }
        }
        return doubleValue;
    }

    public static int castInt(Object obj) {
        return castInt(obj,0);
    }


    public static long castLong(Object obj) {
        return castLong(obj,0l);
    }

    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null ) {
            String strValue = castString(obj);
            try{
                longValue = Long.parseLong(strValue);
            }catch (NumberFormatException e){
                longValue = defaultValue;
            }
        }
        return longValue;
    }

    private static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null ) {
            String strValue = castString(obj);
            try{
                intValue = Integer.parseInt(strValue);
            }catch (NumberFormatException e){
                intValue = defaultValue;
            }
        }
        return intValue;

    }
}
