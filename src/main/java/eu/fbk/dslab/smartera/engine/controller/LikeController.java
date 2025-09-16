package eu.fbk.dslab.smartera.engine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.fbk.dslab.smartera.engine.model.Like;
import eu.fbk.dslab.smartera.engine.service.LikeService;
import eu.fbk.security.UserContext;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Autowired
    LikeService likeService;

    @GetMapping("/sip/{sipId}/owner")
    public ResponseEntity<List<Like>> getLikeByOwner(
            @PathVariable String sipId) {
        String owner = UserContext.getOwner();
        List<Like> likes = likeService.getLikesBySIPOwner(owner, sipId);
        return ResponseEntity.ok().body(likes);
    }

    @GetMapping("/sip/{sipId}")
    public ResponseEntity<List<Like>> getLikes(
            @PathVariable String sipId) {
        String owner = UserContext.getOwner();
        List<Like> likes = likeService.getLikes(owner, sipId);
        return ResponseEntity.ok().body(likes);
    }

    @PutMapping("/sip/{sipId}")
    public ResponseEntity<Like> addSipLike(
            @PathVariable String sipId) {
        String owner = UserContext.getOwner();
        Like like = likeService.addSipLike(owner, sipId);
        return ResponseEntity.ok().body(like);
    }

    @PutMapping("/sip/{sipId}/component/{componentId}")
    public ResponseEntity<Like> addSipLike(
            @PathVariable String sipId, 
            @PathVariable String componentId) {
        String owner = UserContext.getOwner();
        Like like = likeService.addComponentLike(owner, sipId, componentId);
        return ResponseEntity.ok().body(like);
    }

}
