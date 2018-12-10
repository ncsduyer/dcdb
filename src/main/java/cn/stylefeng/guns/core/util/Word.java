package cn.stylefeng.guns.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

import java.util.List;

public class Word {
    private Element root;
    private XWPFDocument xwpfTemplate;
    public Word(String template) {
//        SAXBuilder builder = new SAXBuilder();
//        try {
//
//            Document parse=builder.build(this.getClass().getResourceAsStream("/excel/"+template));
//            this.root=parse.getRootElement();
//            xwpfTemplate=new XWPFDocument();
//            //添加标题
//            String templateName=root.getAttribute("name").getValue();
//
//            XmlCursor cursor = xwpfTemplate.getCTP().newCursor();
//            XWPFTable tableOne = doc.insertNewTbl(cursor);
//            //基本信息表格
//            XWPFTable infoTable = document_createTable();
//            //去表格边框
//            infoTable.getCTTbl().getTblPr().unsetTblBorders();
//
//
//            //列宽自动分割
//            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
//            infoTableWidth.setType(STTblWidth.DXA);
//            infoTableWidth.setW(BigInteger.valueOf(9072));
//
//
//            //表格第一行
//            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
//            infoTableRowOne.getCell(0).setText("职位");
//            infoTableRowOne.addNewTableCell().setText(": ;
//
//                    //表格第二行
//                    XWPFTableRow infoTableRowTwo = infoTable.createRow();
//            infoTableRowTwo.getCell(0).setText("姓名");
//            infoTableRowTwo.getCell(1).setText(": seawater");
//
//            //表格第三行
//            XWPFTableRow infoTableRowThree = infoTable.createRow();
//            infoTableRowThree.getCell(0).setText("生日");
//            infoTableRowThree.getCell(1).setText(": xxx-xx-xx");
//
//            //表格第四行
//            XWPFTableRow infoTableRowFour = infoTable.createRow();
//            infoTableRowFour.getCell(0).setText("性别");
//            infoTableRowFour.getCell(1).setText(": 男");
//
//            //表格第五行
//            XWPFTableRow infoTableRowFive = infoTable.createRow();
//            infoTableRowFive.getCell(0).setText("现居地");
//            infoTableRowFive.getCell(1).setText(": xx");
//
//
//            //两个表格之间加个换行
//            XWPFParagraph paragraph = document_createParagraph();
//            XWPFRun paragraphRun = paragraph.createRun();
//            paragraphRun.setText("\r");
//
//
//
//            //工作经历表格
//            XWPFTable ComTable = document_createTable();
//
//
//            //列宽自动分割
//            CTTblWidth comTableWidth = ComTable.getCTTbl().addNewTblPr().addNewTblW();
//            comTableWidth.setType(STTblWidth.DXA);
//            comTableWidth.setW(BigInteger.valueOf(9072));
//
//            //表格第一行
//            XWPFTableRow comTableRowOne = ComTable.getRow(0);
//            comTableRowOne.getCell(0).setText("开始时间");
//            comTableRowOne.addNewTableCell().setText("结束时间");
//            comTableRowOne.addNewTableCell().setText("公司名称");
//            comTableRowOne.addNewTableCell().setText("title");
//
//            //表格第二行
//            XWPFTableRow comTableRowTwo = ComTable.createRow();
//            comTableRowTwo.getCell(0).setText("2016-09-06");
//            comTableRowTwo.getCell(1).setText("至今");
//            comTableRowTwo.getCell(2).setText("seawater");
//            comTableRowTwo.getCell(3).setText(";
//
//                    //表格第三行
//                    XWPFTableRow comTableRowThree = ComTable.createRow();
//            comTableRowThree.getCell(0).setText("2016-09-06");
//            comTableRowThree.getCell(1).setText("至今");
//            comTableRowThree.getCell(2).setText("seawater");
//            comTableRowThree.getCell(3).setText(";
//
//
//                    CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
//            XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);
//
//            //添加页眉
//            CTP ctpHeader = CTP.Factory.newInstance();
//            CTR ctrHeader = ctpHeader.addNewR();
//            CTText ctHeader = ctrHeader.addNewT();
//            String headerText = "Java POI create MS word file.";
//            ctHeader.setStringValue(headerText);
//            XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
//            //设置为右对齐
//            headerParagraph.setAlignment(ParagraphAlignment.RIGHT);
//            XWPFParagraph[] parsHeader = new XWPFParagraph[1];
//            parsHeader[0] = headerParagraph;
//            policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);
//
//
//            //添加页脚
//            CTP ctpFooter = CTP.Factory.newInstance();
//            CTR ctrFooter = ctpFooter.addNewR();
//            CTText ctFooter = ctrFooter.addNewT();
//            String footerText = "http://blog.csdn.net/zhouseawater";
//            ctFooter.setStringValue(footerText);
//            XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, document);
//            headerParagraph.setAlignment(ParagraphAlignment.CENTER);
//            XWPFParagraph[] parsFooter = new XWPFParagraph[1];
//            parsFooter[0] = footerParagraph;
//            policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);
//
//            int rownum=0;
//            int colnum=0;
//            Element colgroup=root.getChild("colgroup");
//            //设置列宽
//            setColumnWidth(colgroup);
//            //设置标题
//            rownum = setTitle(rownum);
//            rownum = setThead(rownum);
//            //生成表头
//            //生成thead
//            //设置格式
//            String templateName=root.getAttribute("name").getValue();
//        } catch (JDOMException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public XWPFDocument getXWPFTemplate() {
        return xwpfTemplate;
    }

    private int setThead( int rownum) {
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

    private int setTitle( int rownum) throws DataConversionException {
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
     * @param colgroup
     */
    private void setColumnWidth(Element colgroup) {
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
}
