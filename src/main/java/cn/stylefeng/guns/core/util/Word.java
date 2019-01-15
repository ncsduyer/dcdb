package cn.stylefeng.guns.core.util;

import cn.stylefeng.guns.core.util.vo.ExportColVo;
import cn.stylefeng.guns.core.util.vo.ExportRowVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.List;

public class Word {
    private XWPFTable table;
    private int rownum=0;
    private String sheetname;
    private Element root;
    private XWPFDocument xWPFDocument;

    public Word(List<ExportRowVo> titles,String template) {
        try {
            XWPFDocument document = new XWPFDocument();
            //  创建段落
            XWPFParagraph titleParagraph = document.createParagraph();
            // 设置段落居中
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
//            设置标题
            if (StringUtils.isNotBlank(this.sheetname)){
                titleParagraphRun.setText(this.sheetname);
            }else{
                titleParagraphRun.setText(template);
            }
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);


            XWPFTable table = document.createTable();
            setTableWidth(table, "10000");

            rownum=0;
            setContent(titles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Word(List<ExportRowVo> titles,String template,List<ExportRowVo> exportRowVos) {
        new Word(titles,template);
        setContent(exportRowVos);
    }

    private void setContent(List<ExportRowVo> exportRowVos) {
        // 自动填充数据
        XWPFTableRow row = null;
        XWPFTableCell cell = null;
        for (ExportRowVo exportRowVo : exportRowVos){
            for (int i = 0; i < exportRowVo.getTotal(); i++) {
//                int startRow = rownum;
                row = table.createRow();
//                int firstCol = 0;
                for (int j = 0; j < exportRowVo.getColVos().size(); j++) {
                    ExportColVo exportColVo = exportRowVo.getColVos().get(j);
                    cell = row.createCell();
                    if (i < exportColVo.getCols().size()) {
                        cell.setText(exportColVo.getCols().get(i).getContent());
                    } else {
                        cell.setText("");
                    }
//                    if (exportColVo.getCols().get(i).getSetStyle()){
//                        cell.set().cloneStyleFrom(exportColVo.getCols().get(i).getStyle());
//                    }
                    if (i < exportColVo.getCols().size()) {

                        if (exportColVo.getCols().get(i).getRowspan() > 1) {
//                            if (i>1&&exportColVo.getCols().get(i-1).getRowspan()>1){
//                                startRow+=exportColVo.getCols().get(i-1).getRowspan()-1;
//                            }
                            mergeCellsVertically(table, j, rownum, rownum + exportColVo.getCols().get(i).getRowspan() - 1);
                        } else if (exportColVo.getCols().get(i).getColspan() > 1) {
                            mergeCellsHorizontal(table, rownum,j,j+exportColVo.getCols().get(i).getColspan() - 1);
                        }
                    }
                }
                rownum++;
            }
        }

//        this.setTableWidth(table,);
    }

    public XWPFDocument getXWPFDocument() {
        return xWPFDocument;
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
