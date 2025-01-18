package com.example.candid.Myql_POC;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface voltireInterface extends JpaRepository<VoltireModel, Integer> {
}
