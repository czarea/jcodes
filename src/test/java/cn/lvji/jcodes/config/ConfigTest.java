package cn.lvji.jcodes.config;

import cn.lvji.jcodes.model.Codes;
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