package com.waggi.metersdata.data.repository;

import com.waggi.metersdata.data.FilterMetersData;
import com.waggi.metersdata.data.domain.User;

public interface CustomMetersDataRepository {
    Object[] findAllByFilter(FilterMetersData filter, User user);
}
