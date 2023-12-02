package com.cooksnap.backend.domains.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rating")
@IdClass(RatingKey.class)
public class Rating {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "rate_score")
    private Integer rateScore;

    @Id
    @Column(name = "dish_id")
    private Integer dishId;

}
