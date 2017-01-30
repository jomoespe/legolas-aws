package com.josemorenoesteban.lab.legolas.analysis.amazon;

import com.amazonaws.services.rekognition.AmazonRekognitionClient;

public class DefaultConfiguration implements Configuration {
    private final AmazonRekognitionClient client = new AmazonRekognitionClient();
    
    @Override
    public AmazonRekognitionClient client() { return client; }
}
