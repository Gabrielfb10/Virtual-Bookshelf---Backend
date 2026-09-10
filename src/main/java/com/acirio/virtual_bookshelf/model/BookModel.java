package com.acirio.virtual_bookshelf.model;

import com.acirio.virtual_bookshelf.model.enums.CategoryBookEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long numberOfPages;

    private String cover; //Endereço da imagem da capa do livro
}
