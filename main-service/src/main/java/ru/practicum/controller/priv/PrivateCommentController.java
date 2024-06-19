package ru.practicum.controller.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentInputDto;
import ru.practicum.dto.comment.CommentOutputDto;
import ru.practicum.service.api.priv.PrivateCommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/private/comments")
public class PrivateCommentController {
    private final PrivateCommentService privateCommentService;

    @PostMapping("/{eventId}/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentOutputDto addComment(@PathVariable Long eventId,
                                       @PathVariable Long userId,
                                       @RequestBody @Valid CommentInputDto commentInputDto) {
        return privateCommentService.addComment(eventId, userId, commentInputDto);
    }

    @GetMapping("/{eventId}")
    public List<CommentOutputDto> getComments(@PathVariable Long eventId,
                                              @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                              @RequestParam(defaultValue = "10") @Positive Integer size) {
        return privateCommentService.getComments(eventId, from, size);
    }

    @PatchMapping("/{commentId}/{userId}")
    public CommentOutputDto updateComment(@RequestBody @Valid CommentInputDto commentInputDto,
                                          @PathVariable Long commentId,
                                          @PathVariable Long userId) {
        return privateCommentService.updateComment(commentInputDto, commentId, userId);
    }

    @DeleteMapping("/{commentId}/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId,
                              @PathVariable Long userId) {
        privateCommentService.deleteComment(commentId, userId);
    }
}
