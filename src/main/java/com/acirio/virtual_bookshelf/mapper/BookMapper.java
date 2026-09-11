package com.acirio.virtual_bookshelf.mapper;

import com.acirio.virtual_bookshelf.dto.BookRequestDto;
import com.acirio.virtual_bookshelf.dto.BookResponseDto;
import com.acirio.virtual_bookshelf.model.BookModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookModel toEntity(BookRequestDto bookRequestDto);
    BookResponseDto toResponse(BookModel bookModel);
}
