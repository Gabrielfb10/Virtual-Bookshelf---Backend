package com.acirio.virtual_bookshelf.dto;


import lombok.Builder;

import java.time.Instant;

@Builder
public record ErrorHandlerResponse (
    Instant timestamp,
    Integer status,
    String error,
    String message,
    String path
) {}
