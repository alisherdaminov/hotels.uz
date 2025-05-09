package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.hotels.created.CommentsCreatedDTO;
import hotels.uz.dto.hotels.dto.CommentsDTO;
import hotels.uz.service.hotels.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping("/create/{userId}/{hotelsDetailsId}")
    public ResponseEntity<ApiResponse<CommentsDTO>> createComment(@RequestBody CommentsCreatedDTO dto,
                                                                  @PathVariable("userId") Integer userId,
                                                                  @PathVariable("hotelsDetailsId") String hotelsDetailsId) {
        return ResponseEntity.ok().body(new ApiResponse<>(commentsService.createComment(dto, userId, hotelsDetailsId),
                "Success", new Date()));
    }

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponse<List<CommentsDTO>>> findAllComments() {
        return ResponseEntity.ok().body(new ApiResponse<>(commentsService.findAllComments(),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole(ADMIN_ROLE)")
    @PutMapping("/update/{commentId}")
    public ResponseEntity<ApiResponse<CommentsDTO>> updateComment(@PathVariable("commentId") String commentId,
                                                                  @RequestBody CommentsCreatedDTO dto) {
        return ResponseEntity.ok().body(new ApiResponse<>(commentsService.updateComment(commentId, dto),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole(ADMIN_ROLE)")
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable("commentId") String commentId) {
        return ResponseEntity.ok().body(new ApiResponse<>(commentsService.deleteComment(commentId),
                "Success", new Date()));
    }

}