package com.zqw.gp.component.entity;

/**
 * @author zhangqianwei
 * @date 2023/3/7 10:29
 */
public class LeftTicketDTO {
    private String train_date;
    private String from_station;
    private String to_station;
    private String purpose_codes;

    public LeftTicketDTO() {
    }

    public LeftTicketDTO(String train_date, String from_station, String to_station, String purpose_codes) {
        this.train_date = train_date;
        this.from_station = from_station;
        this.to_station = to_station;
        this.purpose_codes = purpose_codes;
    }

    public String getTrain_date() {
        return train_date;
    }

    public void setTrain_date(String train_date) {
        this.train_date = train_date;
    }

    public String getFrom_station() {
        return from_station;
    }

    public void setFrom_station(String from_station) {
        this.from_station = from_station;
    }

    public String getTo_station() {
        return to_station;
    }

    public void setTo_station(String to_station) {
        this.to_station = to_station;
    }

    public String getPurpose_codes() {
        return purpose_codes;
    }

    public void setPurpose_codes(String purpose_codes) {
        this.purpose_codes = purpose_codes;
    }

    @Override
    public String toString() {
        return "LeftTicketDTO{" +
                "train_date='" + train_date + '\'' +
                ", from_station='" + from_station + '\'' +
                ", to_station='" + to_station + '\'' +
                ", purpose_codes='" + purpose_codes + '\'' +
                '}';
    }
}
