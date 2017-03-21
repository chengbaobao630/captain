package cc.home.util;

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

}
