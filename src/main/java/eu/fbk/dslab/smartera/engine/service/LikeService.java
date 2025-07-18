package eu.fbk.dslab.smartera.engine.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.fbk.dslab.smartera.engine.model.Invite;
import eu.fbk.dslab.smartera.engine.model.Like;
import eu.fbk.dslab.smartera.engine.model.SIP;
import eu.fbk.dslab.smartera.engine.repository.InviteRepository;
import eu.fbk.dslab.smartera.engine.repository.LikeRepository;
import eu.fbk.dslab.smartera.engine.repository.SIPRepository;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final InviteRepository inviteRepository;
    private final SIPRepository sipRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, InviteRepository inviteRepository, SIPRepository sipRepository) {
        this.likeRepository = likeRepository;
        this.inviteRepository = inviteRepository;
        this.sipRepository = sipRepository;
    }
    
    public Like addSipLike(String owner, String sipId) throws IllegalArgumentException {
        Invite existingInvite = inviteRepository.findBySipIdAndInvitedUser(sipId, owner);
        if (existingInvite == null) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        Like existingLike = likeRepository.findByOwnerAndSipIdAndComponentIdNull(owner, sipId);
        if (existingLike != null) {
            return existingLike; // Return existing like if it already exists
        }
        Like like = new Like(owner, sipId);
        likeRepository.save(like);
        return like;
    }

    public List<Like> getLikesByOwnerAndSipId(String owner, String sipId) throws IllegalArgumentException {
        Optional<SIP> sip = sipRepository.findById(sipId);
        if (sip.isEmpty()) {
            throw new IllegalArgumentException("SIP with ID " + sipId + " does not exist.");
        }
        if (!sip.get().getOwner().equals(owner)) {
            Invite existingInvite = inviteRepository.findBySipIdAndInvitedUser(sipId, owner);
            if (existingInvite == null) {
                throw new IllegalArgumentException("You do not have permission to view likes for this SIP.");
            }
        }
        return likeRepository.findBySipId(sipId);
    }
}
