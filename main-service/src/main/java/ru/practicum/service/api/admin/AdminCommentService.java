package ru.practicum.service.api.admin;

import ru.practicum.dto.comment.CommentInputDto;
import ru.practicum.dto.comment.CommentOutputDto;

import java.util.List;

public interface AdminCommentService {

    CommentOutputDto updateComment(Long commentId, CommentInputDto comment);

    List<CommentOutputDto> getAllCommentsOfEvent(Long eventId, Integer from, Integer size);

    void deleteComment(Long commentId);
}
