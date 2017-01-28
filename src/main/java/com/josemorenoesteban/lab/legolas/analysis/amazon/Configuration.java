package com.josemorenoesteban.lab.legolas.analysis.amazon;

import static com.josemorenoesteban.lab.legolas.analysis.Environment.asInteger;
import static com.josemorenoesteban.lab.legolas.analysis.Environment.asFloat;

import static java.util.Objects.requireNonNull;

import com.amazonaws.services.rekognition.AmazonRekognitionClient;

import java.util.ServiceLoader;

public interface Configuration {
    static final String  MAX_LABELS_ENV         = "MAX_LABELS";
    static final String  MIN_CONFIDENCE_ENV     = "MIN_CONFIDENCE";
    static final Integer DEFAULT_MAX_LABELS     = 20;
    static final Float   DEFAULT_MIN_CONFIDENCE = 50f;
    
    static Configuration instance() { 
        final ServiceLoader<Configuration> loader = ServiceLoader.load(Configuration.class);
        return requireNonNull(loader.iterator().next(), 
                              "No environment instance class found");  // TODO review this message
    }
    
    default Integer maxLabels() { 
        return asInteger.apply(MAX_LABELS_ENV).orElse(DEFAULT_MAX_LABELS); 
    }

    default Float minConfidence() {  
        return asFloat.apply(MIN_CONFIDENCE_ENV).orElse(DEFAULT_MIN_CONFIDENCE); 
    }

    AmazonRekognitionClient client();
}
