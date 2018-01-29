package com.wearewaes.repository;

import com.wearewaes.model.JSONData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JSONDataRepository extends JpaRepository<JSONData, Long> {

    public JSONData findByDiffId(Long diffId);

}
