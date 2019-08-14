package @{package}.controller;

import cn.lvji.order.garden.vo.PageRequest;
import cn.lvji.order.garden.vo.Grid;
import cn.lvji.order.garden.vo.Response;
import @{package}.entity.@{table.className};
import @{package}.service.@{table.className}Service;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author @{author}
 */
@RestController
public class @{table.className}Controller {
    public class Page extends PageRequest<@{table.className}>{
    }

    private @{table.className}Service @{utils.toLowerCaseFirst(table.className)}ServiceImpl;

    @Autowired
    private @{table.className}Controller(@{table.className}Service @{utils.toLowerCaseFirst(table.className)}ServiceImpl){
        this.@{utils.toLowerCaseFirst(table.className)}ServiceImpl = @{utils.toLowerCaseFirst(table.className)}ServiceImpl;
    }

    @PostMapping("/pt/@{utils.toLowerCaseFirst(table.className)}")
    public Response<Void> save(@RequestBody @{table.className} @{utils.toLowerCaseFirst(table.className)}){
        @{utils.toLowerCaseFirst(table.className)}ServiceImpl.save(@{utils.toLowerCaseFirst(table.className)});
        return Response.SUCCESS;
    }

    @GetMapping("/pt/@{utils.toLowerCaseFirst(table.className)}/{id}")
    public Response<@{table.className}> get(@PathVariable("id") Long id){
        Response<@{table.className}> res = new Response<>();
        @{table.className} @{utils.toLowerCaseFirst(table.className)} = @{utils.toLowerCaseFirst(table.className)}ServiceImpl.getById(id);
        res.setData(@{utils.toLowerCaseFirst(table.className)});
        return res;
    }

    @GetMapping("/pt/@{utils.toLowerCaseFirst(table.className)}/action/search")
    public Response<Grid<@{table.className}>> list(Page request){
        return @{utils.toLowerCaseFirst(table.className)}ServiceImpl.list(request.buildPage(), request.getFilter());
    }

    @PutMapping("/pt/@{utils.toLowerCaseFirst(table.className)}")
    public Response<Void> update(@RequestBody @{table.className} param){
    	param.setUpdateTime(new Date());
        @{utils.toLowerCaseFirst(table.className)}ServiceImpl.saveOrUpdate(param);
        return Response.SUCCESS;
    }

    @DeleteMapping("/pv/@{utils.toLowerCaseFirst(table.className)}/{id}")
    public Response<Void> delete(@PathVariable("id") Long id){
        @{utils.toLowerCaseFirst(table.className)}ServiceImpl.removeById(id);
        return Response.SUCCESS;
    }
}
