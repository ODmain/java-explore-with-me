package ru.practicum.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.comment.CommentInputDto;
import ru.practicum.dto.comment.CommentOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.service.api.admin.AdminCommentService;
import ru.practicum.storage.CommentStorage;
import ru.practicum.storage.EventStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentStorage commentStorage;
    private final CommentMapper commentMapper;
    private final EventStorage eventStorage;

    @Override
    @Transactional
    public CommentOutputDto updateComment(Long commentId, CommentInputDto commentInputDto) {
        Comment comment = checkCommentId(commentId);
        comment.setText(commentInputDto.getText());
        return commentMapper.toCommentOutputDto(commentStorage.save(comment));
    }

    @Override
    public List<CommentOutputDto> getAllCommentsOfEvent(Long eventId, Integer from, Integer size) {
        if (!eventStorage.existsById(eventId)) {
            throw new ValidException("Event was not found", HttpStatus.NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(from / size, size);
        List<Comment> comments = commentStorage.findAllByEventId(eventId, pageable);
        return commentMapper.toCommentOutputDtoList(comments);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        checkCommentId(commentId);
        commentStorage.deleteById(commentId);
    }

    private Comment checkCommentId(Long commentId) {
        return commentStorage.findById(commentId).orElseThrow(() ->
                new ValidException("Comment was not found", HttpStatus.NOT_FOUND));
    }
}
