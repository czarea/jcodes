package @{package}.controller;

import @{commonPackage}.vo.Grid;
import @{commonPackage}.vo.Response;
import @{package}.entity.@{table.className};
import @{package}.service.@{table.className}Service;
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
    private final @{table.className}Service @{utils.toLowerCaseFirst(table.className)}ServiceImpl;

    public @{table.className}Controller(@{table.className}Service @{utils.toLowerCaseFirst(table.className)}ServiceImpl){
        this.@{utils.toLowerCaseFirst(table.className)}ServiceImpl = @{utils.toLowerCaseFirst(table.className)}ServiceImpl;
    }

    @PostMapping("/@{utils.toLowerCaseFirst(table.className)}")
    public Response<Void> save(@RequestBody @{table.className} @{utils.toLowerCaseFirst(table.className)}){
        @{utils.toLowerCaseFirst(table.className)}ServiceImpl.save(@{utils.toLowerCaseFirst(table.className)});
        return Response.SUCCESS;
    }

    @GetMapping("/@{utils.toLowerCaseFirst(table.className)}/{id}")
    public Response<@{table.className}> get(@PathVariable("id") Long id){
        Response<@{table.className}> res = new Response<>();
        @{table.className} @{utils.toLowerCaseFirst(table.className)} = @{utils.toLowerCaseFirst(table.className)}ServiceImpl.getById(id);
        res.setData(@{utils.toLowerCaseFirst(table.className)});
        return res;
    }

    @GetMapping("/@{utils.toLowerCaseFirst(table.className)}/search")
    public Response<Grid<@{table.className}>> page(@{table.className}VO request){
        return @{utils.toLowerCaseFirst(table.className)}ServiceImpl.page(request);
    }

    @PutMapping("/@{utils.toLowerCaseFirst(table.className)}")
    public Response<Void> update(@RequestBody @{table.className} param){
        @{utils.toLowerCaseFirst(table.className)}ServiceImpl.saveOrUpdate(param);
        return Response.SUCCESS;
    }

    @DeleteMapping("/@{utils.toLowerCaseFirst(table.className)}/{id}")
    public Response<Void> delete(@PathVariable("id") Long id){
        @{utils.toLowerCaseFirst(table.className)}ServiceImpl.removeById(id);
        return Response.SUCCESS;
    }
}
