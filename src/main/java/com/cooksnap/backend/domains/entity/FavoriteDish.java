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
@Table(name = "favorite_dish")
public class FavoriteDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dish_id")
    private Integer dishId;

    @Column(name = "favorite_list_id")
    private Integer favoriteListId;
}
