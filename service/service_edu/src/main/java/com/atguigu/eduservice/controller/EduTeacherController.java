package com.atguigu.eduservice.controller;


import com.atgugui.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-18
 */
@Api(tags = "讲师管理")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    //查所有讲师表
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //逻辑删除
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R deleteTeacherById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        System.out.println("flag===>" + flag);
        return flag ? R.ok() : R.error();
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("list/{current}/{limit}")
    public R pageListTeacher(@PathVariable Long current, @PathVariable Long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();

        //特定异常 ArithmeticException
        int i = 10 / 0;

        //自定义异常
        /*try {
            int i = 10 / 0;
        } catch (Exception e) {
            throw new GuliException(20001, "出现自定义异常");
        }*/

        List<EduTeacher> records = pageTeacher.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);

        //return R.ok().data("total", total).data("rows", records);
    }


    @ApiOperation("带条件分页查询")
    /*@GetMapping("query/{current}/{limit}")
    public R pageQueryTeacher(
            @PathVariable Long current,
            @PathVariable Long limit,
            TeacherQuery teacherQuery) {*/
    @PostMapping("query/{current}/{limit}")
    public R pageQueryTeacher(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建分页对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        final String name = teacherQuery.getName();
        final Integer level = teacherQuery.getLevel();
        final String begin = teacherQuery.getBegin();
        final String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }

        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();
        final long pageNumber = pageTeacher.getPages();
        final long pageSize = pageTeacher.getSize();
        final long currentPage = pageTeacher.getCurrent();

        List<EduTeacher> records = pageTeacher.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        map.put("currentPage", currentPage);
        map.put("rows", records);
        return R.ok().data(map);
    }

    @ApiOperation("带条件GET分页查询")
    @GetMapping("/query/{current}/{limit}")
    public R getTeachersByQuery(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) String begin,
            @RequestParam(required = false) String end){
        Page pageTeacher = new Page<EduTeacher>(current, limit);

        QueryWrapper wrapper = new QueryWrapper<EduTeacher>();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified", end);
        }
        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();
        final long pageNum = pageTeacher.getCurrent();
        final long pageSize = pageTeacher.getSize();
        final long pages = pageTeacher.getPages();
        final List records = pageTeacher.getRecords();

        final HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        map.put("pages", pages);
        map.put("list", records);

        return R.ok().data(map);
    }


    //添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("add")
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师信息", required = true)
            @RequestBody EduTeacher eduTeacher) {
        final boolean save = teacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    @ApiOperation("根据ID查询讲师")
    @GetMapping("{id}")
    public R getTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        if (!StringUtils.isEmpty(id)) {
            final EduTeacher teacher = teacherService.getById(id);
            return R.ok().data("item", teacher);
        }
        return R.error();
    }

    @ApiOperation("根据ID修改讲师")
    @PutMapping("{id}")
    public R updateTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师信息", required = true)
            @RequestBody EduTeacher eduTeacher) {
        eduTeacher.setId(id);
        final boolean flag = teacherService.updateById(eduTeacher);
        return flag ? R.ok() : R.error();
    }
}

