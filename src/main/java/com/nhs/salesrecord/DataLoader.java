/*******************************************************************************
 * Copyright 2020 - Amadeus Development Company S.A. Copyright of this program is the property of
 * AMADEUS, without whose written permission reproduction in whole or in part is prohibited. All
 * rights reserved. Amadeus development company S.A. B.P. 69 06902 SOPHIA ANTIPOLIS CEDEX
 * http://www.amadeus.net
 *
 *******************************************************************************
 * Last Version Identification: DataLoader.java,v 1.0 2 Aug 2020 19:40:45 Last modified by: srahman
 *******************************************************************************/
package com.nhs.salesrecord;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Loads Data from given filename, Parse and populate the values in given Maps and List.
 * 
 * @author srahman
 *
 */
public class DataLoader {

  // map to hold the total profits per item
  static Map<String, Double> itemProfitMap = new HashMap<>();

  // map to hold the number of orders per year
  static Map<Integer, Integer> yearOrdersMap = new HashMap<>();

  // list of the number of days each order took til shipment
  static List<Long> duration = new ArrayList<>();

  /**
   * Default Constructor.
   */
  public DataLoader() {

  }

  /**
   * reads the data from the csv file and compute the solutions
   */
  public void loadDataAndCalculate(String fileName) {
    List<SalesRecord> records = new ArrayList<>();
    try {
        records = parseRecords(Paths.get(ClassLoader.getSystemResource(fileName).toURI()));
    } catch (Exception ignored) {
    	clear();
    }
    for (SalesRecord sr : records) {
        populateItemAndProfitValues(itemProfitMap, sr);

        addYearsAndOrdersValueToMap(yearOrdersMap, sr);

        addDurationOfOrder(duration, sr);
    }

  }

  /**
   * Reads the file from the URI, skips the first line (header) and makes the list of SalesRecords
   *
   * @param filePath to be read
   * @return list of SalesRecords (empty in case of Exception)
   */
  private List<SalesRecord> parseRecords(Path filePath) {
    // init empty list
    List<SalesRecord> result = new ArrayList<>();
    try (Stream<String> stream = Files.lines(filePath).skip(1)) {
      // for each line in the file
      stream.forEach(line -> {
        // load the data to SalesRecord
        SalesRecord sr = SalesRecordParser.parse(line);
        // if it's not null nor its item type
        if (sr == null || sr.getItemType() == null) {
          return;
        }
        result.add(sr);
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Adds the difference between ordered Data and shipped date to the list.
   * 
   * @param duration
   * @param sr
   */
  private void addDurationOfOrder(List<Long> duration, SalesRecord sr) {
    // add the number of days between order and shipment to the list
    duration.add(ChronoUnit.DAYS.between(sr.getOrderDate(), sr.getShipDate()));
  }

  /**
   * Adds the Year and number of Orders in the year to the Map.
   * 
   * @param yearOrdersMap
   * @param sr
   */
  private void addYearsAndOrdersValueToMap(Map<Integer, Integer> yearOrdersMap, SalesRecord sr) {
    // get the year of the record
    int year = sr.getOrderDate().getYear();
    // read the number of orders for that year up to 'now' and add 1 (current order)
    int ordersCount = yearOrdersMap.getOrDefault(year, 0) + 1;
    // update the latest value for that item in the map
    yearOrdersMap.put(year, ordersCount);
  }

  /**
   * Adds the Items and total profits to the Map.
   * 
   * @param itemProfitMap
   * @param sr
   */
  private void populateItemAndProfitValues(Map<String, Double> itemProfitMap, SalesRecord sr) {
    // get item type
    String itemType = sr.getItemType();
    // read the profit for that item up to 'now' and add profit of the current item
    double profit = itemProfitMap.getOrDefault(itemType, 0.0) + sr.getTotalProfit();
    // update the latest value for that item in the map
    itemProfitMap.put(itemType, profit);
  }

  /**
   * @return the itemProfitMap
   */
  public Map<String, Double> getItemProfitMap() {
    return itemProfitMap;
  }

  /**
   * @return the yearOrdersMap
   */
  public Map<Integer, Integer> getYearOrdersMap() {
    return yearOrdersMap;
  }

  /**
   * Finds the key of the map entry with the highest value
   */
  public int getBestYear() {
    Optional<Map.Entry<Integer, Integer>> maxEntry = getYearOrdersMap().entrySet().stream().max(Map.Entry.comparingByValue());
    return maxEntry.isPresent() ? maxEntry.get().getKey() : -1;
  }

  /**
   * calculates the average duration value
   * 
   * @return average
   */
  public double getaAverage() {
    return getDuration().stream().mapToLong(v -> v).average().orElse(0);
  }

  /**
   * @return the duration
   */
  public List<Long> getDuration() {
    return duration;
  }

  /**
   * Clears the map entries.
   */
  public void clear() {
	getItemProfitMap().clear();
    getYearOrdersMap().clear();
    getDuration().clear();
	
}

}
