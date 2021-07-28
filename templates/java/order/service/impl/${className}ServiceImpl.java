package @{package}.service.impl;


import @{package}.dao.@{table.className}Mapper;
import @{package}.entity.@{table.className};
import @{package}.service.@{table.className}Service;
import @{commonPackage}.vo.Grid;
import @{commonPackage}.vo.Response;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * @author @{author}
 */
@Service
@Transactional
public class @{table.className}ServiceImpl extends ServiceImpl<@{table.className}Mapper,@{table.className}> implements @{table.className}Service {
    private static final Logger logger = LoggerFactory.getLogger(@{table.className}ServiceImpl.class);

    @Override
    public Response<Grid<@{table.className}>> list(Page<@{table.className}> page, @{table.className} @{utils.toLowerCaseFirst(table.className)}){
        if(logger.isDebugEnabled()) {
            logger.debug("queryPage @{table.className} ,the entity is {},the page is {}",@{utils.toLowerCaseFirst(table.className)},page);
        }
        Response<Grid<@{table.className}>> res = new Response<>();
        List<@{table.className}> result = baseMapper.get@{table.className}Page(page,@{utils.toLowerCaseFirst(table.className)});
        Grid<@{table.className}> grid = new Grid<>();
        grid.setTotal(page.getTotal());
        grid.setPages(page.getPages());
        grid.setList(result);
        res.setData(grid);
        return res;
    }

}
