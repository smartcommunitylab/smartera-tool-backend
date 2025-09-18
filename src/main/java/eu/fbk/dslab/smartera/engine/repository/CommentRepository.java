package eu.fbk.dslab.smartera.engine.repository;

import eu.fbk.dslab.smartera.engine.model.Comment;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findBySipId(String sipId);
    List<Comment> findByOwnerAndSipId(String owner, String sipId);
    Comment findOneByOwnerAndSipIdAndComponentId(String owner, String sipId, String componentId);
    Comment findOneByOwnerAndSipIdAndComponentIdNull(String owner, String sipId);

    void deleteBySipId(String sipId);
}