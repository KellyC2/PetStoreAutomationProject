package api.utilities;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLUtility {
    public FileInputStream fileInputStream;
    public FileOutputStream fileOutputStream;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle cellStyle;
    String path;

    public XLUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        this.fileInputStream = new FileInputStream(this.path);
        this.workbook = new XSSFWorkbook(this.fileInputStream);
        this.sheet = this.workbook.getSheet(sheetName);
        int rowCount = this.sheet.getLastRowNum();
        this.workbook.close();
        this.fileInputStream.close();
        return rowCount;
    }

    public int getColumnCount(String sheetName, int rowCount) throws IOException {
        this.fileInputStream = new FileInputStream(this.path);
        this.workbook = new XSSFWorkbook(this.fileInputStream);
        this.sheet = this.workbook.getSheet(sheetName);
        this.row = this.sheet.getRow(rowCount);
        int columnCount = this.row.getLastCellNum();
        this.workbook.close();
        this.fileInputStream.close();
        return columnCount;
    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        this.fileInputStream = new FileInputStream(this.path);
        this.workbook = new XSSFWorkbook(this.fileInputStream);
        this.sheet = this.workbook.getSheet(sheetName);
        this.row = this.sheet.getRow(rownum);
        this.cell = this.row.getCell(colnum);
        DataFormatter formatter = new DataFormatter();
        String data = formatter.formatCellValue(this.cell);
        this.workbook.close();
        this.fileInputStream.close();
        return data;
    }

    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        File xlsFile = new File(this.path);
        if (!xlsFile.exists()) {
            this.workbook = new XSSFWorkbook();
            this.fileOutputStream = new FileOutputStream(this.path);
            this.workbook.write(this.fileOutputStream);
        }

        this.fileOutputStream = new FileOutputStream(this.path);
        this.workbook = new XSSFWorkbook(this.fileInputStream);
        if (this.workbook.getSheetIndex(sheetName) == -1) {
            this.workbook.createSheet(sheetName);
        }

        this.sheet = this.workbook.getSheet(sheetName);
        if (this.sheet.getRow(rownum) == null) {
            this.sheet.createRow(rownum);
        }

        this.row = this.sheet.getRow(rownum);
        this.cell = this.row.getCell(colnum);
        this.cell.setCellValue(data);
        this.fileOutputStream = new FileOutputStream(this.path);
        this.workbook.write(this.fileOutputStream);
        this.workbook.close();
        this.fileInputStream.close();
    }
}
