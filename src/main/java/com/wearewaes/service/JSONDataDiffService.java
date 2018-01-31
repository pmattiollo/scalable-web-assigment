package com.wearewaes.service;

import com.wearewaes.model.JSONData;
import com.wearewaes.model.JSONDataDTO;

/**
 * Service interface used for data upload and comparison
 */
public interface JSONDataDiffService {

    public JSONData saveLeft(Long id, JSONDataDTO jsonDataDTO);

    public JSONData saveRight(Long id, JSONDataDTO jsonDataDTO);

    public String getJSONDiffResult(Long id);

}
