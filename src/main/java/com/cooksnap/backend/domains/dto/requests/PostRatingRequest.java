package com.cooksnap.backend.domains.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRatingRequest {
    private Integer score;
    private String aboutDish;
}
