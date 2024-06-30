package com.calise.tb_backend.dao;

import com.calise.tb_backend.models.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoDAO extends JpaRepository<Video, Integer> {
}
