package eu.fbk.dslab.smartera.engine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import eu.fbk.dslab.smartera.engine.model.SIP;
import eu.fbk.dslab.smartera.engine.service.SIPService;
import eu.fbk.security.UserContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sip")
public class SIPController {

    @Autowired
    private SIPService sipService;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEntityById(@PathVariable String id,
        HttpServletRequest request) {
        Optional<SIP> op = sipService.findById(UserContext.getOwner(request), id);
        if (op.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(op.get().getBody());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Map<String, Object>>> getByOwner(HttpServletRequest request) {
        String owner = UserContext.getOwner(request);
        List<SIP> sips = sipService.findByOwner(owner);
        List<Map<String, Object>> response = sips.stream()
            .map(SIP::getBody)
            .toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/invited")
    public ResponseEntity<List<Map<String, Object>>> getByInvitedUser(HttpServletRequest request) {
        String owner = UserContext.getOwner(request);
        List<SIP> sips = sipService.findByInvitedUser(owner);
        List<Map<String, Object>> response = sips.stream()
            .map(SIP::getBody)
            .toList();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createEntity(
        @RequestParam String id,
        @RequestParam String inviteCode,
        @RequestBody Map<String, Object> body, 
        HttpServletRequest request) {
            String owner = UserContext.getOwner(request);
            SIP sip = sipService.save(owner, id, inviteCode, body);
            return ResponseEntity.ok().body(sip.getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEntity(
        @PathVariable String id, 
        Map<String, Object> body, 
        HttpServletRequest request) {
            String owner = UserContext.getOwner(request);
            SIP sip = sipService.save(owner, id, null, body);
            return ResponseEntity.ok().body(sip.getBody());
    }

    @PutMapping("/{id}/invite-code")
    public ResponseEntity<Void> updateInviteCode(
        @PathVariable String id, 
        @RequestParam String inviteCode, 
        HttpServletRequest request) {
        String owner = UserContext.getOwner(request);
        try {
            sipService.updateInvitationCode(owner, id, inviteCode);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable String id, HttpServletRequest request) {
        String owner = UserContext.getOwner(request);
        if (sipService.deleteById(owner, id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    } 
}