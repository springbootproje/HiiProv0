package com.java.app.ws.repository;

import com.java.app.ws.entity.TacheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TacheRepository extends CrudRepository<TacheEntity,Long> {


}
