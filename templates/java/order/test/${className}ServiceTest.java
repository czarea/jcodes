package @{package}.service;

import com.alibaba.fastjson.JSONObject;
import @{package}.AbstractJunit;
import @{package}.common.vo.ResultInfo;
import @{package}.service.@{table.className}Service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 *  @author @{author}
 */
public class @{table.className}ServiceTest extends AbstractJunit  {
    @Autowired
    private @{table.className}Service @{utils.toLowerCaseFirst(table.className)}Service;
    
    @Test
    public void testAdd(){
    }
    
    @Test
    public void testQuery(){
    }
    
    @Test
    public void testQueryPage(){
        
    }
    
    @Test
    public void testUpdate(){
        
    }
    
    @Test
    public void testDelete(){
        
    }
}