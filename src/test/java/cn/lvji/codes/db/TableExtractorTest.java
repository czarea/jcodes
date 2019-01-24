package cn.lvji.codes.db;

import cn.lvji.codes.config.Config;
import cn.lvji.codes.model.Codes;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TableExtractorTest {

    @Test
    public void getTable() {
        Codes codes = Config.yamlInit();
        CodesDataSource.getInstance().init(codes);
        TableExtractor tableExtractor = new TableExtractor();
        Table table = tableExtractor.getTable("eo_api");
        assertNotNull(table);
    }
}