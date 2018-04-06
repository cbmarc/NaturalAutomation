package com.naturalautomation.selenium.element.ui;

import java.util.Collection;
import java.util.Map;

import com.naturalautomation.selenium.element.Element;

/**
 * @author marc.carne@werlabs.com
 * Since 6/4/18
 */
public interface TableElement extends Element {

	Collection<String> getColumnNames();
	String getValueByRowAndColumnName(int row, String columnName);
	void clickOnCellByRowAndColumnName(int row, String columnName);
	Map<String, Integer> getNumberedColumnNames();

}
