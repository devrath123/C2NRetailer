package com.example.c2n.core.models;

public class DealResponseDataModel {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DealResponseDataModel{" +
                "status='" + status + '\'' +
                '}';
    }
}
