package com.zqw.gp.component.control.url;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqw.gp.component.dao.UrlInfoDao;
import com.zqw.gp.component.entity.UrlInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zqw
 * @date 2023/3/8 10:54
 */
public class UrlInfoControl {

    private static UrlInfoDao urlInfoDao;

    private static Map<Integer,UrlInfo> urlInfoMap = new HashMap<>();

    public static void loadService(UrlInfoDao urlInfoDao){
        UrlInfoControl.urlInfoDao = urlInfoDao;
    }

    public static void init(){
        QueryWrapper<UrlInfo> query = new QueryWrapper<>();
        query.eq("enable",true);
        List<UrlInfo> urlInfos = urlInfoDao.selectList(query);
        for (UrlInfo urlInfo : urlInfos){
            urlInfoMap.put(urlInfo.getId(),urlInfo);
        }
    }

    public static UrlInfo getUrl(int id){
        return urlInfoMap.get(id);
    }

    public static UrlInfo getUrl(String resource,String name){
        for (UrlInfo urlInfo : urlInfoMap.values()){
            if (urlInfo.getResource().equals(resource) && urlInfo.getName().equals(name)){
                return urlInfo;
            }
        }
        return null;
    }
}
