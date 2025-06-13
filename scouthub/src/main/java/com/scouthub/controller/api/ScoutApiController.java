package com.scouthub.controller.api;

import com.scouthub.model.Scout;
import com.scouthub.service.ScoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scouts")
public class ScoutApiController {

    private final ScoutService scoutService;

    @Autowired
    public ScoutApiController(ScoutService scoutService) {
        this.scoutService = scoutService;
    }

    @PostMapping
    public ResponseEntity<Scout> create(@RequestBody Scout scout) {
        return ResponseEntity.ok(scoutService.createScout(scout));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scout> get(@PathVariable Long id) {
        return scoutService.getScoutById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Scout> list() {
        return scoutService.getAllScouts();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scout> update(@PathVariable Long id, @RequestBody Scout scout) {
        return ResponseEntity.ok(scoutService.updateScout(id, scout));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scoutService.deleteScout(id);
        return ResponseEntity.noContent().build();
    }
}
