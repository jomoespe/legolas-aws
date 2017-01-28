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
            public DetectLabelsResult detectLabels(DetectLabelsRequest detectLabelsRequest) {
                DetectLabelsResult result = new DetectLabelsResult();
                result.setLabels(asList(
                        label("Campo0", 0.0f),
                        label("Campo1", 0.1f),
                        label("Campo2", 0.2f),
                        label("Campo3", 0.3f),
                        label("Campo4", 0.4f),
                        label("Campo5", 0.5f),
                        label("Campo6", 0.6f),
                        label("Campo7", 0.7f),
                        label("Campo8", 0.8f),
                        label("Campo9", 0.9f)
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
