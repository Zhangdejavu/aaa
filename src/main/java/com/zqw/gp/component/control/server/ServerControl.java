package com.zqw.gp.component.control.server;

import com.zqw.gp.component.common.SystemStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zqw
 * @date 2023/3/8 14:04
 */
@Slf4j
public class ServerControl implements Runnable{

    private int id;
    private String url;

    private boolean stop = false;

    public ServerControl(int id, String url) {
        this.id = id;
        this.url = url;
    }

    @Override
    public void run() {
       while (true){
           //系统或者此台服务器被停用
            if (SystemStatus.isStop() || stop){
                ServerGroupControl.removeServer(id);
                log.info("success stop server {} control",id);
                break;
            }
       }
    }

    public void stop(boolean stop) {
        this.stop = stop;
    }
}
