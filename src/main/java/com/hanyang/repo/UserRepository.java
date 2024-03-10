package com.hanyang.repo;

import com.hanyang.pojo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    UserInfo findByUserName(String userName);

    UserInfo findByUserNameAndUserPassword(String account, String password);
}
