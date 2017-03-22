package cc.home.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by root on 17-3-22.
 */
public final class CodeUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(CodeUtil.class);

    public static String encodeURL(String source){
        String target;
        try{
            target = URLEncoder.encode(source,"utf-8");
        }catch (Exception e){
            LOGGER.error("encode url due error",e);
            throw  new RuntimeException(e);
        }
        return target;
    }

    public static String decodeURL(String source){
        String target;
        try{
            target = URLDecoder.decode(source,"utf-8");
        }catch (Exception e){
            LOGGER.error("encode url due error",e);
            throw  new RuntimeException(e);
        }
        return target;
    }
}
