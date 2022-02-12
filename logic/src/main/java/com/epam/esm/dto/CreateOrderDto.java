package com.epam.esm.dto;

import com.sun.istack.NotNull;

public class CreateOrderDto {

    @NotNull
    private long userId;
    @NotNull
    private long certificateId;

    public CreateOrderDto(long userId, long certificateId) {
        this.userId = userId;
        this.certificateId = certificateId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(long certificateId) {
        this.certificateId = certificateId;
    }




}
