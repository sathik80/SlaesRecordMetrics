package com.nhs.launcher;

import com.nhs.print.PrintHelper;
import com.nhs.salesrecord.DataLoader;

/**
 * Class with the solution for 1. Read the Sales record file (use the SalesRecords.csv file
 * provided) 2. Calculate sum of profits per each item sold and print on console. 3. Calculate in
 * which year there was more orders received and print it on console. 4. Calculate average days
 * between order date and ship date.
 * 
 */
public class SalesRecordMetricsCalculator {

  // file to read from resources folder
  private static final String FILE_NAME = "SalesRecords.csv";

  // DataLoader Loads Data from CSV for calculations.
  DataLoader dataLoader = new DataLoader();


  /**
   * @return the dataLoader
   */
  public DataLoader getDataLoader() {
    return dataLoader;
  }

  /**
   * Starting method of the class. Initializes the class and runs the method that does the
   * processing
   *
   * @param args not used
   */
  public static void main(String args[]) {
    SalesRecordMetricsCalculator calculator = new SalesRecordMetricsCalculator();
    calculator.process(FILE_NAME);
  }

  /**
   * Starts the process of Loading, Calculating and printing tasks.
   * 
   * @param fileName
   */
  void process(String fileName) {

    dataLoader.loadDataAndCalculate(fileName);

    PrintHelper.printItemsAndProfit(dataLoader.getItemProfitMap());

    PrintHelper.printBestYear(dataLoader.getYearOrdersMap());

    PrintHelper.printAvgShippingDays(dataLoader.getDuration());

  }


}
