package @{package}.dao;

import @{package}.entity.@{table.className};
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author @{author}
 */
public interface @{table.className}Mapper extends BaseMapper<@{table.className}> {
    
    List<@{table.className}> get@{table.className}Page(Page<@{table.className}> page,@Param("@{utils.toLowerCaseFirst(table.className)}") @{table.className} @{utils.toLowerCaseFirst(table.className)});
}
