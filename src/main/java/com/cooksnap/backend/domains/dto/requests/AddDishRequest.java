package com.cooksnap.backend.domains.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddDishRequest {
    private int listId;
    private String dishId;
    private String aboutDish;
}
