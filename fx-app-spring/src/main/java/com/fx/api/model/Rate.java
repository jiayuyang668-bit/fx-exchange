package com.fx.api.model;

import java.time.LocalDateTime;

public record Rate(int id, String baseCode, String quoteCode, double rate, java.time.LocalDate rateDate, LocalDateTime capturedAt) {
    public String pair() { return baseCode + "/" + quoteCode; }
}
