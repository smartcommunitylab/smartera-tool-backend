package eu.fbk.dslab.smartera.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import eu.fbk.dslab.smartera.engine.model.UserPrefs;
import eu.fbk.dslab.smartera.engine.repository.UserPrefsRepository;

import java.util.Map;

@Service
public class UserPrefsService {

    private final UserPrefsRepository entityRepository;

    @Autowired
    public UserPrefsService(UserPrefsRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public UserPrefs findByOwner(String owner) {
        return entityRepository.findOneByOwner(owner);
    }

    public UserPrefs save(String owner, String id, Map<String, Object> body) throws IllegalArgumentException {
        UserPrefs prefs = entityRepository.findById(id).orElse(null);
        if (prefs != null && !prefs.getOwner().equals(owner)) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        UserPrefs entity = new UserPrefs(id, owner, body);
        return entityRepository.save(entity);
    }

    public void deleteById(String owner, String id) throws IllegalArgumentException {
        UserPrefs entity = entityRepository.findById(id).orElse(null);
        if (entity !=  null) {
            if (!entity.getOwner().equals(owner)) {
                throw new IllegalArgumentException("You do not have permission to delete this entity.");
            }
            entityRepository.deleteById(id);
        }
    }
}