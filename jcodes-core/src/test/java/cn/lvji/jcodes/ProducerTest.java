package cn.lvji.jcodes;

import org.junit.Test;

import java.io.IOException;

public class ProducerTest {

    @Test
    public void productProject() throws IOException, InterruptedException {
        Producer producer = new Producer();
        producer.produceProject();
    }

    @Test
    public void productCodes() throws IOException {
        Producer producer = new Producer();
        producer.produceCodes();
    }
}