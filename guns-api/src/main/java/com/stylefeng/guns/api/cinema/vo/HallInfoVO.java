package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallInfoVO implements Serializable {

    private String hallFieldId;
    private String hallName;
    private Integer price;
    private String seatFile;
    private String soldSeats;

}
