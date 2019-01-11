package cn.stylefeng.guns.core.util;

import cn.stylefeng.guns.core.util.vo.ExportColVo;
import cn.stylefeng.guns.core.util.vo.ExportRowVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.List;

public class Excel {
    private  HSSFSheet sheet;
    private int rownum=0;
    private String sheetname;
    private Element root;



    private HSSFWorkbook hssfWorkbook;

    public Excel(String template) {

        createExcel(template);

    }

    private Excel createExcel(String template) {
        SAXBuilder builder = new SAXBuilder();
        try {

            Document parse=builder.build(this.getClass().getResourceAsStream("/excel/"+template));
            this.root=parse.getRootElement();
            hssfWorkbook=new HSSFWorkbook();

            String templateName=root.getAttribute("name").getValue();
            if (StringUtils.isNotBlank(this.sheetname)){
                this.sheet = hssfWorkbook.createSheet(this.sheetname);
            }else{
                sheet = hssfWorkbook.createSheet(templateName);
            }
             rownum=0;
            int colnum=0;
            Element colgroup=root.getChild("colgroup");
            //设置列宽
            setColumnWidth(sheet,colgroup);
            //设置标题
            rownum = setTitle(sheet, rownum);
            rownum = setThead(sheet, rownum);


            Element tbody=root.getChild("tbody");
            Element tr=tbody.getChild("tr");
            int repeat=tr.getAttribute("repeat").getIntValue();
            List<Element> tds = tr.getChildren("td");
            for (int i=0;i<repeat;i++){
               HSSFRow row=sheet.createRow(rownum);
               for (colnum=0;colnum<tds.size();colnum++){
                   Element td=tds.get(colnum);
                   HSSFCell cell=row.createCell(colnum);
                   setType(hssfWorkbook,cell,td);
               }
            }
//            rownum++;


        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Excel(String template, List<ExportRowVo> exportRowVos) {
        createExcel(template).setContet(exportRowVos);
    }
    public Excel(List<ExportRowVo> titles,String template) {

        createExcel(titles,template);

    }

    private Excel createExcel(List<ExportRowVo> titles,String template) {

            hssfWorkbook=new HSSFWorkbook();

            if (StringUtils.isNotBlank(this.sheetname)){
                this.sheet = hssfWorkbook.createSheet(this.sheetname);
            }else{
                sheet = hssfWorkbook.createSheet(template);
            }
             rownum=0;
            int colnum=0;

            //设置标题
          setContet(titles);
            return this;
    }

    public Excel(List<ExportRowVo> titles,String template, List<ExportRowVo> exportRowVos) {
        createExcel(titles,template).setContet(exportRowVos);
    }

    private void setContet(List<ExportRowVo> exportRowVos) {
        //创建内容
        HSSFRow row;
        HSSFCell cell ;
        for (ExportRowVo exportRowVo : exportRowVos){
            for (int i = 0; i < exportRowVo.getTotal(); i++) {
                int startRow=rownum;
                row = sheet.createRow(rownum);
                int firstCol=0;
                for (int j = 0; j< exportRowVo.getColVos().size(); j++) {
                    ExportColVo exportColVo=exportRowVo.getColVos().get(j);
                    cell=row.createCell(firstCol);
                    if (i<exportColVo.getCols().size()){
                        cell.setCellValue(exportColVo.getCols().get(i).getContent());
                    }else{
                        cell.setCellValue("");
                    }
                    if (exportColVo.getCols().get(i).getSetStyle()){
                        cell.getCellStyle().cloneStyleFrom(exportColVo.getCols().get(i).getStyle());
                    }
                    if(i<exportColVo.getCols().size()){

//                        if(exportColVo.getCols().get(i).getRowspan()>1){
////                            if (i>1&&exportColVo.getCols().get(i-1).getRowspan()>1){
////                                startRow+=exportColVo.getCols().get(i-1).getRowspan()-1;
////                            }
//                        sheet.addMergedRegion(new CellRangeAddress(rownum,rownum+exportColVo.getCols().get(i).getRowspan()-1,j,j+exportColVo.getCols().get(i).getColspan()-1));
//                        }else if (exportColVo.getCols().get(i).getColspan()>1){
                            int rspan = exportColVo.getCols().get(i).getRowspan()-1;
                            int cspan = exportColVo.getCols().get(i).getColspan();
//                            if (firstCol+cspan-1<=0){
//                                firstCol++;
//                                continue;
//                            }
                            if(rspan>0||cspan>1){
                            sheet.addMergedRegion(new CellRangeAddress(rownum,rownum+rspan,firstCol,firstCol+cspan-1));
                            }
//                        }
                            firstCol+=cspan;
                    }else{
                            firstCol++;
                    }
                }
                rownum++;
            }
        }
        // 处理中文不能自动调整列宽的问题
        this.setSizeColumn(sheet);
    }

    private int setThead(HSSFSheet sheet, int rownum) {
        int colnum;//设置表头
        Element thead=root.getChild("thead");
        List<Element> trs=thead.getChildren("tr");
        for (int i=0;i<trs.size();i++) {
            Element tr = trs.get(i);
            HSSFRow row=sheet.createRow(rownum);
            List<Element> ths = tr.getChildren("th");
            for (colnum=0;colnum<ths.size();colnum++){
                Element th=ths.get(colnum);
                Attribute value=th.getAttribute("value");
                HSSFCell cell=row.createCell(colnum);
                if (value!=null){
                    cell.setCellValue(value.getValue());
                }
            }
            rownum++;
        }
        return rownum;
    }

    private int setTitle(HSSFSheet sheet, int rownum) throws DataConversionException {
        int colnum;Element title=root.getChild("title");
        List<Element> trs=title.getChildren("tr");
        for (int i=0;i<trs.size();i++){
            Element tr=trs.get(i);
            List<Element> tds=tr.getChildren("td");
            HSSFRow row=sheet.createRow(rownum);
            HSSFCellStyle cellStyle=hssfWorkbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            int firstCol=0;
            for (colnum=0;colnum<tds.size();colnum++){
                Element td=tds.get(colnum);
                HSSFCell cell = row.createCell(colnum);
                Attribute rowSpan=td.getAttribute("rowspan");
                Attribute colSpan=td.getAttribute("colspan");
                Attribute value=td.getAttribute("value");
                if (value!=null){
                    int rspan = rowSpan.getIntValue()-1;
                    int cspan = colSpan.getIntValue();
                    //设置字体
                    cell = row.createCell(firstCol);
                    String val=value.getValue();
                    cell.setCellValue(val);
                    HSSFFont font= hssfWorkbook.createFont();
                    font.setFontName("仿宋_GB2312");
                    font.setBold(true);
                    font.setFontHeightInPoints((short) 12);
                    cellStyle.setFont(font);
                    cell.setCellStyle(cellStyle);
                    if (firstCol+cspan-1<=0){
                        firstCol++;
                        continue;
                    }

                    sheet.addMergedRegion(new CellRangeAddress(rspan,rspan,firstCol,firstCol+cspan-1));
                    firstCol+=cspan;
                }
            }

            rownum++;
        }
        return rownum;
    }

    public Excel(String template,String sheetname) {
        this.sheetname = sheetname;
        new Excel(template);
    }

    /**
     * 设置单元格样式
     * @param wb
     * @param cell
     * @param td
     */
    private static void setType(HSSFWorkbook wb, HSSFCell cell, Element td) {
        Attribute typeAttr = td.getAttribute("type");
        String type = typeAttr.getValue();
        HSSFDataFormat format = wb.createDataFormat();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        if("NUMERIC".equalsIgnoreCase(type)){
            cell.setCellType(CellType.NUMERIC);
            Attribute formatAttr = td.getAttribute("format");
            String formatValue = formatAttr.getValue();
            formatValue = StringUtils.isNotBlank(formatValue)? formatValue : "#,##0.00";
            cellStyle.setDataFormat(format.getFormat(formatValue));
        }else if("STRING".equalsIgnoreCase(type)){
            cell.setCellValue("");
            cell.setCellType(CellType.STRING);
            cellStyle.setDataFormat(format.getFormat("@"));
        }else if("DATE".equalsIgnoreCase(type)){
            cell.setCellType(CellType.NUMERIC);
            cellStyle.setDataFormat(format.getFormat("yyyy-m-d"));
        }else if("ENUM".equalsIgnoreCase(type)){
            CellRangeAddressList regions =
                    new CellRangeAddressList(cell.getRowIndex(), cell.getRowIndex(),
                            cell.getColumnIndex(), cell.getColumnIndex());
            Attribute enumAttr = td.getAttribute("format");
            String enumValue = enumAttr.getValue();
            //加载下拉列表内容
            DVConstraint constraint =
                    DVConstraint.createExplicitListConstraint(enumValue.split(","));
            //数据有效性对象
            HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
            wb.getSheetAt(0).addValidationData(dataValidation);
        }
        cell.setCellStyle(cellStyle);
    }
     /**
     * 设置列宽
     * @param sheet
     * @param colgroup
     */
    private void setColumnWidth(HSSFSheet sheet, Element colgroup) {
        List<Element> cols = colgroup.getChildren("col");
        for (int i = 0; i < cols.size(); i++) {
            Element col = cols.get(i);
            Attribute width = col.getAttribute("width");
            String unit = width.getValue().replaceAll("[0-9,\\.]", "");
            String value = width.getValue().replaceAll(unit, "");
            int v=0;
            if(StringUtils.isBlank(unit) || "px".endsWith(unit)){
                v = Math.round(Float.parseFloat(value) * 37F);
            }else if ("em".endsWith(unit)){
                v = Math.round(Float.parseFloat(value) * 267.5F);
            }
            sheet.setColumnWidth(i, v);
        }
    }

    public HSSFWorkbook getHssfWorkbook() {
        return hssfWorkbook;
    }
    // 自适应宽度(中文支持)
    private void setSizeColumn(HSSFSheet sheet) {
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                for (int columnNum = 0; columnNum < sheet.getRow(rowNum).getPhysicalNumberOfCells(); columnNum++) {
                    int columnWidth = sheet.getColumnWidth(columnNum) / 256;
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                    HSSFCellStyle cellStyle=hssfWorkbook.createCellStyle();
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    HSSFFont font= hssfWorkbook.createFont();
                    font.setFontName("仿宋_GB2312");
                    font.setBold(true);
                    font.setFontHeightInPoints((short) 12);
                    cellStyle.setFont(font);
                    currentCell.setCellStyle(cellStyle);
                }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
            }
        }
    }
}
