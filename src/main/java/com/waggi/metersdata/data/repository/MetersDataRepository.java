package com.waggi.metersdata.data.repository;

import com.waggi.metersdata.data.domain.MetersData;
import org.springframework.data.repository.CrudRepository;

public interface MetersDataRepository extends CrudRepository<MetersData, Long>, CustomMetersDataRepository {
}
