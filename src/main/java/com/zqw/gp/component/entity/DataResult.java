package com.zqw.gp.component.entity;

import java.util.List;
import java.util.Map;

/**
 * @author zhangqianwei
 * @date 2023/3/7 14:53
 */
public class DataResult{
    private List<String> result;
    private String flag;
    private Map<String,String> map;

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
