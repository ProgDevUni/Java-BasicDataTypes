import java.util.ArrayList;
import java.util.List;

public class AlgorithmUtilities {
    public static void convertaion(int number, int base) {
        // lazy realization: only from 2 to 10
        Stack<Integer> stack = new Stack<>(20); // MY OWN STACK
        while (number != 0) {
            stack.push(number%base);
            number/=base;
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
        System.out.println();
    }

    // TODO: unary operator for minus (like -6)
    //infix
//    public static double evaluateExpression(String expression) {
//        char[] tokens = expression.toCharArray();
//    }

    // TODO: unary operator for minus (like -6)
    //postfix
    public static double evaluateExpressionPostfix(String expression) {
        char[] tokens = expression.toCharArray();
        double a,b;
        Stack<Double> numbersStack = new Stack<>(50);
        StringBuilder numberBuffer = new StringBuilder();

        for (char ch : tokens) {
            if (Character.isDigit(ch)) {
                numberBuffer.append(ch);
            }
            else if (ch == '.') {
                numberBuffer.append(ch);
            }
            else {
                if (!numberBuffer.isEmpty()) {
                    numbersStack.push(Double.parseDouble(numberBuffer.toString()));
                    numberBuffer.setLength(0);
                }

                if (ch == ' ') {continue;} // just ignore spaces

                if ("+-*/".indexOf(ch) != -1) { // analog of {ch in "+-*/"}
                    b = numbersStack.pop();
                    a = numbersStack.pop(); // the first term located deeper in stack!!!!!!!!!!!!!
                    if (ch == '+') {numbersStack.push(a+b);}
                    if (ch == '-') {numbersStack.push(a-b);}
                    if (ch == '*') {numbersStack.push(a*b);}
                    if (ch == '/') {numbersStack.push(a/b);}
                }
            }
        }

        return numbersStack.pop();
    }


    // TODO: unary operator for minus (like -6)
    //infix to postfix
    public static String convertInfixToPrefix(String expression) {
        char[] tokens = expression.toCharArray();
        List<String> res = new ArrayList<>();
        Stack<Character> operators = new Stack<>(50);  // MY OWN STACK
        StringBuilder numberBuffer = new StringBuilder(); // easy way to get smth like 132.3432

        for (char ch : tokens) {
            if (Character.isDigit(ch)) {
                numberBuffer.append(ch);
            }
            else if (ch == '.') {
                numberBuffer.append(ch);
            }
            else {
                if (!numberBuffer.isEmpty()) {
                    res.add(numberBuffer.toString());
                    numberBuffer.setLength(0);
                }

                if (ch == ' ') {continue;} // just ignore spaces

                if (ch == '(') {operators.push(ch);}

                if (ch == ')') {
                    while (!operators.isEmpty() && operators.top() != '(') {
                        res.add(String.valueOf(operators.pop())); // JUST HAVE TO USE THIS WAY
                    }
                    if (!operators.isEmpty()) {operators.pop();} // remove '('
                }

                if ("+-*/".indexOf(ch) != -1) { // analog of {ch in "+-*/"}
                    while (!operators.isEmpty() && operatorsWeight(ch) < operatorsWeight(operators.top())) {
                        res.add(String.valueOf(operators.pop()));
                    }
                    operators.push(ch);
                }
            }
        }

        // if still we have to add a number
        if (!numberBuffer.isEmpty()) {res.add(numberBuffer.toString());}

        // the same for operators
        while (!operators.isEmpty()) {
            res.add(String.valueOf(operators.pop()));
        }

        return String.join(" ", res);
    }


    private static int operatorsWeight(char op) {
        // 0 is for brackets
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : 0;
    }

    public static <T extends Comparable<?super T>> void insertionSort(T[] a) {
        int j;
        // idea. take element on wrong. shift other elements to left until we will find a place for element we got
        // in tmp
        for (int p=1;p<a.length;p++) {
            T tmp = a[p];
            for (j=p; j>0 && tmp.compareTo(a[j-1])>0;j--)
                a[j] = a[j-1]; //shift
            a[j] = tmp;
        }
    }

    public static <T extends Comparable<?super T>> void shellSort(T[] a) {
        int j;
        // something like multiple insertion * gap(every 4 elements, every 2 elements, every 1 elements...)
        for (int gap=a.length/2;gap > 0; gap/=2)
            for (int i=gap; i<a.length;i++) {
                T tmp = a[i];
                for (j=i;j>=gap && tmp.compareTo(a[j-gap])<0;j--)
                    a[j]=a[j-gap];
                a[j]=tmp;
            }
    }


    // iternal method for heapsort
    private static int leftChild(int i) {return 2*i+1;}

    //internal method for heapsort
    private static <T extends Comparable<?super T>> void percDown(T[] a, int i, int n) {
        int child;
        T tmp;

        for (tmp=a[i];leftChild(i)<n;i=child) {
            child = leftChild(i);
            if (child != n-1 && a[child].compareTo(a[child+1]) < 0)
                child++;
            if (tmp.compareTo(a[child]) < 0)
                a[i] = a[child];
            else
                break;
        }
        a[i] = tmp;
    }

    //internal method for heap sort
    private static <T extends Comparable<?super T>> void swapReferences(T[] a, int i, int n) {
        T item = a[i];
        a[i] = a[n];
        a[n] = item;
    }

    //standard heapsort
    public static <T extends Comparable<?super T>> void heapSort(T[] a) {
        for (int i=a.length/2-1;i>=0;i--)
            percDown(a, i, a.length); // build heap
        for (int i=a.length-1;i>0;i--) {
            swapReferences(a, 0, i);
            percDown(a, 0, i);
        }
    }

    //internal recursive for mergeSort
    private static <T extends Comparable<?super T>> void mergeSort(T[] a, T[] tmpArray, int left, int right) {
        if (left < right) {
            int center = (right+left) / 2;
            mergeSort(a, tmpArray, left, center);
            mergeSort(a, tmpArray, center+1, right);
            merge(a, tmpArray, left, center+1, right);
        }
    }

    //internal method for mergeSort
    private static <T extends Comparable<?super T>>
    void merge(T[] a, T[] tmpArray, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos-1;
        int tmpPos = leftPos;
        int numElements = rightEnd-leftPos+1;

        while (leftPos <= leftEnd && rightPos <= rightEnd)
            if (a[leftPos].compareTo(a[rightPos]) <= 0)
                tmpArray[tmpPos++] = a[leftPos++];
            else
                tmpArray[tmpPos++] = a[rightPos++];

        while (leftPos <= leftEnd)
            tmpArray[tmpPos++] = a[leftPos++]; //copy left half

        while (rightPos <= rightEnd)
            tmpArray[tmpPos++] = a[rightPos++];

        for (int i=0;i<numElements;i++, rightEnd--)
            a[rightEnd] = tmpArray[rightEnd]; // copyback
    }

    public static <T extends Comparable<?super T>> void mergeSort(T[] a) {
        T[] tempArray = (T[]) new Comparable[a.length];
        mergeSort(a, tempArray, 0, a.length-1);
    }

    
}
