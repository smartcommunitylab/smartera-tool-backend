package eu.fbk.dslab.smartera.engine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.fbk.dslab.smartera.engine.model.Invite;
import eu.fbk.dslab.smartera.engine.service.InviteService;
import eu.fbk.security.UserContext;
import eu.fbk.security.UserInfo;

@RestController
@RequestMapping("/api/invite")
public class InviteController {
    
    @Autowired
    InviteService inviteService;

    @GetMapping("/my")
    public ResponseEntity<List<Invite>> getMyInvites() {
        UserInfo owner = UserContext.getOwner();
        List<Invite> invites = inviteService.findByOwner(owner.getEmail());
        return ResponseEntity.ok().body(invites);
    }

    @GetMapping("/sip/{id}")
    public ResponseEntity<List<Invite>> getSipInvites(
            @PathVariable String id) {
        UserInfo owner = UserContext.getOwner();
        List<Invite> invites = inviteService.findByOwnerAndSip(owner.getEmail(), id);
        return ResponseEntity.ok().body(invites);
    }

    @PutMapping("/accept")
    public ResponseEntity<Invite> acceptInvite(
            @RequestParam String inviteCode) {
        UserInfo invitedUser = UserContext.getOwner();
        try {
            Invite invite = inviteService.saveInvite(inviteCode, invitedUser.getEmail());
            return ResponseEntity.ok().body(invite);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteInvite(
            @RequestParam String sipId, 
            @RequestParam String invitedUser) {
        UserInfo owner = UserContext.getOwner();
        inviteService.deleteInvite(owner.getEmail(), sipId, invitedUser);
        return ResponseEntity.noContent().build();
    }

}
