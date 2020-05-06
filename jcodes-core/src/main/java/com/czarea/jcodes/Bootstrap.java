package com.czarea.jcodes;

import com.czarea.jcodes.util.StringUtils;

import java.io.IOException;

/**
 * boot class
 *
 * @author zhouzx
 */
public class Bootstrap {

    public static void main(String[] args) throws IOException, InterruptedException {
        Producer producer = new Producer();
        if (args.length <= 0) {
            System.err.println("请携带参数！");
            help();
            System.exit(-1);
        }
        String command = args[0];
        if (command.equals("d")) {
            producer.deleteOutput();
        } else {
            String param = args[1];
            if (command.equals("p") && !StringUtils.isEmpty(param) && param.equals("pj")) {
                producer.produceProject();
            } else {
                producer.produceCodes();
            }
        }
    }


    private static void help() {
        System.out.println(
                "参数：p pj    （创建项目）\n" +
                "      p code  （生产代码）\n" +
                "      d       （删除代码）");
    }

}
