package com.acirio.virtual_bookshelf.mapper;

import com.acirio.virtual_bookshelf.dto.ShelfResponseDto;
import com.acirio.virtual_bookshelf.dto.ShelfSummaryDto;
import com.acirio.virtual_bookshelf.model.BookModel;
import com.acirio.virtual_bookshelf.model.UserBookModel;
import com.acirio.virtual_bookshelf.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserBookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "book", source = "book")
    @Mapping(target = "status", constant = "WANT_TO_READ")
    @Mapping(target = "currentPage", constant = "0")
    @Mapping(target = "percentageRead", ignore = true)
    @Mapping(target = "note", ignore = true)
    @Mapping(target = "addedDate", source = "now")
    @Mapping(target = "readingStartDate", ignore = true)
    @Mapping(target = "readingEndDate", ignore = true)
    UserBookModel toEntity(BookModel book, UserModel user, LocalDateTime now);



    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "book", source = "book.id")
    ShelfResponseDto toResponse(UserBookModel userBookModel);
    @Mapping(target = "cover", source = "userBook.book.cover")
    @Mapping(target = "name", source = "userBook.book.name")
    @Mapping(target = "author", source = "userBook.book.author")
    @Mapping(target = "currentPage", source = "userBook.currentPage")
    @Mapping(target = "percentageRead", source = "userBook.percentageRead")
    ShelfSummaryDto toSummary(UserBookModel userBook);
}
