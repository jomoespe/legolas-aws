package com.josemorenoesteban.lab.legolas.analysis.amazon;

import static java.util.stream.Collectors.toMap;

import com.josemorenoesteban.lab.legolas.analysis.ImageAnalysisResult;
import com.josemorenoesteban.lab.legolas.analysis.ImageAnalysisService;

import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class AmazonImageAnalysisService implements ImageAnalysisService {
    private static final String NAME                        = "amazon-rekognition";
    private static final Float  DEFAULT_ADULT_CONTENT_SCORE =  0f; // Amazon doesn't gives any information about adult content of an image

    private final Configuration conf = Configuration.instance();

    private final Function<Supplier<ByteBuffer>, Image> image = imageBytes -> 
        new Image()
        .withBytes(imageBytes.get());
    
    private final Function<Image, DetectLabelsRequest> createAwsRequest = theImage -> 
        new DetectLabelsRequest()
        .withImage(theImage)
        .withMaxLabels(conf.maxLabels())
        .withMinConfidence(conf.minConfidence());
    
    private final Function<DetectLabelsRequest, DetectLabelsResult> callAws = request -> 
        conf.client()
        .detectLabels(request);
    
    private Function<DetectLabelsResult, Map<String, Float>> labels = result -> 
        result
        .getLabels()
        .stream()
        .collect(toMap(Label::getName, Label::getConfidence));

    private final Function<DetectLabelsResult, ImageAnalysisResult> adaptor = result -> 
        new ImageAnalysisResult( labels.apply(result), DEFAULT_ADULT_CONTENT_SCORE );

    private final Function<Supplier<ByteBuffer>, ImageAnalysisResult> analyzer = 
        adaptor.compose(callAws).compose(createAwsRequest).compose(image);
      
    @Override
    public String name() { return NAME; }

    @Override
    public ImageAnalysisResult analyse(final Supplier<ByteBuffer> imageBytes) { return analyzer.apply(imageBytes); }
}
