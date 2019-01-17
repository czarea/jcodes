package cn.lvji.codes.util;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BeetlUtilTest {

    @Test
    public void generator() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", "'cn.lvji'");
        map.put("version", "'1.0'");
        BeetlUtil.generator("boot.template", "boot.txt", map);
    }
}