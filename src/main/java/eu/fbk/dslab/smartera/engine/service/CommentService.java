package eu.fbk.dslab.smartera.engine.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.fbk.dslab.smartera.engine.model.Comment;
import eu.fbk.dslab.smartera.engine.model.Invite;
import eu.fbk.dslab.smartera.engine.model.SIP;
import eu.fbk.dslab.smartera.engine.repository.CommentRepository;
import eu.fbk.dslab.smartera.engine.repository.InviteRepository;
import eu.fbk.dslab.smartera.engine.repository.SIPRepository;
import eu.fbk.security.UserInfo;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final InviteRepository inviteRepository;
    private final SIPRepository sipRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, 
            InviteRepository inviteRepository, SIPRepository sipRepository) {
        this.commentRepository = commentRepository;
        this.inviteRepository = inviteRepository;
        this.sipRepository = sipRepository;
    }

    public Comment addSipComment(UserInfo owner, String sipId, String text) throws IllegalArgumentException {
        Invite existingInvite = inviteRepository.findBySipIdAndInvitedUser(sipId, owner.getEmail());
        if (existingInvite == null) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        Comment existingCOmment = commentRepository.findOneByOwnerAndSipIdAndComponentIdNull(owner.getEmail(), sipId);
        if (existingCOmment != null) {
            return existingCOmment; // Return existing comment if it already exists
        }
        Comment comment = new Comment(owner.getEmail(), owner.getName(), sipId, null, text);
        commentRepository.save(comment);
        return comment;
    }

    public Comment addComponentComment(UserInfo owner, String sipId, String componentId, String text) throws IllegalArgumentException {
        Invite existingInvite = inviteRepository.findBySipIdAndInvitedUser(sipId, owner.getEmail());
        if (existingInvite == null) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        Comment existingCOmment = commentRepository.findOneByOwnerAndSipIdAndComponentId(owner.getEmail(), sipId, componentId);
        if (existingCOmment != null) {
            return existingCOmment; // Return existing comment if it already exists
        }
        Comment comment = new Comment(owner.getEmail(), owner.getName(), sipId, componentId, text);
        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getComments(UserInfo owner, String sipId) throws IllegalArgumentException {
        Invite existingInvite = inviteRepository.findBySipIdAndInvitedUser(sipId, owner.getEmail());
        if (existingInvite == null) {
            throw new IllegalArgumentException("You do not have permission to view comments for this entity.");
        }
        return commentRepository.findByOwnerAndSipId(owner.getEmail(), sipId);
    }

    public List<Comment> getCommentsBySIPOwner(UserInfo owner, String sipId) throws IllegalArgumentException {
        SIP sip = sipRepository.findById(sipId).orElse(null);
        if (sip == null) {
            throw new IllegalArgumentException("SIP not found");
        }
        if (!StringUtils.equals(sip.getOwner(), owner.getEmail())) {
            throw new IllegalArgumentException("You do not have permission to view comments for this SIP.");
        }
        return commentRepository.findBySipId(sipId);
    }
}