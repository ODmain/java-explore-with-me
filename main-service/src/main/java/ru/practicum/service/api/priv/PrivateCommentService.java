package ru.practicum.service.api.priv;

import ru.practicum.dto.comment.CommentInputDto;
import ru.practicum.dto.comment.CommentOutputDto;

import java.util.List;

public interface PrivateCommentService {
    CommentOutputDto addComment(Long eventId, Long userId, CommentInputDto commentInputDto);

    List<CommentOutputDto> getComments(Long eventId, Integer from, Integer size);

    CommentOutputDto updateComment(CommentInputDto commentInputDto, Long commentId, Long userId);

    void deleteComment(Long commentId, Long userId);
}
