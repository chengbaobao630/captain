package cc.home.framework.bean;

import cc.home.util.CastUtil;

import java.util.Map;

/**
 * Created by cheng on 2017/3/22 0022.
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String ,Object> getMap(){
        return paramMap;
    }




}
