package com.acirio.virtual_bookshelf.model;

import com.acirio.virtual_bookshelf.model.enums.StatusBookEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_user_book")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private UserModel user;

    @ManyToOne()
    @JoinColumn(name="book_id")
    private BookModel book;

    @Enumerated(EnumType.STRING)
    private StatusBookEnum status;

    private int currentPage;

    private int percentageRead;

    private float note;

    private LocalDateTime addedDate;

    private LocalDateTime readingStartDay;

    private LocalDateTime readingEndDate;
}
