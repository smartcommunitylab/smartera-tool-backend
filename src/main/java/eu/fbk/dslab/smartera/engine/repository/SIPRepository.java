package eu.fbk.dslab.smartera.engine.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import eu.fbk.dslab.smartera.engine.model.SIP;

@Repository
public interface SIPRepository extends MongoRepository<SIP, String> {
    // Custom query methods can be defined here if needed
    List<SIP> findByOwner(String owner);
    SIP findOneByInviteCode(String inviteCode);
}