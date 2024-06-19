package ru.practicum.service.impl.priv;

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
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.service.api.priv.PrivateCommentService;
import ru.practicum.storage.CommentStorage;
import ru.practicum.storage.EventStorage;
import ru.practicum.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final CommentStorage commentStorage;
    private final CommentMapper commentMapper;
    private final UserStorage userStorage;
    private final EventStorage eventStorage;


    @Override
    @Transactional
    public CommentOutputDto addComment(Long eventId, Long userId, CommentInputDto commentInputDto) {
        User user = checkUserId(userId);
        Event event = checkEventId(eventId);
        return commentMapper.toCommentOutputDto(commentStorage.save(Comment.builder()
                .text(commentInputDto.getText())
                .created(LocalDateTime.now())
                .event(event)
                .user(user)
                .build()));
    }

    @Override
    public List<CommentOutputDto> getComments(Long eventId, Integer from, Integer size) {
        checkEventId(eventId);
        Pageable pageable = PageRequest.of(from / size, size);
        List<Comment> comments = commentStorage.findAllByEventId(eventId, pageable);
        return commentMapper.toCommentOutputDtoList(comments);
    }

    @Override
    @Transactional
    public CommentOutputDto updateComment(CommentInputDto commentInputDto, Long commentId, Long userId) {
        Comment comment = checkCommentId(commentId);
        checkUserId(userId);
        checkForOwner(userId, comment);
        comment.setText(commentInputDto.getText());
        return commentMapper.toCommentOutputDto(commentStorage.save(comment));
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = checkCommentId(commentId);
        checkUserId(userId);
        checkForOwner(userId, comment);
        commentStorage.deleteById(commentId);
    }

    private void checkForOwner(Long userId, Comment comment) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new ValidException("You are not owner of this comment", HttpStatus.CONFLICT);
        }
    }

    private Comment checkCommentId(Long commentId) {
        return commentStorage.findById(commentId).orElseThrow(() ->
                new ValidException("Comment was not found", HttpStatus.NOT_FOUND));
    }

    private Event checkEventId(Long eventId) {
        return eventStorage.findById(eventId).orElseThrow(() ->
                new ValidException("Event was not found", HttpStatus.NOT_FOUND));
    }

    private User checkUserId(Long userId) {
        return userStorage.findById(userId).orElseThrow(() ->
                new ValidException("User was not found", HttpStatus.NOT_FOUND));
    }
}
