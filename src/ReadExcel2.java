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

      // 각 로또번호별 당첨 회수 카운트
      for (int i = 0; i < excelNumData.length; i++) {
        count[excelNumData[i]]++; //COUNT          
      }
    }



    //숫자별 카운트 데이터 맵 객체에 담기

    for (int i = 1; i < count.length; i++) {
      map.put(i, count[i]);  

      //System.out.println(map.get(i));
    }


    wb.close();


    /*   System.out.println("------------sort 전 -------------");

    System.out.println(map);
     */


    System.out.println("\n------최다 빈도 6개 수 내림차순 ------");

    Iterator<Integer> it = ReadExcel2.sortValueDesc(map).iterator();


    int[] descNum = new int[45];
    int[] maxNum = new int[6]; // 
    int[] minNum = new int[6];
    int index = 0;

    while(it.hasNext()){

      int temp =  (Integer) it.next();

      descNum[index++] = temp;
      //System.out.println(temp + " : " + map.get(temp)+"회");
    }


    for(int i = 0; i < 6; i++) {
      System.out.println( descNum[i] + " : " + map.get(descNum[i])+"회");
      maxNum[i] = descNum[i];

    }


    System.out.println("\n------최소 빈도 6개 수 오름차순 ------");


    for(int i = descNum.length -1; i >= descNum.length -6 ; i--) {
      minNum[(descNum.length -1)-i] = descNum[i];
      System.out.println( minNum[(descNum.length -1)-i] + " : " + map.get(descNum[i]) + "회");
    }


    //-------------------로또번호 만들기-----------------

    System.out.println("\n------랜덤 로또번호 만들기 ------");


    int lotto[] = new int[6];
    int compareNum = 0; 
    int randomCount = 0;
    double prob = 0;


    // Start time
    long startTime = System.currentTimeMillis();

    // 최다당첨번호 돌리기 시작
    while (compareNum < 6) {

      // 랜덤번호 생성
      for(int i = 0; i < lotto.length; i++){

        lotto[i] = (int)(Math.random() * 45) + 1;

        // 랜덤 값 반환

        for (int j = 0; j < i; j++) {

          if (lotto[i] == lotto[j]) {

            i--;

            break;

          }  // 중복 값 제거1
        }
      }



      //-------------------로또번호 만들기 끝-----------------

      compareNum = compare(lotto, maxNum);    // 같은 번호가 몇개인지 비교

    }

    System.out.print("\nlotto 선택 숫자는 [");

    for(int i = 0; i < lotto.length; i++) {

      System.out.print(lotto[i] + " ");

    }

    System.out.print("] 입니다.");


    // End time
    long endTime = System.currentTimeMillis();

    // Total time
    long totalTime = endTime - startTime;
    System.out.println("\n최다 빈도 당첨번호 생성 시간은  " + totalTime + "(ms) 입니다.");
    
    
    
    compareNum = 0;
    
    // 최소당첨번호 돌리기 시작
    startTime = System.currentTimeMillis();
    
    while (compareNum < 6) {

      // 랜덤번호 생성

      for(int i = 0; i < lotto.length; i++){

        lotto[i] = (int)(Math.random()*45)+1;

        // 랜덤 값 반환

        for(int j = 0; j < i; j++){

          if(lotto[i] == lotto[j]){

            i--;

            break;

          }  // 중복 값 제거1

        }

      }


      //-------------------로또번호 만들기 끝-----------------

      compareNum = compare(lotto, minNum);    // 같은 번호가 몇개인지 비교

    }
    
    System.out.print("\nlotto 선택 숫자는 [");

    for(int i = 0; i < lotto.length; i++){

      System.out.print(lotto[i] + " ");

    }

    System.out.print("] 입니다.");


    // End time
    endTime = System.currentTimeMillis();

    // Total time
     totalTime = endTime - startTime;
    System.out.println("\n최소 빈도 당첨번호 생성 시간은  " + totalTime + "(ms) 입니다.");

  }



  //맵의 value 값으로 정렬하는 메소드
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


  //배열 안의 값을 비교하는 메소드

  static int compare(int arr[], int arr2[]){

    int count = 0;

    for (int i = 0; i < arr.length; i++){

      for (int j = 0; j < arr2.length; j++){

        if (arr[i] == arr2[j])

          count++;
      }
    }
    return count;

  }

}


