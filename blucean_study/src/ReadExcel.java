import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

  private static final String FILE_PATH = "/Users/KWON/Downloads/로또 당첨번호(1-750).xlsx";

  public static void main(String[] args) throws Exception {

    // 엑셀파일 불러오기 
    File file = new File(FILE_PATH);

    int[] numData = new int[6]; 
    int[] index = new int[46];
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();




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

    for (int i = 1; i < index.length; i++) {
      map.put(i, index[i]);  

      //System.out.println(map.get(i));
    }


    wb.close();
    System.out.println("------------sort 전 -------------");

    System.out.println(map);



    Iterator<Integer> it = ReadExcel.sortByValue(map).iterator();



    System.out.println("---------sort 후------------");

    while(it.hasNext()){

      int temp =  (Integer) it.next();

      System.out.println(temp + " : " + map.get(temp)+"회");
     

    }

  }


  //맵의 value 값으로 정렬 
  public static List<Integer> sortByValue(final Map<Integer, Integer> map){

    List<Integer> list = new ArrayList<Integer>();

    list.addAll(map.keySet());



    Collections.sort(list,new Comparator<Object>(){

      @SuppressWarnings("unchecked")

      public int compare(Object o1,Object o2){

        Object v1 = map.get(o1);

        Object v2 = map.get(o2);



        return ((Comparable<Object>) v1).compareTo(v2);

      }

    });

    Collections.reverse(list); // 주석시 오름차순

    return list;

  }




}
