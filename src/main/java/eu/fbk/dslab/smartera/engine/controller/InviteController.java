package eu.fbk.dslab.smartera.engine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.fbk.dslab.smartera.engine.model.Invite;
import eu.fbk.dslab.smartera.engine.service.InviteService;
import eu.fbk.security.UserContext;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/invite")
public class InviteController {
    
    @Autowired
    InviteService inviteService;

    @GetMapping("/my")
    public ResponseEntity<List<Invite>> getMyInvites(HttpServletRequest request) {
        String owner = UserContext.getOwner(request);
        List<Invite> invites = inviteService.findByOwner(owner);
        return ResponseEntity.ok().body(invites);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteInvite(
        @RequestParam String sipId, 
        @RequestParam String invitedUser, 
        HttpServletRequest request) {
            String owner = UserContext.getOwner(request);
            inviteService.deleteInvite(owner, sipId, invitedUser);
        return ResponseEntity.noContent().build();
    }

}
