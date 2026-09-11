package com.acirio.virtual_bookshelf.model;

import com.acirio.virtual_bookshelf.model.enums.CategoryBookEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_book")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String author;

    @Enumerated(EnumType.STRING)
    private CategoryBookEnum category;

    private String genre;

    private Long numberOfPages;

    private String cover; //Endereço da imagem da capa do livro

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBookModel> usersWhoAdded = new ArrayList<>();
}
