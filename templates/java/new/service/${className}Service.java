package @{package}.service;

import @{commonPackage}.vo.Grid;
import @{commonPackage}.vo.Response;
import @{package}.entity.@{table.className};
import @{package}.vo.@{table.className}VO;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 *
 * @author @{author}
 */
public interface @{table.className}Service extends IService<@{table.className}> {

    Response<Grid<@{table.className}>> page(@{table.className}VO @{utils.toLowerCaseFirst(table.className)});

}
