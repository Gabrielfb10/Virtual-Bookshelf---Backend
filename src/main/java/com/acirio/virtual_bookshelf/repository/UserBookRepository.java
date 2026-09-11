package com.acirio.virtual_bookshelf.repository;

import com.acirio.virtual_bookshelf.model.BookModel;
import com.acirio.virtual_bookshelf.model.UserBookModel;
import com.acirio.virtual_bookshelf.model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookRepository extends JpaRepository<UserBookModel, Long> {

    Boolean existsByBookAndUser(BookModel book, UserModel user);

}
