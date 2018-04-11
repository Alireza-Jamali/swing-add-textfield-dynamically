package export;

import calculator.Cal;
import calculator.MyPrinter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToExcel {

    public FileOutputStream init(MyPrinter myPrinter, String filePath) {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        String didName = filePath.replaceAll("^(.*[\\\\\\/])", "");
        didName = didName.substring(0, didName.lastIndexOf("."));
        XSSFSheet sheet = workbook.createSheet(didName);

        //This data needs to be written (Object[])
        int count = 1;
        Map<Integer, String[]> data = new TreeMap<>();
//        if(didName.length() > ) {
//            
//        }
        if(didName.length() > 85) {
            data.put(count++, new String[]{didName.substring(0, 83)});
            data.put(count++, new String[]{didName.substring(84, didName.length())});
        }else {
            data.put(count++, new String[]{didName});
        }
        data.put(count++, new String[]{"Total/Rial", myPrinter.tempSumForExcel});
        data.put(count++, new String[]{
            "colony: " + Cal.factorCPercentage + "%",
            "vira: " + Cal.factorEPercentage + "%",
            "bime: " + Cal.factorDPercentage + "%",
            "hekmat: " + Cal.factorFPercentage + "%",
            "brand: " + Cal.factorAPercentage + "%",
            "CreditCard: " + Cal.factorBPercentage + "%"});
        data.put(count++, new String[]{
            myPrinter.tempCalForExcel.factorC.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,"), 
            myPrinter.tempCalForExcel.factorE.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,"), 
            myPrinter.tempCalForExcel.factorD.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,"), 
            myPrinter.tempCalForExcel.factorF.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,"),
            myPrinter.tempCalForExcel.factorA.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,"), 
            myPrinter.tempCalForExcel.factorB.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,")});
        data.put(count++, new String[]{
            myPrinter.tempCalForExcel.rawFactorCForExcel.toString(), 
            myPrinter.tempCalForExcel.rawFactorEForExcel.toString(), 
            myPrinter.tempCalForExcel.rawFactorDForExcel.toString(), 
            myPrinter.tempCalForExcel.rawFactorFForExcel.toString(),
            myPrinter.tempCalForExcel.rawFactorAForExcel.toString(), 
            myPrinter.tempCalForExcel.rawFactorBForExcel.toString()});
        data.put(count++, new String[]{"", "", "", ""});
        data.put(count++, new String[]{"", "colony", "vira", "bime", "hekmat","brand", "CreditCard"}); 

        for (int i = 0; i < myPrinter.arInputs.size(); i++) {
            myPrinter.tempCalForExcel.singleFactorCalculateForExcel(Double.valueOf(myPrinter.arInputs.get(i).replace(",", "")));
            data.put(i + count++, new String[]{
                i + 1 + ") " +
                myPrinter.arInputs.get(i),
                myPrinter.tempCalForExcel.roundedFactorCForExcel,
                myPrinter.tempCalForExcel.roundedFactorEForExcel,
                myPrinter.tempCalForExcel.roundedFactorDForExcel,
                myPrinter.tempCalForExcel.roundedFactorFForExcel,
                myPrinter.tempCalForExcel.roundedFactorAForExcel,
                myPrinter.tempCalForExcel.roundedFactorBForExcel});
        }

        //Iterate over data and write to sheet
        //Set<String> keyset = data.keySet();

        int rownum = 0;
        for (int key : data.keySet()) {
            //create a row of excelsheet
            Row row = sheet.createRow(rownum++);

            //get object array of prerticuler key
            String[] objArr = data.get(key);

            int cellnum = 0;

            for (String obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                cell.setCellValue(obj);
            }
        }
        for (int j = 0; j < 8; j++) {
            if(j == 0){
                sheet.setColumnWidth(0, (myPrinter.tempCalForExcel.rawFactorCForExcel.toString().length() < 12 ? 13 : myPrinter.tempCalForExcel.rawFactorCForExcel.toString().length()) * 280);
                continue;
            }
            sheet.autoSizeColumn(j);
        }
        try {
            File excelFile = new File(filePath);
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(excelFile);
            workbook.write(out);
            System.out.println("file written successfully on disk.");
            myPrinter.printFile(excelFile);

            Desktop.getDesktop().open(new File(excelFile.getParent()));
//            Desktop.getDesktop().open(excelFile);
            return out;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
