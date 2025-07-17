package eu.fbk.dslab.smartera.engine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import eu.fbk.dslab.smartera.engine.service.SIPService;

import java.util.List;

@RestController
@RequestMapping("/api/sip")
public class SIPController {

    @Autowired
    private SIPService entityService;

/*     @GetMapping
    public List<SIP> getAllEntities() {
        return entityService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SIP> getEntityById(@PathVariable String id) {
        return entityService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SIP createEntity(@RequestBody SIP entity) {
        return entityService.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SIP> updateEntity(@PathVariable String id, @RequestBody SIP entity) {
        return entityService.update(id, entity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable String id) {
        if (entityService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    } */
}