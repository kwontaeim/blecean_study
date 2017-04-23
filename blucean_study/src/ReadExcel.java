import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

  private static final String FILE_PATH = "/Users/KWON/Downloads/로또 당첨번호(1-750).xlsx";

  public static void main(String[] args) throws Exception {

    // 엑셀파일 불러오기 
    File file = new File(FILE_PATH);

    int[] numData = new int[6]; 
    int[] index = new int[46];
    HashMap<Integer, Integer> map  = new HashMap<>();



    // 엑셀파일 오픈 
    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));

    for (Row row : wb.getSheetAt(0)) {

      // 처음 3열은 생략 
      if (row.getRowNum() <= 2) {
        continue;
      }
      // 회자별 로또번호 저장 
      for (int i = 13; i <= 18; i++) {
        numData[i-13] = (int)row.getCell(i).getNumericCellValue();
        //System.out.println(numData[i-13]);
      }

      for (int i = 0; i < numData.length; i++) {
        index[numData[i]]++; //COUNT          
      }
    }
    
    //숫자별 카운트를 맵 객체에 담기
    
    for (int i = 0; i < index.length; i++) {
      map.put(index[i], i);       
    }
  
    TreeMap<Integer, Integer> treeMap = new TreeMap<>(map);
    Iterator<Integer> treeMapIter = treeMap.keySet().iterator();

    while(treeMapIter.hasNext()) {

        int key = treeMapIter.next();
        int value = treeMap.get( key );

        System.out.println(key+" : "+value);

    }

    
    
    wb.close();
  }
  
 

}
