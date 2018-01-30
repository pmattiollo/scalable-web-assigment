package com.wearewaes.service;

import com.wearewaes.model.InputType;
import com.wearewaes.model.JSONData;
import com.wearewaes.model.JSONDataDTO;
import com.wearewaes.repository.JSONDataRepository;
import com.wearewaes.service.exception.EmptyJsonDataException;
import com.wearewaes.service.exception.FileAlreadyExistsException;
import com.wearewaes.service.exception.IDNotFoundException;
import com.wearewaes.service.exception.NumberOfFilesToDiffException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

import static com.wearewaes.model.InputType.LEFT;
import static com.wearewaes.model.InputType.RIGHT;

@Service
public class JSONDataDiffServiceImpl implements JSONDataDiffService {

    private JSONDataRepository jsonDataRepository;

    public JSONDataDiffServiceImpl(JSONDataRepository jsonDataRepository) {
        this.jsonDataRepository = jsonDataRepository;
    }

    public JSONData saveLeft(Long id, JSONDataDTO jsonDataDTO) {
       return saveFile(id, jsonDataDTO, LEFT);
    }

    public JSONData saveRight(Long id, JSONDataDTO jsonDataDTO) {
        return saveFile(id, jsonDataDTO, RIGHT);
    }

    public String getJSONDiffResult(Long id) {
        return compareJSONData(id);
    }

    private JSONData saveFile(Long id, JSONDataDTO jsonDataDTO, InputType type) {
        String data = jsonDataDTO.getValue();

        if (data == null || data.isEmpty()) {
            throw new EmptyJsonDataException();
        }

        JSONData jsonData = jsonDataRepository.findByDiffId(id);

        if (jsonData != null) {
            if ((jsonData.hasLeftData() && type.equals(LEFT) || (jsonData.hasRightData() && type.equals(RIGHT)))) {
                throw new FileAlreadyExistsException(id, type);
            }
        } else {
            jsonData = new JSONData(id);
        }

        if (type.equals(LEFT)) {
            jsonData.setLeft(data);
        } else {
            jsonData.setRight(data);
        }

        return jsonDataRepository.save(jsonData);
    }

    private String compareJSONData(Long id) {
        Optional<JSONData> optionalJSONData = Optional.ofNullable(jsonDataRepository.findByDiffId(id));

        JSONData jsonData = optionalJSONData.orElseThrow(() -> new IDNotFoundException(id));

        if (!jsonData.hasLeftData() || !jsonData.hasRightData()) {
            throw new NumberOfFilesToDiffException(id, jsonData.hasLeftData(), jsonData.hasRightData());
        }

        byte[] leftData = jsonData.getLeft().getBytes();
        byte[] rightData = jsonData.getRight().getBytes();

        if (Arrays.equals(leftData, rightData)) {
            return "They are equal";
        } else if (leftData.length != rightData.length) {
            return "They have different sizes. Left JSON size: " + leftData.length + ", Right JSON size: " + rightData.length;
        } else {
            int size = leftData.length;
            StringBuilder builder = new StringBuilder("They have same sizes, but their content are different. Wrong bytes offsets: ");

            for (int i=0;i<size;i++) {
                if (leftData[i] != rightData[i]) {
                    builder.append(i);
                    builder.append(", ");
                }
            }

            return builder.toString().substring(0, builder.length() - 2);
        }
    }

}
