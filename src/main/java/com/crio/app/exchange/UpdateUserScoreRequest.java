package com.crio.app.exchange;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateUserScoreRequest(
    @Min(value = 1)
    @Max(value = 100)
    Integer score
) {    
}
