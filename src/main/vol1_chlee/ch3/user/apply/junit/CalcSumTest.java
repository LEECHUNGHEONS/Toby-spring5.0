package main.vol1_chlee.ch3.user.apply.junit;

import main.vol1_chlee.ch3.user.apply.templet.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcSumTest {

    Calculator calculator;
    String numFilepath;

    //파일이 테스트마다 중복되기 때문에 테스트 전에 실행해서 각 테스트에 적용되도록 @BeforeEach으로 정의했다.
    @BeforeEach
    public void setUp() {
        this.calculator = new Calculator();
        this.numFilepath = "E:/development2/code/toby5.1/src/main/vol1_chlee/ch3/user/apply/junit/numbers.txt";
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertEquals(10, calculator.calcSum(numFilepath));
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertEquals(24, calculator.calcMultiply(numFilepath));
    }

    @Test
    public void concatenate() throws IOException {
        assertEquals("1234", calculator.concatenate(numFilepath));
    }
}
