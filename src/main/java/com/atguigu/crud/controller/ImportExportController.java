package com.atguigu.crud.controller;


import com.atguigu.crud.bean.BaseData;
import com.atguigu.crud.service.BaseDataService;
import com.atguigu.crud.service.ExportService;
import com.atguigu.crud.utils.ImportExcelUtil;
import com.atguigu.crud.utils.POIUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel批量导入
 * 导出Excel文件
 */
@Controller()
@RequestMapping("/excel")
public class ImportExportController {

    @Autowired
    BaseDataService baseDataService;

    @Autowired
    ExportService exportService;

    @RequestMapping(value = "/uploadFile")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {

        System.out.println("开始");
        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = file.getOriginalFilename();
//        String fileName = new Date().getTime()+".jpg";
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileInputStream input = null;
        try {
            input = new FileInputStream(targetFile);
            MultipartFile multipartFile = new MockMultipartFile(targetFile.getName(), targetFile.getName(), "text/plain", IOUtils.toByteArray(input));
            List<String[]> list = POIUtil.readExcel(multipartFile);
            for (int i = 0; i < list.size(); i++) {
                System.out.println("list数组" + list.get(i));
            }

            FileInputStream fis = new FileInputStream(targetFile);
            Map<String, String> m = new HashMap<String, String>();
            m.put("校区名称", "校区名称");
            m.put("网络地址段（多）", "网络地址段");
            m.put("掩码", "掩码");
            m.put("ip地址（多）", "ip地址");
            m.put("使用设备", "使用设备");
            m.put("使用部门", "使用部门");
            m.put("存放位置", "存放位置");
            m.put("用户名", "用户名");
            m.put("密码（不显示）", "密码");
            m.put("备注", "备注");
            List<Map<String, Object>> result = ImportExcelUtil.parseExcel(fis, targetFile.getName(), m);
           /* for (int i = 0; i < result.size(); i++) {
                System.out.println("----result"+result.get(i).toString());
            }*/
            for (int i = 0; i < result.size(); i++) {
                if (!(result.get(i).get("校区名称").equals("") && result.get(i).get("网络地址段").equals("") && result.get(i).get("掩码")
                        .equals("") && result.get(i).get("ip地址").equals("") && result.get(i).get("使用设备").equals("") && result.get(i).get("使用部门")
                        .equals("") && result.get(i).get("存放位置").equals("") && result.get(i).get("用户名").equals("") && result.get(i).get("密码")
                        .equals("") && result.get(i).get("备注").equals(""))){
                    BaseData baseData = new BaseData((String)result.get(i).get("校区名称"),(String)result.get(i).get("网络地址段"),(String)result.get(i).get("掩码")
                            ,(String)result.get(i).get("ip地址"),(String)result.get(i).get("使用设备"),(String)result.get(i).get("使用部门")
                            ,(String)result.get(i).get("存放位置"),(String)result.get(i).get("用户名"),(String)result.get(i).get("密码")
                            ,(String)result.get(i).get("备注"));
                    baseDataService.saveData(baseData);
                    System.out.println("------controller"+result.get(i).toString());

                }
            }
            model.addAttribute("fileUrl", request.getContextPath() + "/upload/" + fileName);
            System.out.println("path" + request.getContextPath() + "/upload/" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index.jsp";
    }

    @RequestMapping(value = "/gotoExport")
    public String gotoImport(HttpServletRequest request, HttpServletResponse response) {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String timer=format.format(date);
        HSSFWorkbook wb = exportService.export();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" +timer + "ipInfo.xls");
        OutputStream ouputStream = null;
        try {
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "import";
    }

}
