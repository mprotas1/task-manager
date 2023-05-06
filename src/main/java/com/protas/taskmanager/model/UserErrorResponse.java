package com.protas.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record UserErrorResponse(int status, String message, long timeStamp)
{ }
