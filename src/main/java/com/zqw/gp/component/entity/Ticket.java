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
    private String businessSeat;
    private String firstSeat;
    private String secondSeat;
    private String noSeat;
    private String arriveDate;
    private float businessSeatPrice;
    private float firstSeatPrice;
    private float secondSeatPrice;
    private float noSeatPrice;

    public Ticket(String trainCode, String trainNo, String fromStation, String toStation, String startStation, String endStation, String fromTime, String toTime, String costTime, String businessSeat, String firstSeat, String secondSeat, String noSeat, String arriveDate, float businessSeatPrice, float firstSeatPrice, float secondSeatPrice, float noSeatPrice) {
        this.trainCode = trainCode;
        this.trainNo = trainNo;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.startStation = startStation;
        this.endStation = endStation;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.costTime = costTime;
        this.businessSeat = businessSeat;
        this.firstSeat = firstSeat;
        this.secondSeat = secondSeat;
        this.noSeat = noSeat;
        this.arriveDate = arriveDate;
        this.businessSeatPrice = businessSeatPrice;
        this.firstSeatPrice = firstSeatPrice;
        this.secondSeatPrice = secondSeatPrice;
        this.noSeatPrice = noSeatPrice;
    }
}
