package com.luwei.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * excel工具集合类
 *
 * @author Leone
 * @since 2018-06-27
 **/
@Slf4j
public class ExcelUtil {

    private ExcelUtil() {
    }

    public static void exportExcel(HttpServletResponse response, List<String[]> data, String fileName, String[] title) {
        Workbook workbook = new HSSFWorkbook();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        try {
            fileName = new String(fileName.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            log.error("文件名称转码错误");
        }

        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName + ".xls"));

        if (data != null && data.size() > 0) {
            if (title.length != data.get(0).length) {
                throw new RuntimeException("标题和主题的字段长度不同!");
            }
            Sheet sheet1 = workbook.createSheet("sheet1");
            Sheet sheet2 = workbook.createSheet("sheet2");
            Row row0 = sheet1.createRow(0);
            if (title != null) {
                for (int i = 0; i < title.length; i++) {
                    row0.createCell(i).setCellValue(title[i]);
                }
            }
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    Row row = sheet1.createRow(i + 1);
                    for (int j = 0; j < title.length; j++) {
                        row.createCell(j).setCellValue(data.get(i)[j]);
                    }
                }
            }
            try {
                log.info("导出excel成功" + fileName);
                workbook.write(response.getOutputStream());
            } catch (IOException e) {
                log.info("导出excel失败:{}", e.getMessage());
            }
        }
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = stringDateProcess(cell);
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    public static String stringDateProcess(Cell cell) {
        String result;
        if (HSSFDateUtil.isCellDateFormatted(cell)) {
            SimpleDateFormat sdf = null;
            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                sdf = new SimpleDateFormat("HH:mm");
            } else {
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
            Date date = cell.getDateCellValue();
            result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            double value = cell.getNumericCellValue();
            Date date = DateUtil
                    .getJavaDate(value);
            result = sdf.format(date);
        } else {
            double value = cell.getNumericCellValue();
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();
            if (temp.equals("General")) {
                format.applyPattern("#");
            }
            result = format.format(value);
        }
        return result;
    }

    /**
     * 功能通过上传的excel文件解析为一个string数组
     *
     * @param file
     * @return
     */
    public static List<String[]> importExcel(File file) {
        if (null == file) {
            log.error("file is not exist!");
            return null;
        }

        String fileName = file.getName();
        if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            log.error(fileName + "this file is not excel!");
        }
        Workbook workbook = null;
        try {
            InputStream in = new FileInputStream(file);
            if (fileName.endsWith(".xls")) {
                workbook = new HSSFWorkbook(in);
            } else if (fileName.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(in);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        List<String[]> data = new ArrayList<>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {

                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                for (int rowNum = firstRowNum + 2; rowNum <= lastRowNum; rowNum++) {

                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }

                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getLastCellNum();
                    String[] cells = new String[row.getLastCellNum()];

                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    data.add(cells);
                }
            }
        }
        return data;
    }


}
