package com.skcc.rtspgw.repository;

import com.skcc.rtspgw.entity.CameraInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraInfoRepository extends JpaRepository<CameraInfo, Long> {
}
