package eu.fbk.dslab.smartera.engine.repository;

import eu.fbk.dslab.smartera.engine.model.Invite;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteRepository extends MongoRepository<Invite, String> {
    // Puoi aggiungere metodi di query personalizzati qui
    List<Invite> findByOwnerAndSipId(String owner, String sipId);
    List<Invite> findByInvitedUser(String invitedUser);
    Invite findByOwnerAndSipIdAndInvitedUser(String owner, String sipId, String invitedUser);
    Invite findBySipIdAndInvitedUser(String sipId, String invitedUser);

    void deleteBySipId(String sipId);
}