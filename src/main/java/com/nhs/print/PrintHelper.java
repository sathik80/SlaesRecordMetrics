/*******************************************************************************
 *  Copyright 2020 - Amadeus Development Company S.A. Copyright of this program
 *  is the property of AMADEUS, without whose written permission reproduction in
 *  whole or in part is prohibited. All rights reserved. Amadeus development
 *  company S.A. B.P. 69 06902 SOPHIA ANTIPOLIS CEDEX http://www.amadeus.net
 *
 *******************************************************************************
 * Last Version Identification:
 * PrintHelper.java,v 1.0  2 Aug 2020   19:27:34    Last modified by: srahman
 *******************************************************************************/
package com.nhs.print;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * @author  srahman
 */
public class PrintHelper {

  // decimal formatter for the proper output
  private final static DecimalFormat decimalFormat = new DecimalFormat("#0.00");

  /**
   * Prints the Average days between order date and ship date
   * 
   * @param duration
   */
  public static void printAvgShippingDays(List<Long> duration) {
    System.out.println("3. Average days between order date and ship date");
    System.out.println("-----------------------------------------------------------------------------");
    // print out the average number of days
    System.out.printf("%10s %20s", "Average days to ship", duration.stream().mapToLong(v -> v).average().orElse(0) + "\n");
    System.out.println("-----------------------------------------------------------------------------");
  }

  /**
   * Prints the Year with more orders received
   * 
   * @param yearOrders
   */
  public static void printBestYear(Map<Integer, Integer> yearOrders) {
    System.out.println("2. Year with more orders received");
    System.out.println("-----------------------------------------------------------------------------");
    // get the entry from the map with the highest value
    Optional<Map.Entry<Integer, Integer>> maxEntry = yearOrders.entrySet().stream().max(Map.Entry.comparingByValue());
    // print out the key of that entry

    maxEntry.ifPresent(entry -> System.out.printf("%20s %20s", "Best year", entry.getKey() + "\n"));
    System.out.println("-----------------------------------------------------------------------------\n");
  }

  /**
   * 
   * Prints Items and Profits
   * 
   * @param itemProfit
   */
  public static void printItemsAndProfit(Map<String, Double> itemProfit) {
    System.out.println("1. Items and Profits");
    System.out.println("-----------------------------------------------------------------------------");
    System.out.printf("%20s %20s", "ITEM", "PROFIT");
    System.out.println();
    System.out.println("-----------------------------------------------------------------------------");

    // TreeMap to store values of HashMap
    TreeMap<String, Double> sortedItemProfitMap = new TreeMap<>(itemProfit);
    // for each item in the map
    for (String item : sortedItemProfitMap.keySet()) {
      // print its total profit formatted to two decimal places
      // System.out.println(item + ":\t " + decimalFormat.format(itemProfit.get(item)));
      System.out.printf("%20s %25s", item, decimalFormat.format(sortedItemProfitMap.get(item)) + "\n");
    }
    System.out.println("-----------------------------------------------------------------------------\n");
  }


}
