package eu.fbk.dslab.smartera.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.fbk.dslab.smartera.engine.model.Invite;
import eu.fbk.dslab.smartera.engine.model.SIP;
import eu.fbk.dslab.smartera.engine.repository.InviteRepository;
import eu.fbk.dslab.smartera.engine.repository.LikeRepository;
import eu.fbk.dslab.smartera.engine.repository.SIPRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SIPService {

    private final SIPRepository sipRepository;
    private final LikeRepository likeRepository;
    private final InviteRepository inviteRepository;

    @Autowired
    public SIPService(SIPRepository entityRepository, 
                      LikeRepository likeRepository, 
                      InviteRepository inviteRepository) {
        this.sipRepository = entityRepository;
        this.likeRepository = likeRepository;
        this.inviteRepository = inviteRepository;
    }

    public List<SIP> findByOwner(String owner) {
        return sipRepository.findByOwner(owner);
    }

    public List<SIP> findByInvitedUser(String invitedUser) {
        List<String> ids = inviteRepository.findByInvitedUser(invitedUser).stream().map(Invite::getSipId).toList();
        return sipRepository.findAllById(ids);
    }

    public List<SIP> findPublished() {
        return sipRepository.findByPublished(true);
    }

    public Optional<SIP> findById(String owner, String id) {
        SIP sip = sipRepository.findById(id).orElse(null);
        if (sip != null && !sip.getOwner().equals(owner)) {
            return Optional.empty(); // User does not own this entity
        }
        return sipRepository.findById(id);
    }

    public SIP save(String owner, String id, String inviteCode, Map<String, Object> body) throws IllegalArgumentException {
        SIP sip = sipRepository.findById(id).orElse(null);
        if (sip != null && !sip.getOwner().equals(owner)) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        if (sip == null) {  
            sip = new SIP(id, owner, inviteCode, body);
        } else {
            sip.setBody(body);
        }
        return sipRepository.save(sip);
    }

    public SIP updateInvitationCode(String owner, String id, String inviteCode) throws IllegalArgumentException {
        SIP sip = sipRepository.findById(id).orElse(null);
        if (sip != null && !sip.getOwner().equals(owner)) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        if (sip == null) {
            throw new IllegalArgumentException("SIP entity not found.");
        }
        sip.setInviteCode(inviteCode);
        return sipRepository.save(sip);
    }

    public SIP publishSIP(String owner, String id, boolean published) throws IllegalArgumentException {
        SIP sip = sipRepository.findById(id).orElse(null);
        if (sip != null && !sip.getOwner().equals(owner)) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        if (sip == null) {
            throw new IllegalArgumentException("SIP entity not found.");
        }
        sip.setPublished(published);
        return sipRepository.save(sip);
    }

    public boolean deleteById(String owner, String id) throws IllegalArgumentException {
        SIP sip = sipRepository.findById(id).orElse(null);
        if (sip !=  null) {
            if (!sip.getOwner().equals(owner)) {
                throw new IllegalArgumentException("You do not have permission to delete this entity.");
            }
            likeRepository.deleteBySipId(id);
            inviteRepository.deleteBySipId(id);
            sipRepository.deleteById(id);
            return true;
        }
        return false;
    }
}