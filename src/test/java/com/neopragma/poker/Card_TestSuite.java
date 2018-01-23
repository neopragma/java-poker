package com.neopragma.poker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Card_InstantiationTest.class,
        Card_OneEyedTest.class,
        Card_EqualityTest.class,
        Card_ComparisonTest.class
})
public class Card_TestSuite {

}
