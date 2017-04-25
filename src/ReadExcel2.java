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

public class ReadExcel2 {

  private static final String FILE_PATH = "/Users/blucean/Downloads/lolo.xlsx";

  public static void main(String[] args) throws Exception {

    // 엑셀파일 불러오기 
    File file = new File(FILE_PATH);

    int[] excelNumData = new int[6]; 
    int[] count = new int[46];
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
        excelNumData[i-13] = (int)row.getCell(i).getNumericCellValue();
        //System.out.println(numData[i-13]);
      }

      for (int i = 0; i < excelNumData.length; i++) {
        count[excelNumData[i]]++; //COUNT          
      }
    }



    //숫자별 카운트를 맵 객체에 담기

    for (int i = 1; i < count.length; i++) {
      map.put(i, count[i]);  

      //System.out.println(map.get(i));
    }


    wb.close();


    /*   System.out.println("------------sort 전 -------------");

    System.out.println(map);
     */


    System.out.println("------최다 빈도 6개 수 내림차순 ------");

    Iterator<Integer> it1 = ReadExcel2.sortValueDesc(map).iterator();


    int[] maxNum = new int[45];
    int index = 0;

    while(it1.hasNext()){

      int temp =  (Integer) it1.next();


      maxNum[index++] = temp;
      //System.out.println(temp + " : " + map.get(temp)+"회");

    }


    for(int i = 0; i < 6; i++) {
      System.out.println( maxNum[i] + " : " + map.get(maxNum[i])+"회");
    }


    System.out.println("------최소 빈도 6개 수 오름차순 ------");


    for(int i = maxNum.length -1; i >= maxNum.length -6 ; i--) {
      System.out.println( maxNum[i] + " : " + map.get(maxNum[i])+"회");
    }


    //-------------------로또번호 만들기-----------------

    System.out.println("------로또번호 만들기 ------");
    
    
    int Lotto[] = new int[6];

    // 배열 생성



    System.out.print("Lotto 선택 숫자는 [");



    for(int i=0; i<Lotto.length; i++){

      Lotto[i] = (int)(Math.random()*45)+1;

      // 랜덤 값 반환



      for(int j=0; j<i; j++){

        if(Lotto[i] ==Lotto[j]){

          i--;

          break;

        }  // 중복 값 제거1

      }

    }

    for(int i=0; i<Lotto.length; i++){

      System.out.print(Lotto[i] + " ");

    }

    System.out.print("] 입니다.");






  }



  //맵의 value 값으로 정렬 
  public static List<Integer> sortValueDesc(final Map<Integer, Integer> map){

    List<Integer> list = new ArrayList<Integer>();

    list.addAll(map.keySet());



    Collections.sort(list,new Comparator<Object>(){


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


