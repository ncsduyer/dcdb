package cn.stylefeng.guns.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.jdom2.Document;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class Word {
    private XWPFTable table;
    private int rownum=0;
    private String sheetname;
    private Element root;
    private XWPFDocument xWPFDocument;
    public Word(String template,String sheetname) {
        SAXBuilder builder = new SAXBuilder();
        try {

            Document parse=builder.build(this.getClass().getResourceAsStream("/excel/"+template));
            this.root=parse.getRootElement();
            // 创建document对象
            XWPFDocument document = new XWPFDocument();

            String templateName=root.getAttribute("name").getValue();
            //  创建段落
            XWPFParagraph titleParagraph = document.createParagraph();
            // 设置段落居中
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
//            设置标题
            if (StringUtils.isNotBlank(this.sheetname)){
                titleParagraphRun.setText(this.sheetname);
            }else{
                titleParagraphRun.setText(templateName);
            }
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);


            XWPFTable table = document.createTable();
            setTableWidth(table, "10000");

            rownum=0;
            int colnum=0;

            Element colgroup=root.getChild("colgroup");
            //设置列宽
            setColumnWidth(table,colgroup);
            //设置标题
            rownum = setTitle(table, rownum);
            rownum = setThead(table, rownum);


            Element tbody=root.getChild("tbody");
            Element tr=tbody.getChild("tr");
            int repeat=tr.getAttribute("repeat").getIntValue();
            List<Element> tds = tr.getChildren("td");
            for (int i=0;i<repeat;i++){
                XWPFTableRow row=table.createRow();
                for (colnum=0;colnum<tds.size();colnum++){
                    Element td=tds.get(colnum);
                    XWPFTableCell cell=row.createCell();
//                    setType(hssfWorkbook,cell,td);
                }
                rownum++;
            }


        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Word(String template, String[][] values) {
        new Word(template,"");
        setContent(values);
    }

    private void setContent(String[][] values) {
        // 自动填充数据
        XWPFTableRow dataRow = null;
        XWPFTableCell dataCell = null;
        //循环行数
        for(int i = 0; i<values.length; i++) {
            dataRow = table.getRow(rownum);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                dataCell = dataRow.getCell(j);
                dataCell.setText(values[i][j]);
            }
        }
    }

    public XWPFDocument getXWPFDocument() {
        return xWPFDocument;
    }

    private int setThead(XWPFTable table, int rownum) {
        int colnum;//设置表头
        Element thead=root.getChild("thead");
        List<Element> trs=thead.getChildren("tr");
        for (int i=0;i<trs.size();i++) {
            Element tr = trs.get(i);

            List<Element> ths = tr.getChildren("th");
            for (colnum=0;colnum<ths.size();colnum++){
                Element th=ths.get(colnum);
                Attribute value=th.getAttribute("value");

                if (value!=null){

                }
            }
            rownum++;
        }
        return rownum;
    }

    private int setTitle(XWPFTable table, int rownum) throws DataConversionException {
        int colnum;Element title=root.getChild("title");
        List<Element> trs=title.getChildren("tr");
        for (int i=0;i<trs.size();i++){
            Element tr=trs.get(i);
            List<Element> tds=tr.getChildren("td");

            int firstCol=0;
            for (colnum=0;colnum<tds.size();colnum++){
                Element td=tds.get(colnum);

                Attribute rowSpan=td.getAttribute("rowspan");
                Attribute colSpan=td.getAttribute("colspan");
                Attribute value=td.getAttribute("value");
                if (value!=null){
                    int rspan = rowSpan.getIntValue()-1;
                    int cspan = colSpan.getIntValue();
                    //设置字体

                    String val=value.getValue();



                    if (firstCol+cspan-1<=0){
                        firstCol++;
                        continue;
                    }


                    firstCol+=cspan;
                }
            }

            rownum++;
        }
        return rownum;
    }

    /**
     * 设置列宽
     * @param table
     * @param colgroup
     */
    private void setColumnWidth(XWPFTable table, Element colgroup) {
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

        }
    }

    /***
     * 跨行合并
     *
     * @param table
     * @param col
     *            合并列
     * @param fromRow
     *            起始行
     * @param toRow
     *            终止行
     */
    private void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (rowIndex == fromRow) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /***
     * 跨列合并
     *
     * @param table
     * @param row
     *            所合并的行
     * @param fromCell
     *            起始列
     * @param toCell
     *            终止列
     */
    private void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /***
     * 导出word 设置行宽
     *
     * @param table
     * @param width
     */
    private void setTableWidth(XWPFTable table, String width) {
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc = tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger(width));
        tblWidth.setType(STTblWidth.DXA);
    }
}
