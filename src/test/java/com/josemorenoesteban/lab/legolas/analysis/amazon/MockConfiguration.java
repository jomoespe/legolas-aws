package com.josemorenoesteban.lab.legolas.analysis.amazon;

import static java.util.Arrays.asList;

import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Label;

public class MockConfiguration implements Configuration {
    @Override
    public AmazonRekognitionClient client() {
        return new AmazonRekognitionClient() {
            @Override
            public DetectLabelsResult detectLabels(final DetectLabelsRequest detectLabelsRequest) {
                DetectLabelsResult result = new DetectLabelsResult();
                result.setLabels(asList(
                        label("Label_0", 0.0f),
                        label("Label_1", 0.1f),
                        label("Label_2", 0.2f),
                        label("Label_3", 0.3f),
                        label("Label_4", 0.4f),
                        label("Label_5", 0.5f),
                        label("Label_6", 0.6f),
                        label("Label_7", 0.7f),
                        label("Label_8", 0.8f),
                        label("Label_9", 0.9f)
                ));
                return result;
            }
        };
    }
    
    private Label label(final String name, final float confidence) {
        Label label = new Label();
        label.setName(name);
        label.setConfidence(confidence);
        return label;
    }
}
