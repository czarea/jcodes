package cn.lvji.codes;

import org.junit.Test;

import java.io.IOException;

public class ProducerTest {

    @Test
    public void productProject() {
    }

    @Test
    public void productCodes() throws IOException {
        Producer producer = new Producer();
        producer.produceCodes();
    }
}