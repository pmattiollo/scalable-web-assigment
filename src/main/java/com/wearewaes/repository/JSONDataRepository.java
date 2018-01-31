package com.wearewaes.repository;

import com.wearewaes.model.JSONData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A repository that manipulates all {@link JSONData} data over the database
 */
public interface JSONDataRepository extends JpaRepository<JSONData, Long> {

    /**
     * Retrieves a database registry finding by the user specified ID
     *
     * @param diffId that represents the user specified ID
     * @return the {@link JSONData} related
     */
    public JSONData findByDiffId(Long diffId);

}
