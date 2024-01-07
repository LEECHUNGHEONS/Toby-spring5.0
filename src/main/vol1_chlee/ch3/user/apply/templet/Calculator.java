package main.vol1_chlee.ch3.user.apply.templet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filepath) throws IOException {

        //콜백 오브젝트, 예제 익명클래스에서 람다로 바꿨다
        //콜백 메소드는 오직 파일의 읽은 한 줄의 값을 가져와서 더하는 작업만 실시한다.
        LineCallback<Integer> sumCallback = (line, value) -> value + Integer.valueOf(line);
        return lineReadTemplate(filepath, sumCallback, 0);
    }

    public Integer calcMultiply(String filePath) throws IOException {

        //콜백 오브젝트 람다로 바꿈 이것도
        LineCallback<Integer> multiplyCallback = (line, value) -> value * Integer.valueOf(line);
        return lineReadTemplate(filePath, multiplyCallback, 1);
    }


    public String concatenate(String filepath) throws IOException {

        //람다 개꿀~! 이젠 익명 클래스도 적을 코드가 많은데 벗어나보자구요!
        LineCallback<String> concatenateCallback = (line, value) -> value + line;
        return lineReadTemplate(filepath, concatenateCallback, "");
    }



    //템플릿, -> 변경되지 않는 부분
    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException{

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filepath));

            //콜백 메소드를 통해 받환된 결과를 담을 변수
            T res = initVal;
            String line = null;

            //각 라인의 내용을 계산하는 작업만 콜백에게 전담한다
            while((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;

        } finally {
            if (br != null) {
                try {br.close();}
                catch (IOException e) {System.out.println(e.getMessage());}
            }
        }
    }
}
