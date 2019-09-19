package com.trackmanagement.processor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InputReaderTest {

    private InputReader inputReader;


    @BeforeEach
    void setUp() {
        inputReader = new InputReader();
    }

    @Test
    void readTalkInputToMap() {
        Map<String, Integer>  inputTalkMap = inputReader.readTalkInputToMap("/talk-input.txt");
        System.out.println(inputTalkMap.toString());
        System.out.println(inputReader.getTotalInputTalktimeinMins());
        assertEquals(785, inputReader.getTotalInputTalktimeinMins());
    }

    @AfterEach
    void tearDown() {
    }


}