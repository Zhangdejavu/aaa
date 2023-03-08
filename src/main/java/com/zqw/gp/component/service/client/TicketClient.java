package com.zqw.gp.component.service.client;

import com.zqw.gp.component.common.Constants;
import com.zqw.gp.component.common.ErrorCode;
import com.zqw.gp.component.common.ServerException;
import com.zqw.gp.component.control.url.UrlInfoControl;
import com.zqw.gp.component.entity.*;
import com.zqw.gp.utils.GpUtils;
import com.zqw.gp.utils.http.BaseHttp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zhangqianwei
 * @date 2023/3/6 18:57
 */
@Service
@Slf4j
public class TicketClient {


    /**
     * 登录
     */
    public boolean login(){
        return true;
    }

    /**
     * 查询余票
     */
    public List<Ticket> queryTicket(LeftTicketDTO dto){
        try {
            UrlInfo urlInfo = UrlInfoControl.getUrl(Constants.URL_RESOURCE_12306,"查询余票");
            if (urlInfo == null){
                throw new ServerException(ErrorCode.URL_NOT_EXIT,"无此URL");
            }
            //请求头配置
            Map<String, String> headers = GpUtils.buildHeaders(dto);
            //请求的参数
            Map<String, String> paras = GpUtils.buildParams(dto);

            DataResult result = BaseHttp.getGetObj(urlInfo.getUrl(),
                    paras,headers, DataResult.class);
            return GpUtils.splitData(result);
        }catch (Exception e){
            log.error("fail to connect to ticketClient");
            return null;
        }
    }

    /**
     * 查询车站代码
     */
    public Object queryStation(){
        return null;
    }

    /**
     * 查询车次信息
     */
    public Object queryTrainNo(){
        return null;
    }

    /**
     * 提交订单
     */
    public boolean submitOrder(){
        return true;
    }


    public static boolean sendSuccess(HttpResponse response){
        if (response!=null){
            if (response.getHttpstatus()!=null){
                if (response.getHttpstatus()== Constants.HTTP_RESPONSE_CODE_OK || response.getHttpstatus()== Constants.HTTP_RESPONSE_CODE_SUCCESS){
                    return true;
                }
            }
            if (response.getMessages()!=null){
                if (response.getMessages().equals("")){
                    return true;
                }
            }
            if (response.isStatus()){
                return true;
            }
        }
        return false;
    }
}
