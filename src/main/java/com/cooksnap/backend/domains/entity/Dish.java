package com.cooksnap.backend.domains.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dish")
public class Dish {

    @Id
    @Column(name = "dish_id")
    private String dish_id;

    @Column(name = "about")
    private String about;
}
