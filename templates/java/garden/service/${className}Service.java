package @{package}.service;


import cn.lvji.order.garden.vo.Grid;
import cn.lvji.order.garden.vo.Response;
import @{package}.entity.@{table.className};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 *
 * @author @{author}
 */
public interface @{table.className}Service extends IService<@{table.className}> {

    Response<Grid<@{table.className}>> list(Page<@{table.className}> page, @{table.className} @{utils.toLowerCaseFirst(table.className)});

}
