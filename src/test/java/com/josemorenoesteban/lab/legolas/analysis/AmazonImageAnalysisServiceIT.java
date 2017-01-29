package com.josemorenoesteban.lab.legolas.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.function.Function;
import org.junit.Before;

import org.junit.Test;

public class AmazonImageAnalysisServiceIT {
    private ImageAnalysisService service;   // The SUT 
    
    @Before
    public void setup() {
        service = ImageAnalysisService.byName("amazon-rekognition");
    }
    
    @Test
    public void canLoadServiceByName() {
        assertNotNull( service );
    }
    
    @Test
    public void canAnalyseChucho() {
        ImageAnalysisResult result  = service.analyse(() -> forImage.apply("/chucho.jpg"));
        assertNotNull(result);
        assertEquals(0f, result.adultContentScore(), 0);
        assertEquals(10, result.labels().size());
    }

    // Helper functions
    
    private final Function<String, File> fromClassLoader = filename -> {
        try {
            return new File(this.getClass().getResource(filename).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    };
    
    private final Function<File, ByteBuffer> buffer = file -> {
        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            return ByteBuffer.wrap(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
    
    private final Function<String, ByteBuffer> forImage = name -> buffer.compose(fromClassLoader).apply(name);
}
