package com.acirio.virtual_bookshelf.repository;

import com.acirio.virtual_bookshelf.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    Boolean existsByName(String name);
}
