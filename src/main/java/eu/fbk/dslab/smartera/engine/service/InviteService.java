package eu.fbk.dslab.smartera.engine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.fbk.dslab.smartera.engine.model.Invite;
import eu.fbk.dslab.smartera.engine.model.SIP;
import eu.fbk.dslab.smartera.engine.repository.InviteRepository;
import eu.fbk.dslab.smartera.engine.repository.SIPRepository;

@Service
public class InviteService {
    private final InviteRepository inviteRepository;
    private final SIPRepository sipRepository;

    @Autowired
    public InviteService(InviteRepository inviteRepository, 
                         SIPRepository sipRepository) {
        this.inviteRepository = inviteRepository;
        this.sipRepository = sipRepository;
    }
    
    public Invite saveInvite(String inviteCode, String invitedUser) throws IllegalArgumentException {
        SIP sip = sipRepository.findOneByInviteCode(inviteCode);
        if (sip == null) {
            throw new IllegalArgumentException("SIP entity not found.");
        }
        Invite existingInvite = inviteRepository.findByOwnerAndSipIdAndInvitedUser(sip.getOwner(), sip.getId(), invitedUser);
        if (existingInvite == null) {
            Invite invite = new Invite(sip.getOwner(), sip.getId(), invitedUser);
            return inviteRepository.save(invite);
        } else {
            return existingInvite; // Return existing invite if it already exists
        }
    }

    public List<Invite> findByOwner(String owner) {
        return inviteRepository.findByOwner(owner);
    }

    public List<Invite> findByOwnerAndSip(String owner, String sipId) {
        return inviteRepository.findByOwnerAndSipId(owner, sipId);
    }

    public void deleteInvite(String owner, String sipId, String invitedUser) {
        Invite existingInvite = inviteRepository.findByOwnerAndSipIdAndInvitedUser(owner, sipId, invitedUser);
        if (existingInvite != null) {
            inviteRepository.delete(existingInvite);
        }
    }
}
