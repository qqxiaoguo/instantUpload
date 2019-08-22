package com.lpjj.application.dao;

import com.lpjj.application.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * com.lpjj.application.dao
 * 黄新乐
 * 2019/7/18
 */
public interface HistoryDao extends JpaRepository<History, Integer> {
}
