package com.lpjj.application.dao;

import com.lpjj.application.entity.parameters.JsonRootBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * com.lpjj.application.dao
 * 黄新乐
 * 2019/6/24
 */

public interface UploadDao extends JpaRepository<JsonRootBean, Integer> {

}
