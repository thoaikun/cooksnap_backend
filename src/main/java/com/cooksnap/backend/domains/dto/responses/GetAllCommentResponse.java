package com.cooksnap.backend.domains.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllCommentResponse {
    private Integer userId;
    private String userName;
    private String commentText;
}
