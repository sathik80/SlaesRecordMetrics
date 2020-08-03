/*******************************************************************************
 *  Copyright 2020 - Amadeus Development Company S.A. Copyright of this program
 *  is the property of AMADEUS, without whose written permission reproduction in
 *  whole or in part is prohibited. All rights reserved. Amadeus development
 *  company S.A. B.P. 69 06902 SOPHIA ANTIPOLIS CEDEX http://www.amadeus.net
 *
 *******************************************************************************
 * Last Version Identification:
 * SalesRecordParser.java,v 1.0  2 Aug 2020   19:13:17    Last modified by: srahman
 *******************************************************************************/
package com.nhs.salesrecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class to parse every line from the file and create a SalesRecord Object
 * 
 * @author srahman
 * 
 */
public class SalesRecordParser {

  // date formatter used for the shipment date parsing
  private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

  /**
   * Creates the SalesRecord object out of one String
   * 
   * @param line to be split and parsed
   * @return newly created object
   */
  static SalesRecord parse(String line) {
    // split the line by comma
    String[] split = line.split(",");
    // create the new record
    SalesRecord sr = new SalesRecord();
    // set string values in order
    sr.setRegion(split[0]);
    sr.setCountry(split[1]);
    sr.setItemType(split[2]);
    sr.setSalesChannel(split[3]);
    sr.setOrderPriority(split[4]);
    // parse and set dates and numbers in order
    sr.setOrderDate(LocalDate.parse(split[5], dateTimeFormatter));
    sr.setOrderID(Integer.parseInt(split[6]));
    sr.setShipDate(LocalDate.parse(split[7], dateTimeFormatter));
    sr.setUnitsSold(Integer.parseInt(split[8]));
    sr.setUnitPrice(Double.parseDouble(split[9]));
    sr.setUnitCost(Double.parseDouble(split[10]));
    sr.setTotalRevenue(Double.parseDouble(split[11]));
    sr.setTotalCost(Double.parseDouble(split[12]));
    sr.setTotalProfit(Double.parseDouble(split[13]));
    // return the object
    return sr;
  }

}
