package @{package}.controller;

import @{commonPackage}.vo.Grid;
import @{commonPackage}.vo.Response;
import @{package}.entity.@{table.className};
import @{package}.vo.@{table.className}VO;
import @{package}.service.@{table.className}Service;
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
    private final @{table.className}Service @{utils.toLowerCaseFirst(table.className)}Service;

    public @{table.className}Controller(@{table.className}Service @{utils.toLowerCaseFirst(table.className)}Service){
        this.@{utils.toLowerCaseFirst(table.className)}Service = @{utils.toLowerCaseFirst(table.className)}Service;
    }

    @PostMapping("/@{table.restfulUri}s")
    public Response<Void> save(@RequestBody @{table.className} @{utils.toLowerCaseFirst(table.className)}){
        @{utils.toLowerCaseFirst(table.className)}Service.save(@{utils.toLowerCaseFirst(table.className)});
        return Response.SUCCESS;
    }

    @GetMapping("/@{table.restfulUri}s/{id}")
    public Response<@{table.className}> get(@PathVariable("id") Long id){
        return new Response<>(@{table.fclClassName}Service.getById(id));
    }

    @GetMapping("/@{table.restfulUri}s")
    public Response<Grid<@{table.className}>> page(@{table.className}VO request){
        return @{utils.toLowerCaseFirst(table.className)}Service.page(request);
    }

    @PutMapping("/@{table.restfulUri}s")
    public Response<Void> update(@RequestBody @{table.className} param){
        @{utils.toLowerCaseFirst(table.className)}Service.saveOrUpdate(param);
        return Response.SUCCESS;
    }

    @DeleteMapping("/@{table.restfulUri}s/{id}")
    public Response<Void> delete(@PathVariable("id") Long id){
        @{utils.toLowerCaseFirst(table.className)}Service.removeById(id);
        return Response.SUCCESS;
    }
}
