package com.fiap.tech.infra.services;

import java.util.Optional;

import com.fiap.tech.domain.resource.Resource;

public interface StorageService {

    Optional<Resource> get(String name);

    void store(Resource resource);
}