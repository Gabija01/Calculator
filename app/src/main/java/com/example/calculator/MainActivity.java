package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView txtInput;
    private double memory = 0;
    private String currentOperator = "";
    private double firstOperand = 0;
    private boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInput = findViewById(R.id.txtInput);

        setupNumberButtonListeners();
        setupOperatorButtonListeners();
        setupMemoryButtonListeners();
        setupControlButtonListeners();
    }

    private void setupNumberButtonListeners() {
        int[] numberButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btnE8, R.id.btn9, R.id.btnDot
        };

        View.OnClickListener listener = view -> {
            Button button = (Button) view;
            String buttonText = button.getText().toString();

            if (isOperatorPressed) {
                txtInput.setText("");
                isOperatorPressed = false;
            }

            String currentText = txtInput.getText().toString();
            if (currentText.equals("0") && !buttonText.equals(".")) {
                txtInput.setText(buttonText);
            } else {
                txtInput.append(buttonText);
            }
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setupOperatorButtonListeners() {
        int[] operatorButtonIds = {
                R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply,
                R.id.btnDivide, R.id.btnEqual
        };

        View.OnClickListener listener = view -> {
            Button button = (Button) view;
            String operator = button.getText().toString();

            if (!operator.equals("=")) {
                firstOperand = Double.parseDouble(txtInput.getText().toString());
                currentOperator = operator;
                isOperatorPressed = true;
            } else {
                calculateResult();
            }
        };

        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setupMemoryButtonListeners() {
        findViewById(R.id.btnMC).setOnClickListener(v -> memory = 0);
        findViewById(R.id.btnMR).setOnClickListener(v -> txtInput.setText(String.valueOf(memory)));
        findViewById(R.id.btnMS).setOnClickListener(v -> memory = Double.parseDouble(txtInput.getText().toString()));
        findViewById(R.id.btnMPlus).setOnClickListener(v -> memory += Double.parseDouble(txtInput.getText().toString()));
        findViewById(R.id.btnMMinus).setOnClickListener(v -> memory -= Double.parseDouble(txtInput.getText().toString()));
    }

    private void setupControlButtonListeners() {
        findViewById(R.id.btnDelete).setOnClickListener(v -> deleteLastCharacter());
        findViewById(R.id.btnCE).setOnClickListener(v -> txtInput.setText("0"));

        findViewById(R.id.btnDeleteAll).setOnClickListener(v -> {
            txtInput.setText("0");
            firstOperand = 0;
            currentOperator = "";
            isOperatorPressed = false;
        });
        findViewById(R.id.btnPlusMinus).setOnClickListener(v -> toggleSign());
        findViewById(R.id.btnSquareRoot).setOnClickListener(v -> calculateSquareRoot());
        findViewById(R.id.btnReciprocal).setOnClickListener(v -> calculateReciprocal());
    }

    private void deleteLastCharacter() {
        String currentText = txtInput.getText().toString();
        if (currentText.length() > 1) {
            txtInput.setText(currentText.substring(0, currentText.length() - 1));
        } else {
            txtInput.setText("0");
        }
    }

    private void toggleSign() {
        String currentText = txtInput.getText().toString();
        if (!currentText.equals("0")) {
            if (currentText.startsWith("-")) {
                txtInput.setText(currentText.substring(1));
            } else {
                txtInput.setText("-" + currentText);
            }
        }
    }

    private void calculateSquareRoot() {
        double value = Double.parseDouble(txtInput.getText().toString());
        txtInput.setText(String.valueOf(Math.sqrt(value)));
    }

    private void calculateReciprocal() {
        double value = Double.parseDouble(txtInput.getText().toString());
        if (value != 0) {
            txtInput.setText(String.valueOf(1 / value));
        }
    }

    private void calculateResult() {
        double secondOperand = Double.parseDouble(txtInput.getText().toString());
        double result = 0;

        switch (currentOperator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
            case "*":
                result = firstOperand * secondOperand;
                break;
            case "/":
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    txtInput.setText("Error");
                    return;
                }
                break;
        }

        txtInput.setText(String.valueOf(result));
        firstOperand = result;
        isOperatorPressed = true;
    }
}
