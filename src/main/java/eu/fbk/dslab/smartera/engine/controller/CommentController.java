package eu.fbk.dslab.smartera.engine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import eu.fbk.dslab.smartera.engine.model.Comment;
import eu.fbk.dslab.smartera.engine.security.UserContext;
import eu.fbk.dslab.smartera.engine.security.UserInfo;
import eu.fbk.dslab.smartera.engine.service.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/sip/{sipId}/owner")
    public ResponseEntity<List<Comment>> getCommentsByOwner(
            @PathVariable String sipId) {
        UserInfo owner = UserContext.getOwner();
        List<Comment> comments = commentService.getCommentsBySIPOwner(owner, sipId);
        return ResponseEntity.ok().body(comments);
    }

    @GetMapping("/sip/{sipId}")
    public ResponseEntity<List<Comment>> getComments(
            @PathVariable String sipId) {
        UserInfo owner = UserContext.getOwner();
        List<Comment> comments = commentService.getComments(owner, sipId);
        return ResponseEntity.ok().body(comments);
    }

    @PutMapping("/sip/{sipId}")
    public ResponseEntity<Comment> addSipComment(
            @PathVariable String sipId,
            @RequestParam String text) {
        UserInfo owner = UserContext.getOwner();
        Comment comment = commentService.addSipComment(owner, sipId, text);
        return ResponseEntity.ok().body(comment);
    }

    @PutMapping("/sip/{sipId}/component/{componentId}")
    public ResponseEntity<Comment> addComponentComment(
            @PathVariable String sipId,
            @PathVariable String componentId,
            @RequestParam String text) {
        UserInfo owner = UserContext.getOwner();
        Comment comment = commentService.addComponentComment(owner, sipId, componentId, text);
        return ResponseEntity.ok().body(comment);
    }
}