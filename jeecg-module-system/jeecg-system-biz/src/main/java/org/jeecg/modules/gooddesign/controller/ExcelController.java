package org.jeecg.modules.gooddesign.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesDetailVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesService;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Api(tags = "好设计-发现100-设计师信息")
@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {

    @Autowired
    IDesignTopJudgesService designTopJudgesService;

    @PostMapping("import")
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<String> errorMessageList = new ArrayList<>();
        List<SysDepart> listSysDeparts = null;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            String key = entity.getKey();
            MultipartFile file = entity.getValue();
            ImportParams params = new ImportParams();
//            if ("设计师表".equals(key)) {
            Result result = importJudges(file);
//            }
        }
        return null;
    }


    public Result importJudges(MultipartFile multipartFile) {
        try {


            //默认获取第一页
            ExcelReader reader = ExcelUtil.getReader(multipartFile.getInputStream());


            // 获取第一个 sheet，通过索引指定
            reader.setSheet(0);
            // 从第二行开始读取,忽略表头
            List<List<Object>> rows = reader.read(0);
            //获取头像
            List<String> userImgs = getUserImg(rows);


            //读取第二页,即第二个sheet
            reader.setSheet(1);
            // 从第二行开始读取,忽略表头
            List<List<Object>> rows2 = reader.read(1);

            Map<Integer, List<String>> imgMap = doDesignTopImages(rows2, userImgs);


            List<DesignTopJudgesDetailVO> designTopJudgesList = doPorcessJudges(rows, imgMap);
            designTopJudgesList.forEach(designTopJudgesDetailVO -> {
                designTopJudgesService.addDetail(designTopJudgesDetailVO);
            });
            System.out.println(designTopJudgesList);

            reader.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return Result.OK("读取成功");
    }

    private List<String> getUserImg(List<List<Object>> rows) {
        List<String> userImgs = new ArrayList<>();

        if (CollectionUtils.isEmpty(rows)) {
//                return "读取sheet1失败!";
        }

        // 遍历读取的行数据
        for (int i = 1; i < rows.size(); i++) {
            List<Object> rowList = rows.get(i);
            if (rowList.size() > 16) {
                Optional.ofNullable(rowList.get(16)).ifPresent(data -> {
                    userImgs.add(data.toString());
                });
            }

        }
        return userImgs;
    }


    List<DesignTopJudgesDetailVO> doPorcessJudges(List<List<Object>> rows, Map<Integer, List<String>> imgMap) {
        List<DesignTopJudgesDetailVO> designTopJudgesList = new ArrayList<>();

        if (CollectionUtils.isEmpty(rows)) {
//                return "读取sheet1失败!";
        }
        // 遍历读取的行数据
        for (int i = 1; i < rows.size(); i++) {
            List<Object> rowList = rows.get(i);
            DesignTopJudgesDetailVO designTopJudges = new DesignTopJudgesDetailVO();
            List<DesignTopProductVO> products = new ArrayList<>();
            DesignTopProductVO designTopProductVO = new DesignTopProductVO();
            for (int j = 0; j < rowList.size(); j++) {
                if (j == 0) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setActivityId(Integer.valueOf(data.toString()));
                    });
                } else if (j == 1) {
//                    Optional.ofNullable(rowList.get(1)).ifPresent(data -> {
//                        designTopJudges.setActivityId(Integer.valueOf(data.toString()));
//                    });
                } else if (j == 2) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setCity(data.toString());
                    });
                } else if (j == 3) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setActivityName(data.toString());
                    });
                } else if (j == 4) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setSort(Integer.valueOf(data.toString()));
                    });
                } else if (j == 5) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setRealName(data.toString());
                    });

                } else if (j == 6) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setCompany(data.toString());
                    });
                } else if (j == 7) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setPhone(data.toString());
                    });
                } else if (j == 8) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setPost(data.toString());
                    });
                } else if (j == 9) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setProvinces(data.toString());
                    });
                } else if (j == 10) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setCity(data.toString());
                    });
                } else if (j == 11) {

                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setAddress(data.toString());
                    });
                } else if (j == 12) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setWechat(data.toString());
                    });
                } else if (j == 13) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setEmail(data.toString());
                    });
                } else if (j == 14) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setUserDesc(data.toString());
                    });
                } else if (j == 15) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setScreenStatus(Integer.valueOf(data.toString()));
                    });
                } else if (j == 16) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopJudges.setUserImgUrl(data.toString());
                    });
                } else if (j == 17) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopProductVO.setProductName(data.toString());
                    });
                } else if (j == 18) {
                    Optional.ofNullable(rowList.get(j)).ifPresent(data -> {
                        designTopProductVO.setProductDesc(data.toString());
                    });
                }
            }
            if (StringUtils.isEmpty(designTopProductVO.getProductName())) {
                designTopProductVO.setProductName("默认项目名称");
            }
            if (imgMap != null) {
                List<String> strings = imgMap.get(designTopJudges.getSort());
                if (strings == null || strings.isEmpty()) {
                    log.info("当前设计师sort为：{}，姓名：{},无作品，请核查", designTopJudges.getSort(), designTopJudges.getRealName());
                } else {
                    designTopProductVO.setProductImgUrls(strings);
                }
            }

            products.add(designTopProductVO);

            designTopJudges.setProducts(products);
            log.info("sheet1的 " + i + 2 + "行数据 :" + JSONObject.toJSONString(rowList));

            if (designTopJudges.getActivityId() == null) {
                //todo 添加注意修改对应活动
                designTopJudges.setActivityId(2);
            }

            designTopJudgesList.add(designTopJudges);
            //取完后执行操作...........
        }
        return designTopJudgesList;
    }

    Map<Integer, List<String>> doDesignTopImages(List<List<Object>> rows, List<String> userImgs) {
        Map<Integer, List<String>> resultMap = new HashMap<>();
        if (CollectionUtils.isEmpty(rows)) {
            log.info("读取sheet1失败!");
            return null;
        }
        // 遍历读取的行数据
        for (int i = 1; i < rows.size(); i++) {
            List<Object> rowList = rows.get(i);
            Integer sort = Integer.valueOf(rowList.get(0).toString());
            String imgUrl = rowList.get(1).toString();
            if (resultMap.containsKey(sort)) {
                List<String> imgurls = resultMap.get(sort);
                if (!userImgs.contains(imgUrl)) {
                    imgurls.add(imgUrl);
                }
            } else {
                if (!userImgs.contains(imgUrl)) {
                    List<String> imgurls = new ArrayList<>();
                    imgurls.add(imgUrl);
                    resultMap.put(sort, imgurls);
                }
            }

        }
        return resultMap;
    }

    //封装这个方法是因为,会出现导入表格中最后一列对应的字段有的为空,会出现越界问题,被迫校验....
    String readString(List<Object> rowList, int i) {
        if (rowList.size() - 1 < i) {
            return "";
        }
        return (null == rowList.get(i) || ObjectUtils.isEmpty(rowList.get(i))) ? "" : rowList.get(i).toString();
    }


}
