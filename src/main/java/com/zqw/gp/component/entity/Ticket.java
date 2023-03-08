package com.zqw.gp.component.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zqw
 * @date 2023/3/7 20:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ticket")
public class Ticket {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String trainCode;
    private String trainNo;
    private String fromStation;
    private String toStation;
    private String startStation;
    private String endStation;
    private String fromTime;
    private String toTime;
    private String costTime;

    public Ticket(String trainCode, String trainNo, String fromStation, String toStation, String startStation, String endStation, String fromTime, String toTime, String costTime) {
        this.trainCode = trainCode;
        this.trainNo = trainNo;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.startStation = startStation;
        this.endStation = endStation;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.costTime = costTime;
    }
}
