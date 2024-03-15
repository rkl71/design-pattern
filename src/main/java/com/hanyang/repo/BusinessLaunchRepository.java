package com.hanyang.repo;

import com.hanyang.pojo.BusinessLaunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessLaunchRepository extends JpaRepository<BusinessLaunch, Integer> {
}
