package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentInputDto;
import ru.practicum.dto.comment.CommentOutputDto;
import ru.practicum.service.api.admin.AdminCommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class AdminCommentController {
    public final AdminCommentService adminCommentService;

    @PatchMapping("/{commentId}")
    public CommentOutputDto updateComment(@PathVariable Long commentId,
                                          @RequestBody @Valid CommentInputDto comment) {
        return adminCommentService.updateComment(commentId, comment);
    }

    @GetMapping("/{eventId}")
    public List<CommentOutputDto> getAllCommentsOfEvent(@PathVariable Long eventId,
                                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        return adminCommentService.getAllCommentsOfEvent(eventId, from, size);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId) {
        adminCommentService.deleteComment(commentId);
    }
}
