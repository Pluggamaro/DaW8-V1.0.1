package app;

import org.apache.poi.ss.usermodel.*;

import app.BinCard;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
public class ExcelWriter {
/*
    private static final String EXCEL_FILE_PATH = "binCardData.xlsx";

    public static void saveBinCardData(BinCard binCard, StaticInfo staticInfo) {
    	try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("BinCardData");

         // Create rows 1-3 for static information
            Row staticInfoRow = sheet.createRow(0);

            // Schedule
            Cell scheduleLabelCell = staticInfoRow.createCell(0);
            Cell scheduleValueCell = staticInfoRow.createCell(1);
            scheduleLabelCell.setCellValue("Schedule:");
            scheduleValueCell.setCellValue(staticInfo.getSchedule());

            // Card No
            Cell cardNoLabelCell = staticInfoRow.createCell(2);
            Cell cardNoValueCell = staticInfoRow.createCell(3);
            cardNoLabelCell.setCellValue("Card No:");
            cardNoValueCell.setCellValue(staticInfo.getCardNo());

            // Item
            Cell itemLabelCell = staticInfoRow.createCell(4);
            Cell itemValueCell = staticInfoRow.createCell(5);
            itemLabelCell.setCellValue("Item:");
            itemValueCell.setCellValue(staticInfo.getItem());

            // Strength
            Cell strengthLabelCell = staticInfoRow.createCell(6);
            Cell strengthValueCell = staticInfoRow.createCell(7);
            strengthLabelCell.setCellValue("Strength:");
            strengthValueCell.setCellValue(staticInfo.getStrength());

            // Unit of Issue
            Cell unitOfIssueLabelCell = staticInfoRow.createCell(8);
            Cell unitOfIssueValueCell = staticInfoRow.createCell(9);
            unitOfIssueLabelCell.setCellValue("Unit of Issue:");
            unitOfIssueValueCell.setCellValue(staticInfo.getUnitOfIssue());

            // Code
            Cell codeLabelCell = staticInfoRow.createCell(10);
            Cell codeValueCell = staticInfoRow.createCell(11);
            codeLabelCell.setCellValue("Code:");
            codeValueCell.setCellValue(staticInfo.getCode());

            // ICN
            Cell icnLabelCell = staticInfoRow.createCell(12);
            Cell icnValueCell = staticInfoRow.createCell(13);
            icnLabelCell.setCellValue("ICN:");
            icnValueCell.setCellValue(staticInfo.getIcn());

            // Price
            Cell priceLabelCell = staticInfoRow.createCell(14);
            Cell priceValueCell = staticInfoRow.createCell(15);
            priceLabelCell.setCellValue("Price:");
            priceValueCell.setCellValue(staticInfo.getPrice());

            // ROL
            Cell rolLabelCell = staticInfoRow.createCell(16);
            Cell rolValueCell = staticInfoRow.createCell(17);
            rolLabelCell.setCellValue("ROL:");
            rolValueCell.setCellValue(staticInfo.getRol());

            // Size
            Cell sizeLabelCell = staticInfoRow.createCell(18);
            Cell sizeValueCell = staticInfoRow.createCell(19);
            sizeLabelCell.setCellValue("Size:");
            sizeValueCell.setCellValue(staticInfo.getSize());


            // BinCard property
            Cell binCardCell = staticInfoRow.createCell(20); 
            binCardCell.setCellValue(staticInfo.binCardProperty().get());

            Row headerRow = sheet.createRow(3);
            headerRow.createCell(0).setCellValue("Date");
            headerRow.createCell(1).setCellValue("Registration No");
            headerRow.createCell(2).setCellValue("Quantity Ordered");
            headerRow.createCell(3).setCellValue("Quantity Received");
            headerRow.createCell(4).setCellValue("Expiry Date");
            headerRow.createCell(5).setCellValue("Quantity Issued");
            headerRow.createCell(6).setCellValue("To/From");
            headerRow.createCell(7).setCellValue("Reference No");
            headerRow.createCell(8).setCellValue("Balance");
            headerRow.createCell(9).setCellValue("Signature");
            


            int rowNum = 4;
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(binCard.getDate());
            row.createCell(1).setCellValue(binCard.getPatientNo());
            row.createCell(2).setCellValue(binCard.getQueNo());
            row.createCell(3).setCellValue(binCard.getTimeIN());
            row.createCell(4).setCellValue(binCard.getExpiryDate());
            row.createCell(5).setCellValue(binCard.getQuantityIssued());
            row.createCell(6).setCellValue(binCard.getToFrom());
            row.createCell(7).setCellValue(binCard.getReferenceNo());
            row.createCell(8).setCellValue(binCard.getBalance());
            row.createCell(9).setCellValue(binCard.getSignature());
            


            // Write the workbook content to the Excel file
            try (FileOutputStream fileOut = new FileOutputStream(EXCEL_FILE_PATH)) {
                workbook.write(fileOut);
            }

            System.out.println("Excel file created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
