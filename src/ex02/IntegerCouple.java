package ex02;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class IntegerCouple {
  
 static int doCompare (HashSet<Integer> num, int gap) {
   int count = 0;
   
   Iterator<Integer> it = num.iterator();
   
   while (it.hasNext()) {
     if (num.contains(it.next() + gap))
       count++;
   }
   return count;
 }
  
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    int max = sc.nextInt();
    int gap = sc.nextInt();
    sc.nextLine(); // 엔터처리 
    String[] inputNum  = sc.nextLine().split(" ");
    
    HashSet<Integer> num = new HashSet<Integer>();
    
    for (int i = 0; i < inputNum.length; i++) {
      num.add(Integer.parseInt(inputNum[i]));
    }
    
    sc.close();
    
  System.out.println(max);
  System.out.println(gap);
  
  System.out.println(doCompare(num, gap));
    
 
    
  }

}
