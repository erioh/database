import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Test t = new Test();
        t.sort1();
        System.out.println(t.fibo(8));
        System.out.println("---");
        System.out.println(fibonacci(8));
        System.out.println(t.palindrome(64646));
        System.out.println(t.palindrome("64546"));
    }

    public boolean palindrome(int number) {
        if (number < 10) {
            return true;
        }
        if (number%10 == 0){
            return false;
        }
        int test = number;
        int sum = 0;
        while (test > 0) {
            int temp = test % 10;
            sum *= 10;
            sum += temp;
            test /= 10;
        }
        return sum == number;
    }

    public boolean palindrome(String word){
        if (word.length()==1){
            return true;
        }
        char[] chars = word.toCharArray();
        int length = chars.length;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != chars[length-1-i]){
                return false;
            }
        }
        return true;
    }


    public static int fibonacci(int number) {
        if (number == 1 || number == 2) {
            return 1;
        }
        return fibonacci(number - 1) + fibonacci(number - 2);
    }

    public int fibo(int n) {
        if (n == 1 || n == 2) {
            System.out.println(1);
        }
        int prev1 = 1;
        int prev2;
        int fibonacci = 1;
        for (int i = 3; i < n + 1; i++) {
            prev2 = prev1;
            prev1 = fibonacci;
            fibonacci = prev1 + prev2;
        }
        return fibonacci;
    }

    public void sort1() {
        int[] arr = {1, 3, 5, 2, 6};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[i] > arr[j]) {
                    int t = arr[i];
                    arr[i] = arr[j];
                    arr[j] = t;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
