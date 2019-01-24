package @{package}.service;


import cn.lvji.core.vo.Grid;
import cn.lvji.core.vo.Response;
import @{package}.entity.@{table.className};
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.plugins.Page;


/**
 * 
 *
 * @author @{author}
 */
public interface @{table.className}Service extends IService<@{table.className}> {

    Response<Grid<@{table.className}>> list(Page<@{table.className}> page, @{table.className} @{utils.toLowerCaseFirst(table.className)});

}