package com.example.ipbprojekt.DTO.Rest;

import com.example.ipbprojekt.model.enums.EYacht;

public class YachtRestDTO {
    private Long yachtId;
    private String name;
    private int maxPeople;
    private EYacht type;

    public Long getYachtId() {
        return yachtId;
    }

    public void setYachtId(Long yachtId) {
        this.yachtId = yachtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public EYacht getType() {
        return type;
    }

    public void setType(EYacht type) {
        this.type = type;
    }
}
