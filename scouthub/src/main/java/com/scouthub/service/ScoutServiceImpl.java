package com.scouthub.service;

import com.scouthub.model.Scout;
import com.scouthub.repository.ScoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoutServiceImpl implements ScoutService{

    private final ScoutRepository scoutRepository;

    @Autowired
    public ScoutServiceImpl(ScoutRepository scoutRepository) {
        this.scoutRepository = scoutRepository;
    }


    @Override
    public Scout createScout(Scout scout) {
        return scoutRepository.save(scout);
    }

    @Override
    public Optional<Scout> getScoutById(Long id) {
        return scoutRepository.findById(id);
    }

    @Override
    public List<Scout> getAllScouts() {
        return scoutRepository.findAll();
    }

    @Override
    public Scout updateScout(Long id, Scout updatedScout) {
        updatedScout.setId(id);
        return scoutRepository.save(updatedScout);
    }

    @Override
    public void deleteScout(Long id) {
        scoutRepository.deleteById(id);
    }
}
