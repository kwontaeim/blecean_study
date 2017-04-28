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

  private static final String FILE_PATH = "/Users/KWON/Downloads/lolo(1-750).xlsx"; // 파일 경로 스태틱 변수에 담기

  public static void main(String[] args) throws Exception {

    // 엑셀파일 불러오기 
    File file = new File(FILE_PATH);
    int[] numCount; // 로또 번호 빈도 카운트를 담을 배열
    

    // 엑셀파일에서 로또번호 카운트 배열 읽기
    numCount = getExcelData(new XSSFWorkbook(new FileInputStream(file)));
    
    
    // 숫자별 카운트 데이터 맵 객체에 담기
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    
    for (int i = 1; i < numCount.length; i++) {
      map.put(i, numCount[i]);
    }

    // 이터레이터로 내림차순 정렬
    Iterator<Integer> it = ReadExcel.sortByValue(map).iterator();


    int[] descNum = new int[45]; // 내림차순 정렬 데이터 담을 배열
    int[] maxNum = new int[6]; // 최다 빈도 번호 배열
    int[] minNum = new int[6]; // 최소 빈도 번호 배열
    int index = 0;

    // iterator 내림차순 정렬 데이터 배열에 담기
    while (it.hasNext()) {
      int temp =  (Integer) it.next();
      descNum[index++] = temp;
    }

    System.out.println("\n------최다 빈도 6개 수 내림차순 ------");

    for(int i = 0; i < 6; i++) {
      System.out.println( descNum[i] + " : " + map.get(descNum[i]) + "회");
      maxNum[i] = descNum[i];

    }


    System.out.println("\n------최소 빈도 6개 수 오름차순 ------");


    for(int i = descNum.length -1; i >= descNum.length -6 ; i--) {
      minNum[(descNum.length -1)-i] = descNum[i];
      System.out.println( minNum[(descNum.length -1)-i] + " : " + map.get(descNum[i]) + "회");
    }



    System.out.println("\n------랜덤 로또번호 만들기 ------");


    int lotto[] = new int[6];   // 랜덤 로또번호 담을 배열
    int compareNum = 0;     // 최다빈도, 최소빈도수와 매칭할 카운트
    int randomCount = 0;    // 랜덤번호 생성 카운트


    // Start time
    long startTime = System.currentTimeMillis();

    // 최다당첨번호 돌리기 시작
    while (compareNum < 6) {

      // 랜덤번호 생성
      lotto = runLotto();
      compareNum = compare(maxNum, lotto);    // 같은 번호가 몇개인지 비교
      randomCount++;

    }
    
    
    // End time
    long endTime = System.currentTimeMillis();

    System.out.print("\n선택된 로또 숫자: ");

    for(int i = 0; i < lotto.length; i++) {

      System.out.print(lotto[i] + " ");

    }


    System.out.println("\n최소 빈도 당첨번호 생성 시간은  " + (endTime - startTime) + "(ms) 입니다. \n해당 번호가 생성될 확률은  1/" + randomCount + " 입니다.");


    // 카운트 함수 초기화
    compareNum = 0;
    randomCount = 0;


    // 최소당첨번호 돌리기 시작
    startTime = System.currentTimeMillis();

    while (compareNum < 6) {

      // 랜덤번호 생성
      lotto = runLotto();

      compareNum = compare(minNum, lotto);    // 같은 번호가 몇개인지 비교
      randomCount++;
    }

    // End time
    endTime = System.currentTimeMillis();
    
    System.out.print("\n선택된 로또 숫자: ");

    for(int i = 0; i < lotto.length; i++){
      System.out.print(lotto[i] + " ");
    }

    System.out.println("\n최소 빈도 당첨번호 생성 시간은  " + (endTime - startTime)+ "(ms) 입니다. \n해당 번호가 생성될 확률은  1/" + randomCount + " 입니다.");

  }

  
  
  //메소드 정의 구간----------------------------------------------------------------------------------

  // 엑셀 데이터 받기
   static int[] getExcelData(XSSFWorkbook wb) {
   
    int[] excelNumData = new int[6]; // 엑셀
    int[] count = new int[46]; // 로또 번호 카운트를 담을 배열
    
    for (Row row : wb.getSheetAt(0)) {

      // 처음 3열은 생략 
      if (row.getRowNum() <= 2) {
        continue;
      }

      // 회차별 로또번호 저장 
      for (int i = 13; i <= 18; i++) {
        excelNumData[i-13] = (int)row.getCell(i).getNumericCellValue();
        //System.out.println(numData[i-13]);
      }

      // 각 로또번호별 당첨 회수 카운트 배열에 담기
      for (int i = 0; i < excelNumData.length; i++) {
        count[excelNumData[i]]++; //COUNT          
      }
    }
     return count;
  }


  //맵의 value 값으로 정렬하는 메소드
  public static List<Integer> sortByValue(final Map<Integer, Integer> map){

    List<Integer> list = new ArrayList<Integer>();

    list.addAll(map.keySet());

    Collections.sort(list,new Comparator<Object>(){

      public int compare(Object o1, Object o2){

        Object v1 = map.get(o2); // 파라미터 순서를 바꾸면 내림차순 
        Object v2 = map.get(o1);

        return ((Comparable<Object>) v1).compareTo(v2);

      }
    });
    return list;
  }


  //배열 안의 값을 비교하는 메소드
  static int compare(int arr[], int arr2[]) {

    int count = 0;

    for (int i = 0; i < arr.length; i++){
      for (int j = 0; j < arr2.length; j++){
        if (arr[i] == arr2[j])

          count++;
      }
    }
    return count;
  }


  //당첨번호 생성 메소드
  static int[] runLotto() {

    int [] arr = new int[6];

    for (int i = 0; i < arr.length; i++) {
      arr[i] = (int) (Math.random() * 45) + 1;

      // 랜덤 값 반환
      for (int j = 0; j < i; j++) {
        if (arr[i] == arr[j]) {
          i--;
          break;
        } // 중복 값 제거
      }
    }
    return arr;

  }
  

  
}


