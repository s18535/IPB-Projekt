package com.example.ipbprojekt.DTO.Rest;

import com.example.ipbprojekt.model.enums.EBail;


public class BailRestDTO {
    private Long bailId;

    private Integer amount;

    private EBail status;

    public Long getBailId() {
        return bailId;
    }

    public void setBailId(Long bailId) {
        this.bailId = bailId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public EBail getStatus() {
        return status;
    }

    public void setStatus(EBail status) {
        this.status = status;
    }
}
