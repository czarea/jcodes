package cn.lvji.codes;

import java.io.IOException;

/**
 * boot class
 *
 * @author zhouzx
 */
public class Bootstrap {

    public static void main(String[] args) throws IOException, InterruptedException {
        Producer producer = new Producer();
        producer.produceProject();
    }

}
