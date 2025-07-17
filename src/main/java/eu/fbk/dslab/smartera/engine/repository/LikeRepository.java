package eu.fbk.dslab.smartera.engine.repository;

import eu.fbk.dslab.smartera.engine.model.Like;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    List<Like> findByOwnerAndSipId(String owner, String sipId);
    Like findOneByOwnerAndSipIdAndComponentId(String owner, String sipId, String componentId);
    Like findByOwnerAndSipIdAndComponentIdIsNull(String owner, String sipId);
}