package tqs.lab5_1;


import java.util.Stack;

public class Calculator {
    private Stack<Double> stack = new Stack<>();

    public void push(int number) {
        stack.push((double) number);
    }

    public void push(String token) {
        switch (token) {
            case "+":
                checkStackSize(2);
                stack.push(stack.pop() + stack.pop());
                break;
            case "-":
                checkStackSize(2);
                stack.push(-stack.pop() + stack.pop());
                break;
            case "*":
                checkStackSize(2);
                stack.push(stack.pop() * stack.pop());
                break;
            case "/":
                checkStackSize(2);
                double divisor = stack.pop();
                if (divisor == 0) throw new RuntimeException("Cannot divide by zero.");
                stack.push(stack.pop() / divisor);
                break;
            default:
                throw new IllegalArgumentException("Invalid token: " + token);
        }
    }

    public double value() {
        if (stack.size() != 1) {
            throw new IllegalStateException("Invalid state: calculator has more or less than one value remaining.");
        }
        return stack.peek();
    }

    private void checkStackSize(int size) {
        if (stack.size() < size) {
            throw new IllegalStateException("Not enough operands on the stack.");
        }
    }
    
}
