package @{package}.mapper;

import @{package}.entity.@{table.className};
import @{package}.vo.@{table.className}VO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author @{author}
 */
public interface @{table.className}Mapper extends BaseMapper<@{table.className}> {

    List<@{table.className}> page(Page<@{table.className}> page,@Param("@{utils.toLowerCaseFirst(table.className)}") @{table.className}VO @{utils.toLowerCaseFirst(table.className)});
}
