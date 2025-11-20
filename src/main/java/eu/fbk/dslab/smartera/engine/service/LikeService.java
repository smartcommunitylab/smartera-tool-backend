package eu.fbk.dslab.smartera.engine.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.fbk.dslab.smartera.engine.model.Invite;
import eu.fbk.dslab.smartera.engine.model.Like;
import eu.fbk.dslab.smartera.engine.model.SIP;
import eu.fbk.dslab.smartera.engine.repository.InviteRepository;
import eu.fbk.dslab.smartera.engine.repository.LikeRepository;
import eu.fbk.dslab.smartera.engine.repository.SIPRepository;
import eu.fbk.dslab.smartera.engine.security.UserInfo;

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
    
    public Like addSipLike(UserInfo owner, String sipId) throws IllegalArgumentException {
        Invite existingInvite = inviteRepository.findBySipIdAndInvitedUser(sipId, owner.getEmail());
        if (existingInvite == null) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        Like existingLike = likeRepository.findOneByOwnerAndSipIdAndComponentIdNull(owner.getEmail(), sipId);
        if (existingLike != null) {
            return existingLike; // Return existing like if it already exists
        }
        Like like = new Like(owner.getEmail(), owner.getName(), sipId);
        likeRepository.save(like);
        return like;
    }

    public Like addComponentLike(UserInfo owner, String sipId, String componentId) throws IllegalArgumentException {
        Invite existingInvite = inviteRepository.findBySipIdAndInvitedUser(sipId, owner.getEmail());
        if (existingInvite == null) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        Like existingLike = likeRepository.findOneByOwnerAndSipIdAndComponentId(owner.getEmail(), sipId, componentId);
        if (existingLike != null) {
            return existingLike; // Return existing like if it already exists
        }
        Like like = new Like(owner.getEmail(), owner.getName(), sipId, componentId);
        likeRepository.save(like);
        return like;
    }

    public List<Like> getLikes(UserInfo owner, String sipId) throws IllegalArgumentException {
        Invite existingInvite = inviteRepository.findBySipIdAndInvitedUser(sipId, owner.getEmail());
        if (existingInvite == null) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        return likeRepository.findByOwnerAndSipId(owner.getEmail(), sipId);
    }

    public List<Like> getLikesBySIPOwner(UserInfo owner, String sipId) throws IllegalArgumentException {
        SIP sip = sipRepository.findById(sipId).orElse(null); 
        if (sip == null) {
            throw new IllegalArgumentException("SIP not found");
        }
        if (!StringUtils.equals(sip.getOwner(), owner.getEmail())) {
            throw new IllegalArgumentException("You do not have permission to view likes for this SIP.");
        }
        return likeRepository.findBySipId(sipId);
    }

}
