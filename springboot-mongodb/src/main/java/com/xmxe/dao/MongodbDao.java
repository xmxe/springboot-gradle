package com.xmxe.dao;

import com.xmxe.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongodbDao extends MongoRepository<Student, Integer> {
}
