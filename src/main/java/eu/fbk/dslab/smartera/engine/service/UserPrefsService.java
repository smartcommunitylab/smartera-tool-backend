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

    public UserPrefs save(String owner, Map<String, Object> body) {
        UserPrefs prefs = entityRepository.findOneByOwner(owner);
        if (prefs == null) {
            prefs = new UserPrefs(owner, body);
        } else {
            prefs.setBody(body);    
        }
        return entityRepository.save(prefs);
    }

    public void deleteByOwner(String owner) {
        UserPrefs entity = entityRepository.findOneByOwner(owner);
        if (entity !=  null) {
            entityRepository.deleteById(entity.getId());
        }
    }
}