package com.example.candid.Myql_POC;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PanglossService {

    private final voltireInterface voltireRepository;

    public PanglossService(voltireInterface voltireRepository) {
        this.voltireRepository = voltireRepository;
    }


    @Transactional
    @Cacheable(
            value = "voltireCache",
            key = "'model_' + #id",
            condition = "#id != null && #id > 0",
            unless = "#result == null"
    )
    public VoltireModel findModelById(Integer id) {
        log.info("Fetching model with ID: {} from database...", id);
        return voltireRepository.findById(id).orElse(null);
    }

    @Transactional
    @Cacheable(
            value = "voltireCache",
            key = "'allModels_' + T(java.time.LocalDate).now().toEpochDay()"
    )
    public List<VoltireModel> findAllModels() {
        log.info("Fetching all models from database...");
        return voltireRepository.findAll();
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "voltireCache", key = "'model_' + #model.id"),
            @CacheEvict(value = "voltireCache",
                    key = "'allModels_' + T(java.time.LocalDate).now().toEpochDay()",
                    condition = "#model != null")
    })
    public VoltireModel saveModel(VoltireModel model) {
        log.info("Saving model with ID: {}", model.getId());
        return voltireRepository.saveAndFlush(model);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "voltireCache", key = "'model_' + #id"),
            @CacheEvict(value = "voltireCache", allEntries = true, condition = "#id > 0")
    })
    public void deleteModelById(Integer id) {
        log.info("Deleting model with ID: {} from database...", id);
        voltireRepository.deleteById(id);
    }


}