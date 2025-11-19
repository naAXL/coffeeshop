package com.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_item_translations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(nullable = false, length = 5)
    private String language;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
