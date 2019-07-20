package com.example.c2n.core.models;

import com.squareup.moshi.Json;

public class DealResponseEntity {
    @Json(name = "status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DealResponseEntity{" +
                "status='" + status + '\'' +
                '}';
    }
}
