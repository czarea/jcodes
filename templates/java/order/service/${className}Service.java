package @{package}.service;


import @{package}.entity.@{table.className};
import @{commonPackage}.vo.Grid;
import @{commonPackage}.vo.Response;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 *
 *
 * @author @{author}
 */
public interface @{table.className}Service extends IService<@{table.className}> {

    Response<Grid<@{table.className}>> list(Page<@{table.className}> page, @{table.className} @{utils.toLowerCaseFirst(table.className)});

}
