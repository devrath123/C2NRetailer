package com.example.c2n.core.models;

import com.squareup.moshi.Json;

public class OfferEndDateEntity {
    @Json(name = "_seconds")
    private long seconds;
    @Json(name = "_nanoseconds")
    private long nanoseconds;

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getNanoseconds() {
        return nanoseconds;
    }

    public void setNanoseconds(long nanoseconds) {
        this.nanoseconds = nanoseconds;
    }
}
