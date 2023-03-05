package org.carranza.msvc.gestion.ventas.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GenericService<T>{
    Page<T> findAllPage( Pageable paginador);

    List<T> findAll();

    T findById(UUID id);

    T save(T t);

    T update(UUID id, T t);

    Boolean delete (UUID id, T t);
}

