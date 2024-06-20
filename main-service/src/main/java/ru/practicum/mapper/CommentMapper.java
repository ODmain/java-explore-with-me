package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.comment.CommentOutputDto;
import ru.practicum.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CommentMapper {
    CommentOutputDto toCommentOutputDto(Comment comment);

    List<CommentOutputDto> toCommentOutputDtoList(List<Comment> comments);
}
