package eu.fbk.dslab.smartera.engine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import eu.fbk.dslab.smartera.engine.model.SIP;
import eu.fbk.dslab.smartera.engine.security.UserContext;
import eu.fbk.dslab.smartera.engine.security.UserInfo;
import eu.fbk.dslab.smartera.engine.service.SIPService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sip")
public class SIPController {

    @Autowired
    private SIPService sipService;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEntityById(
            @PathVariable String id) {
        UserInfo owner = UserContext.getOwner();
        Optional<SIP> op = sipService.findById(owner.getEmail(), id);
        if (op.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(op.get().getBody());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Map<String, Object>>> getByOwner() {
        UserInfo owner = UserContext.getOwner();
        List<SIP> sips = sipService.findByOwner(owner.getEmail());
        List<Map<String, Object>> response = sips.stream()
            .map(SIP::getBody)
            .toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/invited")
    public ResponseEntity<List<Map<String, Object>>> getByInvitedUser() {
        UserInfo owner = UserContext.getOwner();
        List<SIP> sips = sipService.findByInvitedUser(owner.getEmail());
        List<Map<String, Object>> response = sips.stream()
            .map(SIP::getBody)
            .toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/published")
    public ResponseEntity<List<Map<String, Object>>> getPublished() {
        List<SIP> sips = sipService.findPublished();
        List<Map<String, Object>> response = sips.stream()
            .map(SIP::getBody)
            .toList();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createEntity(
            @RequestParam String id,
            @RequestParam String inviteCode,
            @RequestBody Map<String, Object> body) {
        UserInfo owner = UserContext.getOwner();
        SIP sip = sipService.save(owner.getEmail(), id, inviteCode, body);
        return ResponseEntity.ok().body(sip.getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEntity(
            @PathVariable String id, 
            @RequestBody Map<String, Object> body) {
        UserInfo owner = UserContext.getOwner();
        SIP sip = sipService.save(owner.getEmail(), id, null, body);
        return ResponseEntity.ok().body(sip.getBody());
    }

    @PutMapping("/{id}/invite-code")
    public ResponseEntity<Void> updateInviteCode(
            @PathVariable String id, 
            @RequestParam String inviteCode) {
        UserInfo owner = UserContext.getOwner();
        try {
            sipService.updateInvitationCode(owner.getEmail(), id, inviteCode);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<Void> publishEntity(
            @PathVariable String id, 
            @RequestParam boolean published) {
        UserInfo owner = UserContext.getOwner();
        try {
            sipService.publishSIP(owner.getEmail(), id, published);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(
            @PathVariable String id) {
        UserInfo owner = UserContext.getOwner();
        if (sipService.deleteById(owner.getEmail(), id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    } 
}