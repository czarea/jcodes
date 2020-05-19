package @{package}.service.impl;

import @{package}.mapper.@{table.className}Mapper;
import @{package}.vo.@{table.className}VO;
import @{package}.entity.@{table.className};
import @{package}.service.@{table.className}Service;
import @{commonPackage}.vo.Grid;
import @{commonPackage}.vo.Response;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author @{author}
 */
@Service
public class @{table.className}ServiceImpl extends ServiceImpl<@{table.className}Mapper,@{table.className}> implements @{table.className}Service {

    @Override
    public Response<Grid<@{table.className}>> page(@{table.className}VO req){
        Response<Grid<@{table.className}>> res = new Response<>();
        Page<@{table.className}> page = req.page();
        List<@{table.className}> result = baseMapper.page(page,req);
        Grid<@{table.className}> grid = new Grid<>();
        grid.setTotal(page.getTotal());
        grid.setPages(page.getPages());
        grid.setList(result);
        res.setData(grid);
        return res;
    }

}
