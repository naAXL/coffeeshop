package com.repository;

import com.entity.MenuItemTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemTranslationRepository extends JpaRepository<MenuItemTranslation, Long> {
    List<MenuItemTranslation> findByMenuItemId(Long menuItemId);
}
