package eu.fbk.dslab.smartera.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import eu.fbk.dslab.smartera.engine.model.SIP;
import eu.fbk.dslab.smartera.engine.repository.SIPRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SIPService {

    private final SIPRepository entityRepository;

    @Autowired
    public SIPService(SIPRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public List<SIP> findByOwner(String owner) {
        return entityRepository.findByOwner(owner);
    }

    public Optional<SIP> findById(String owner, String id) {
        SIP sip = entityRepository.findById(id).orElse(null);
        if (sip != null && !sip.getOwner().equals(owner)) {
            return Optional.empty(); // User does not own this entity
        }
        return entityRepository.findById(id);
    }

    public SIP save(String owner, String id, Map<String, Object> body) throws IllegalArgumentException {
        SIP sip = entityRepository.findById(id).orElse(null);
        if (sip != null && !sip.getOwner().equals(owner)) {
            throw new IllegalArgumentException("You do not have permission to modify this entity.");
        }
        SIP entity = new SIP(id, owner, body);
        return entityRepository.save(entity);
    }

    public void deleteById(String owner, String id) throws IllegalArgumentException {
        SIP sip = entityRepository.findById(id).orElse(null);
        if (sip !=  null) {
            if (!sip.getOwner().equals(owner)) {
                throw new IllegalArgumentException("You do not have permission to delete this entity.");
            }
            entityRepository.deleteById(id);
        }
    }
}