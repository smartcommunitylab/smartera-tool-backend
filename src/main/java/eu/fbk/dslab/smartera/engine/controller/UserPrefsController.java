package eu.fbk.dslab.smartera.engine.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.fbk.dslab.smartera.engine.model.UserPrefs;
import eu.fbk.dslab.smartera.engine.service.UserPrefsService;
import eu.fbk.security.UserContext;
import eu.fbk.security.UserInfo;

@RestController
@RequestMapping("/api/prefs")
public class UserPrefsController {
    @Autowired
    UserPrefsService userPrefsService;

    @GetMapping("/my")
    public ResponseEntity<Map<String, Object>> getUserPrefs() {
        UserInfo owner = UserContext.getOwner();
        UserPrefs prefs = userPrefsService.findByOwner(owner.getEmail());
        if (prefs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(prefs.getBody());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveUserPrefs(
            @RequestBody Map<String, Object> body) {
        UserInfo owner = UserContext.getOwner();
        UserPrefs prefs = userPrefsService.save(owner.getEmail(), body);
        return ResponseEntity.ok().body(prefs.getBody());
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteUserPrefs() {
        UserInfo owner = UserContext.getOwner();
        userPrefsService.deleteByOwner(owner.getEmail());
        return ResponseEntity.noContent().build();
    }
}
