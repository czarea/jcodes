package cn.lvji.codes.config;

import cn.lvji.codes.model.Codes;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConfigTest {

    @Test
    public void yamlInit() {
        Codes codes = Config.yamlInit();
        System.out.println(codes);
        assertNotNull(codes);
    }
}