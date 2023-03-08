package com.zqw.gp.component.control.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqw.gp.component.dao.ServerDao;
import com.zqw.gp.component.entity.Server;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zqw
 * @date 2023/3/8 14:01
 */
public class ServerGroupControl {

    public static ServerDao serverDao;

    public static Map<Integer, ServerControl> serverControlMap = new HashMap<>();

    @PostConstruct
    public static void loadService(ServerDao serverDao){
        ServerGroupControl.serverDao = serverDao;
    }

    public static void init(){
        QueryWrapper<Server> query = new QueryWrapper<>();
        query.eq("enable",true);
        List<Server> servers = serverDao.selectList(query);
        for (Server server : servers){
            addServerControl(server.getId(),server.getUrl());
        }
    }

    public static void addServer(Server server){
        if (!serverControlMap.containsKey(server.getId())){
            addServerControl(server.getId(),server.getUrl());
        }
    }

    public static void removeServer(int id){
        serverControlMap.remove(id);
    }

    public static void addServerControl(int id,String url){
        ServerControl serverControl = new ServerControl(id,url);
        serverControlMap.put(id,serverControl);
        Thread thread = new Thread(serverControl);
        thread.setName("server_control_"+id);
        thread.start();
    }
}
