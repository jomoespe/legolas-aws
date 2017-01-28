package com.josemorenoesteban.lab.legolas.analysis;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Properties;
import java.util.function.Function;

import org.junit.BeforeClass;
import org.junit.Test;

public class AmazonImageAnalysisServiceIT {
    private static final String  ACCESS_KEY_PROPERTY        = "aws.accessKeyId";
    private static final String  SECRET_KEY_ACCESS_PROPERTY = "aws.secretKey";
    private static final String  ACCESS_KEY                 = "AKIAJRTXVTJ2G5EIPFVQ";                     // This field should be configured
    private static final String  SECRET_KEY_ACCESS          = "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"; // This field should be configured

    @BeforeClass
    public static void beforeClass() {
        Properties props = System.getProperties();
        props.setProperty(ACCESS_KEY_PROPERTY,        ACCESS_KEY);
        props.setProperty(SECRET_KEY_ACCESS_PROPERTY, SECRET_KEY_ACCESS);
    }

    @Test
    public void canLoadServiceByName() {
        assertNotNull( ImageAnalysisService.byName("amazon-rekognition") );
    }
    
    @Test
    public void canAnalyseChucho() {
        ImageAnalysisService service = ImageAnalysisService.byName("amazon-rekognition");
        ImageAnalysisResult  result  = service.analyse(() -> buffer.compose(fromClassLoader).apply("/chucho.jpg"));
        assertNotNull(result);
        //System.out.printf("is adultContent? %s\n", result.adultContentScore());
        //result.labels().forEach( (label, score) -> System.out.printf("%s=%s\n", label, score) );
    }

    private final Function<String, File> fromClassLoader = (filename) -> {
        try {
            return new File(this.getClass().getResource(filename).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    };
    
    private final Function<File, ByteBuffer> buffer = (file) -> {
        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            return ByteBuffer.wrap(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
}
