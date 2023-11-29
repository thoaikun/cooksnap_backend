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
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "dish_id")
    private Integer dish_id;

    @Column(name = "about")
    private String about;
}
