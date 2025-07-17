package eu.fbk.dslab.smartera.engine.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.fbk.dslab.smartera.engine.model.Invite;
import eu.fbk.dslab.smartera.engine.model.Like;
import eu.fbk.dslab.smartera.engine.repository.InviteRepository;
import eu.fbk.dslab.smartera.engine.repository.LikeRepository;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final InviteRepository inviteRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, InviteRepository inviteRepository) {
        this.likeRepository = likeRepository;
        this.inviteRepository = inviteRepository;
    }
    
    public Like addSipLike(String owner, String sipId) {
        Invite existingInvite = inviteRepository.findBySipIdAndInvitedUser(sipId, owner);
        if (existingInvite == null) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        Like existingLike = likeRepository.findByOwnerAndSipIdAndComponentIdIsNull(owner, sipId);
        if (existingLike != null) {
            return existingLike; // Return existing like if it already exists
        }
        Like like = new Like(owner, sipId);
        likeRepository.save(like);
        return like;
    }
}
