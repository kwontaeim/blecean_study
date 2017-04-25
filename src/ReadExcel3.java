import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel3 {

  private static final String FILE_PATH = "/Users/blucean/Downloads/lolo.xlsx";

  public static void main(String[] args) throws Exception {

    // 엑셀파일 불러오기 
    File file = new File(FILE_PATH);

    int[] excelNumData = new int[6]; 
    int[] count = new int[46];
    // Map<Integer, Integer> map = new HashMap<Integer, Integer>();




    // 엑셀파일 오픈 
    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));

    for (Row row : wb.getSheetAt(0)) {

      // 처음 3열은 생략 
      if (row.getRowNum() <= 2) {
        continue;
      }
      
      // 회자별 로또번호 저장 
      for (int i = 13; i <= 18; i++) {
        excelNumData[i-13] = (int)row.getCell(i).getNumericCellValue();
        //System.out.println(numData[i-13]);
      }

      for (int i = 0; i < excelNumData.length; i++) {
        count[excelNumData[i]]++; //COUNT          
      }
    }
    
    




    wb.close();


  }

}


