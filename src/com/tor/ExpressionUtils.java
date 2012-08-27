package com.tor;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 08.09.2009
 * Time: 12:56:13
 * ����� �������� ������� ��� ������� � ��������� �������������� ���������.
 * * @author ����� ��������
 *
 * @version $Revision$ $Date$
 *          ���������e ��������� � �������� �������� ������� � ���������� ���������� �� �� ���������.
 */
public class ExpressionUtils {
    private static final Logger log = Logger.getLogger(ExpressionUtils.class);
    /**
     * �������� �������������� �������� � �� ����������.
     *
     * @see #sortingStation(String, java.util.Map)
     */
    public static final Map MAIN_MATH_OPERATIONS;

    static {
        MAIN_MATH_OPERATIONS = new HashMap();
        MAIN_MATH_OPERATIONS.put("*", new Integer(2));
        MAIN_MATH_OPERATIONS.put("/", new Integer(2));
        MAIN_MATH_OPERATIONS.put("+", new Integer(3));
        MAIN_MATH_OPERATIONS.put("-", new Integer(3));
        for (Function function : Function.values()) {
            MAIN_MATH_OPERATIONS.put(function.name(), 1);
        }
    }


    /**
     * ����������� ��������� �� ��������� ������� � �������� �������� ������� (���) �� ��������� <i>�������������
     * �������</i> ������� ��������. ������������� ������������ �������� �������� ������� �������� ��, ��� ���
     * ��������� (��� ��������) ����������� ����� ���������. ��� ��������� ���������� �� ������������� �������������
     * ������. ��������, ���������, ��������� � ��������� ������� ��� 3 * (4 + 7), ����� ��������� ��� 3 4 7 + *
     * � ���. ������� ������ ����� ���� ��������.
     * <a href="http://ru.wikipedia.org/wiki/��������_��������_������">��������� �� ���</a>.
     *
     * @param expression   ��������� � ��������� �����.
     * @param operations   ���������, �������������� � ��������� (���������������, ���� ����-���������������).
     *                     ���������� ����� ������ ���������� �������� (����� ������� ��������� - 1). ��������, ��� 5
     *                     �������� �������������� ���������� ����� ����� ��������� ���:
     *                     <pre> *   ->   1
     *                          /   ->   1
     *                          +   ->   2
     *                          -   ->   2
     *                     </pre>
     *                     ����������� ��������� ���������� � ��������� {@link #MAIN_MATH_OPERATIONS}.
     * @param leftBracket  ����������� ������.
     * @param rightBracket ����������� ������.
     * @return ��������������� ��������� � ���.
     */
    public static String sortingStation(String expression, Map operations, String leftBracket, String rightBracket) {
        if (expression == null || expression.length() == 0)
            throw new IllegalStateException("Expression isn't specified.");
        if (operations == null || operations.isEmpty())
            throw new IllegalStateException("Operations aren't specified.");
        // �������� ������, �������� �� "�������" - �������� � ��������..
        List out = new ArrayList();
        // ���� ��������.
        Stack stack = new Stack();

        // �������� �������� �� ���������.
        expression = expression.trim().toUpperCase().replaceAll(" ", "");

        // ��������� "��������", �� ���������� ���������� (�������� � ������).
        Set operationSymbols = new HashSet(operations.keySet());
        operationSymbols.add(leftBracket);
        operationSymbols.add(rightBracket);

        // ������, �� ������� ���������� ������ ������ �� ������� ��������.
        int index = 0;
        // ������� ������������� ������ ���������� ��������.
        boolean findNext = true;
        while (findNext) {
            int nextOperationIndex = expression.length();
            String nextOperation = "";
            // ����� ���������� ��������� ��� ������.
            String operation = null;
            for (Iterator it = operationSymbols.iterator(); it.hasNext(); ) {
                operation = (String) it.next();
                int i = expression.indexOf(operation, index);
                if (i >= 0 && i < nextOperationIndex) {
                    nextOperation = operation;
                    nextOperationIndex = i;
                }
            }
            // �������� �� ������.
            if (nextOperationIndex == expression.length()) {
                findNext = false;
            } else {
                // ���� ��������� ��� ������ ������������ �������, ��������� ��� � �������� ������.
                if (index != nextOperationIndex) {
                    out.add(expression.substring(index, nextOperationIndex));
                }
                // ��������� ���������� � ������.
                // ����������� ������.
                if (nextOperation.equals(leftBracket)) {
                    stack.push(nextOperation);
                }
                // ����������� ������.
                else if (nextOperation.equals(rightBracket)) {
                    while (!stack.peek().equals(leftBracket)) {
                        out.add(stack.pop());
                        if (stack.empty()) {
                            throw new IllegalArgumentException("Unmatched brackets");
                        }
                    }
                    stack.pop();
                }
                // ��������.
                else {
                    while (!stack.empty() && !stack.peek().equals(leftBracket) &&
                            (((Integer) operations.get(nextOperation)).intValue()
                                    >=
                                    ((Integer) operations.get(stack.peek())).intValue())) {
                        out.add(stack.pop());
                    }
                    stack.push(nextOperation);
                }
                index = nextOperationIndex + nextOperation.length();
            }
        }
        // ���������� � �������� ������ ��������� ����� ���������� ��������.
        if (index != expression.length()) {
            out.add(expression.substring(index));
        }
        // ������������� ��������� ������ � �������� ������.
        while (!stack.empty()) {
            out.add(stack.pop());
        }
        StringBuffer result = new StringBuffer();
        if (!out.isEmpty())
            result.append(out.remove(0));
        while (!out.isEmpty())
            result.append(" ").append(out.remove(0));

        return result.toString();
    }

    /**
     * ����������� ��������� �� ��������� ������� � �������� �������� ������� (���) �� ��������� <i>�������������
     * �������</i> ������� ��������. ������������� ������������ �������� �������� ������� �������� ��, ��� ���
     * ��������� (��� ��������) ����������� ����� ���������. ��� ��������� ���������� �� ������������� �������������
     * ������. ��������, ���������, ��������� � ��������� ������� ��� 3 * (4 + 7), ����� ��������� ��� 3 4 7 + *
     * � ���.
     * <a href="http://ru.wikipedia.org/wiki/��������_��������_������">��������� �� ���</a>.
     *
     * @param expression ��������� � ��������� �����.
     * @param operations ���������, �������������� � ��������� (���������������, ���� ����-���������������).
     *
     *                   ���������� ����� ������ ���������� �������� (����� ������� ��������� - 1). ��������, ��� 5
     *                   �������� �������������� ���������� ����� ����� ��������� ���:
     * <pre>
     * *   ->   1
     * /   ->   1
     * +   ->   2
     * -   ->   2
     * </pre>
     *                   ����������� ��������� ���������� � ��������� {@link #MAIN_MATH_OPERATIONS}.
     * @return ��������������� ��������� � ���.
     */
    public static String sortingStation(String expression, Map operations) {
        return sortingStation(expression, operations, "(", ")");
    }

    /**
     * ��������� �������� ���������, ����������� � ��������� �������. ��������� ����� ��������� ������, ����� �
     * ��������� ������, ������ �������� �������������� ���������.
     *
     * @param expression ���������.
     * @return ��������� ����������.
     */
    public static BigDecimal calculateExpression(String expression) {
        String rpn = sortingStation(expression, MAIN_MATH_OPERATIONS);
        StringTokenizer tokenizer = new StringTokenizer(rpn, " ");
        Stack stack = new Stack();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            // �������.
            if (!MAIN_MATH_OPERATIONS.keySet().contains(token.toUpperCase())) {
                stack.push(new BigDecimal(token));
            } else {
                BigDecimal operand2 = (BigDecimal) stack.pop();
                BigDecimal operand1 = (BigDecimal) (stack.empty() ? new BigDecimal(0) : stack.pop());
                if ("*".equals(token)) {
                    stack.push(operand1.multiply(operand2));
                } else if ("/".equals(token)) {

                    stack.push((BigDecimal) (operand1.divide(operand2, BigDecimal.ROUND_UNNECESSARY)));
                } else if ("+".equals(token)) {
                    stack.push(operand1.add(operand2));
                } else if ("-".equals(token)) {
                    stack.push(operand1.subtract(operand2));
                } else if (isFunctions(token)) {
                    stack.push(Function.valueOf(token).calc(operand2, operand1));

                }
            }
        }
        if (stack.size() != 1)
            throw new IllegalArgumentException("Expression syntax error.");
        return (BigDecimal) stack.pop();
    }

    private static boolean isFunctions(String s) {
        for (Function f : Function.values()) {
            if (f.name().equalsIgnoreCase(s)) return true;
        }
        return false;
    }

    /**
     * �������� ����������� ������.
     */
    private ExpressionUtils() {
    }

    /**
     * ��������� ������.
     *
     * @param args ������ ���������� ��������� ������.
     */
    public static void main(String[] args) {
        String expression = "3 + 4 * 2 / (1 - 5) + 2";
        System.out.println("��������� �������:         " + expression);
        System.out.println("\t��������� 3");
        String rpn = sortingStation(expression, MAIN_MATH_OPERATIONS);
        System.out.println("�������� �������� �������: " + rpn);
        System.out.println("\t��������� " + calculateExpression(expression));
    }
    private static final String[] FUNCTIONS = {"abs", "acos", "arg", "asin", "atan", "conj", "cos", "cosh", "exp", "imag", "log", "neg", "pow", "real", "sin", "sinh", "sqrt", "tan", "tanh"};

    public static enum Function {
        ABS {
            @Override
            public BigDecimal calc(BigDecimal... o) {
                double i = o[0].doubleValue();
                return BigDecimal.valueOf(Math.abs(i));
            }
        },
        POW {
            @Override
            public BigDecimal calc(BigDecimal... o) {
                double fist = o[0].doubleValue();
                double second = o[1].doubleValue();
                return BigDecimal.valueOf(Math.pow(fist, second));
            }
        };

        public abstract BigDecimal calc(BigDecimal... o);
    }
}

/** version for JRE 1.5
 public static String sortingStation(String expression, Map<String, Integer> operations, String leftBracket,
 String rightBracket) {
 if (expression == null || expression.length() == 0)
 throw new IllegalStateException("Expression isn't specified.");
 if (operations == null || operations.isEmpty())
 throw new IllegalStateException("Operations aren't specified.");
 // �������� ������, �������� �� "�������" - �������� � ��������..
 List<String> out = new ArrayList<String>();
 // ���� ��������.
 Stack<String> stack = new Stack<String>();

 // �������� �������� �� ���������.
 expression = expression.replace(" ", "");

 // ��������� "��������", �� ���������� ���������� (�������� � ������).
 Set<String> operationSymbols = new HashSet<String>(operations.keySet());
 operationSymbols.add(leftBracket);
 operationSymbols.add(rightBracket);

 // ������, �� ������� ���������� ������ ������ �� ������� ��������.
 int index = 0;
 // ������� ������������� ������ ���������� ��������.
 boolean findNext = true;
 while (findNext) {
 int nextOperationIndex = expression.length();
 String nextOperation = "";
 // ����� ���������� ��������� ��� ������.
 for (String operation : operationSymbols) {
 int i = expression.indexOf(operation, index);
 if (i >= 0 && i < nextOperationIndex) {
 nextOperation = operation;
 nextOperationIndex = i;
 }
 }
 // �������� �� ������.
 if (nextOperationIndex == expression.length()) {
 findNext = false;
 } else {
 // ���� ��������� ��� ������ ������������ �������, ��������� ��� � �������� ������.
 if (index != nextOperationIndex) {
 out.add(expression.substring(index, nextOperationIndex));
 }
 // ��������� ���������� � ������.
 // ����������� ������.
 if (nextOperation.equals(leftBracket)) {
 stack.push(nextOperation);
 }
 // ����������� ������.
 else if (nextOperation.equals(rightBracket)) {
 while (!stack.peek().equals(leftBracket)) {
 out.add(stack.pop());
 if (stack.empty()) {
 throw new IllegalArgumentException("Unmatched brackets");
 }
 }
 stack.pop();
 }
 // ��������.
 else {
 while (!stack.empty() && !stack.peek().equals(leftBracket) &&
 (operations.get(nextOperation) >= operations.get(stack.peek()))) {
 out.add(stack.pop());
 }
 stack.push(nextOperation);
 }
 index = nextOperationIndex + nextOperation.length();
 }
 }
 // ���������� � �������� ������ ��������� ����� ���������� ��������.
 if (index != expression.length()) {
 out.add(expression.substring(index));
 }
 // ������������� ��������� ������ � �������� ������.
 while (!stack.empty()) {
 out.add(stack.pop());
 }
 StringBuffer result = new StringBuffer();
 if (!out.isEmpty())
 result.append(out.remove(0));
 while (!out.isEmpty())
 result.append(" ").append(out.remove(0));

 return result.toString();
 }

 public static String sortingStation(String expression, Map<String, Integer> operations) {
 return sortingStation(expression, operations, "(", ")");
 }

 public static BigDecimal calculateExpression(String expression) {
 String rpn = sortingStation(expression, MAIN_MATH_OPERATIONS);
 StringTokenizer tokenizer = new StringTokenizer(rpn, " ");
 Stack<BigDecimal> stack = new Stack<BigDecimal>();
 while (tokenizer.hasMoreTokens()) {
 String token = tokenizer.nextToken();
 // �������.
 if (!MAIN_MATH_OPERATIONS.keySet().contains(token)) {
 stack.push(new BigDecimal(token));
 } else {
 BigDecimal operand2 = stack.pop();
 BigDecimal operand1 = stack.empty() ? BigDecimal.ZERO : stack.pop();
 if (token.equals("*")) {
 stack.push(operand1.multiply(operand2));
 } else if (token.equals("/")) {
 stack.push(operand1.divide(operand2));
 } else if (token.equals("+")) {
 stack.push(operand1.add(operand2));
 } else if (token.equals("-")) {
 stack.push(operand1.subtract(operand2));
 }
 }
 }
 if (stack.size() != 1)
 throw new IllegalArgumentException("Expression syntax error.");
 return stack.pop();
 }
 *  */