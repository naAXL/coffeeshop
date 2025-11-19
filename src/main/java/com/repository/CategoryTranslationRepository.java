package com.repository;

import com.entity.CategoryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryTranslationRepository extends JpaRepository<CategoryTranslation, Long> {
    List<CategoryTranslation> findByCategoryId(Long categoryId);
}
