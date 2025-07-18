package eu.fbk.dslab.smartera.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.fbk.dslab.smartera.engine.model.Invite;
import eu.fbk.dslab.smartera.engine.repository.InviteRepository;

@Service
public class InviteService {
    private final InviteRepository inviteRepository;

    @Autowired
    public InviteService(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }
    
    public Invite saveInvite(String owner, String sipId, String invitedUser) {
        Invite existingInvite = inviteRepository.findByOwnerAndSipIdAndInvitedUser(owner, sipId, invitedUser);
        if (existingInvite == null) {
            Invite invite = new Invite(owner, sipId, invitedUser);
            return inviteRepository.save(invite);
        } else {
            return existingInvite; // Return existing invite if it already exists
        }
    }

    public void deleteInvite(String owner, String sipId, String invitedUser) {
        Invite existingInvite = inviteRepository.findByOwnerAndSipIdAndInvitedUser(owner, sipId, invitedUser);
        if (existingInvite != null) {
            inviteRepository.delete(existingInvite);
        }
    }
}
