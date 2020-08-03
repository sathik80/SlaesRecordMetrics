
package com.nhs.launcher;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SalesRecordMetricsCalculatorTest {

  @Test
  public void testProcessResults() {
    SalesRecordMetricsCalculator t = new SalesRecordMetricsCalculator();
    t.process("TestRecords.csv");


    Map<String, Double> expProfit = new HashMap<>();
    expProfit.put("Vegetables", 715894.20);
    expProfit.put("Office Supplies", 566105.00);
    expProfit.put("Beverages", 71503.56);
    assertEquals(expProfit, t.getDataLoader().getItemProfitMap());

    Map<Integer, Integer> expYear = new HashMap<>();
    expYear.put(2011, 3);
    expYear.put(2015, 2);
    assertEquals(expYear, t.getDataLoader().getYearOrdersMap());
    assertEquals(2011, t.getDataLoader().getBestYear());
    assertEquals(25.6, t.getDataLoader().getaAverage());

    // coverage
    SalesRecordMetricsCalculator.main(null);
    
    
  }

  @Test
  public void testMissingFile() {
    SalesRecordMetricsCalculator t = new SalesRecordMetricsCalculator();
    t.process("missingFile.csv");
    assertTrue(t.getDataLoader().getItemProfitMap().isEmpty());
    assertEquals(-1, t.getDataLoader().getBestYear());
    assertEquals(0, t.getDataLoader().getaAverage());
  }

  @Test
  public void testLockedFile() throws Exception {
    File file = new File(ClassLoader.getSystemResource("EmptyFile.csv").toURI());
    final RandomAccessFile raFile = new RandomAccessFile(file, "rw");
    raFile.getChannel().lock();
    SalesRecordMetricsCalculator t = new SalesRecordMetricsCalculator();
    t.process("EmptyFile.csv");
  }
}
