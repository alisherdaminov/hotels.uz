package hotels.uz.service.hotels;

import hotels.uz.dto.hotels.created.CommentsCreatedDTO;
import hotels.uz.dto.hotels.dto.CommentsDTO;
import hotels.uz.entity.hotels.CommentEntity;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.hotels.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    public CommentsDTO createComment(CommentsCreatedDTO dto, Integer userId, String hotelsDetailsId) {
        CommentEntity entity = new CommentEntity();
        entity.setCommentText(dto.getCommentsText());
        entity.setUserId(userId);
        entity.setHotelsDetailsId(hotelsDetailsId);
        entity.setCreatedDate(LocalDateTime.now());
        commentsRepository.save(entity);
        return toDTO(entity);
    }

    public List<CommentsDTO> findAllComments() {
        List<CommentEntity> entityList = commentsRepository.findAll();
        if (entityList.isEmpty()) {
            throw new AppBadException("Comments are not found!");
        }
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CommentsDTO updateComment(String commentsId, CommentsCreatedDTO dto) {
        Optional<CommentEntity> optional = commentsRepository.findById(commentsId);
        if (optional.isEmpty()) {
            throw new AppBadException("Comments are not found!");
        }
        CommentEntity entity = optional.get();
        entity.setCommentText(dto.getCommentsText());
        commentsRepository.save(entity);
        return toDTO(entity);
    }

    public String deleteComment(String commentsId) {
        Optional<CommentEntity> optional = commentsRepository.findById(commentsId);
        if (optional.isEmpty()) {
            throw new AppBadException("Comments are not found!");
        }
        commentsRepository.deleteById(commentsId);
        return "Deleted" + commentsId;
    }

    public CommentsDTO toDTO(CommentEntity entity) {
        CommentsDTO dto = new CommentsDTO();
        dto.setCommentsId(entity.getCommentId());
        dto.setCommentsText(entity.getCommentText());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}