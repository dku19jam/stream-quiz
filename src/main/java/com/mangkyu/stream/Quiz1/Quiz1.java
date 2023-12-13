package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Quiz1 {

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        List<String[]> csvLines = readCsvLines();

        return csvLines.stream()
                .map(strings -> strings[1].replaceAll("\\s",""))
                .flatMap(hobby -> Arrays.stream(hobby.split(":")))
                .collect(Collectors.toMap(s -> s, s -> 1, Integer::sum));
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                .filter(strings -> strings[0].startsWith("정"))
                .map(strings -> strings[1].replaceAll("\\s",""))
                .flatMap(hobby -> Arrays.stream(hobby.split(":")))
                .collect(Collectors.toMap(s -> s, s -> 1, Integer::sum));
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                .map(strings -> count좋아(strings[2], 0))
                .reduce(0, Integer::sum);
    }

    public int count좋아(String string, int startIndex) {
        int index = string.indexOf("좋아", startIndex);
        if (index >= 0) {
            return 1 + count좋아(string, index + "좋아".length());
        }
        return 0;
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}
