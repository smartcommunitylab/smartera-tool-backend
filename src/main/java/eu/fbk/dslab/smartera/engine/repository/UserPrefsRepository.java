package eu.fbk.dslab.smartera.engine.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import eu.fbk.dslab.smartera.engine.model.UserPrefs;


@Repository
public interface UserPrefsRepository extends MongoRepository<UserPrefs, String> {
    // Custom query methods can be defined here if needed
    UserPrefs findOneByOwner(String owner);
}