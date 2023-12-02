package com.cooksnap.backend.domains.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RatingKey implements Serializable {
    private Integer userId;
    private Integer dishId;
}
