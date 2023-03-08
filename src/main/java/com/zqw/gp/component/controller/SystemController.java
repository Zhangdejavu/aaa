package com.zqw.gp.component.controller;

import com.zqw.gp.component.common.SystemStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zqw
 * @date 2023/3/8 15:40
 */
@RequestMapping("system")
@RestController
public class SystemController extends BaseController{

    @PostMapping("stop")
    public Object stop() throws InterruptedException {
        SystemStatus.setStop();

        //等待10s后程序正常执行结束后退出
        Thread.sleep(10000);
        System.exit(0);
        return this.success();
    }
}
