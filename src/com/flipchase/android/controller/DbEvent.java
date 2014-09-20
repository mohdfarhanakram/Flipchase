/**
 * 
 */
package com.flipchase.android.controller;

/**
 * @author m.farhan
 *
 */
public interface DbEvent {
   public static int FETCH_LIST = 1;
   public static int FETCH_SUB_LIST = 2;
   public static int INSERT_MASTER_LIST_DATA = 3;
   public static int INSERT_LIST_DATA = 4;
   public static int CREATE_LIST_DATA = 5;
   public static int INSERT_IN_MASTER_TABLE = 6;
   public static int DELETE_SELECTED_LIST = 7;
   public static int DELETE_SELECTED_SUB_LIST = 8;
}
