package com.naturalautomation.selenium.element.ui;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.naturalautomation.selenium.element.DefaultElement;

/**
 * @author marc.carne@werlabs.com
 * Since 6/4/18
 */
public class DefaultTableElement extends DefaultElement implements TableElement {

	public DefaultTableElement(WebElement element, WebDriver driver) {
		super(element, driver);
	}

	@Override
	public Collection<String> getColumnNames() {
		WebElement thead = this.findElement(By.tagName("thead"));
		List<WebElement> headColumns = thead.findElements(By.tagName("td"));
		return headColumns.stream()
				.map(WebElement::getText)
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, Integer> getNumberedColumnNames() {
		WebElement thead = this.findElement(By.tagName("thead"));
		List<WebElement> headColumns = thead.findElements(By.tagName("td"));
		return IntStream.range(0, headColumns.size())
				.boxed()
				.collect(Collectors.toMap(i -> headColumns.get(i).getText(), i -> i));
	}

	@Override
	public String getValueByRowAndColumnName(int row, String columnName) {
		Map<String, Integer> columnMap = getNumberedColumnNames();
		Map<Integer, String> rowColumnMap = this.getRow(row);
		int columIndex = columnMap.get(columnName);
		Assert.assertThat("Column does not exist.", columIndex, Matchers.notNullValue());
		Assert.assertThat("Header and row column number do not match.",
				columnMap.size(), Matchers.equalTo(rowColumnMap.size()));
		return rowColumnMap.get(columIndex);
	}

	@Override
	public void clickOnCellByRowAndColumnName(int row, String columnName) {
		Map<String, Integer> columnMap = getNumberedColumnNames();
		int columIndex = columnMap.get(columnName);
		WebElement webElement = this.getRowElementMap(row).get(columIndex);
		DefaultElement element = new DefaultElement(webElement, getDriver());
		waitForElementToBeVisible(element);
		element.click();
	}

	private List<WebElement> getRows() {
		WebElement tbody = this.findElement(By.tagName("tbody"));
		return tbody.findElements(By.tagName("tr"));
	}

	private Map<Integer, String> getRow(int row) {
		return this.getRowElementMap(row).entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getText()));
	}

	private void waitForElementToBeVisible(WebElement element) {
		new WebDriverWait(getDriver(), 10L)
				.until(ExpectedConditions.visibilityOf(element));
	}

	private Map<Integer, WebElement> getRowElementMap(int row) {
		List<WebElement> rows = this.getRows();

		Assert.assertThat("Specified row number does not exist.",
				rows.size(), Matchers.greaterThan(row));

		List<WebElement> columns = rows.get(row).findElements(By.tagName("td"));
		return IntStream.range(0, columns.size())
				.boxed()
				.collect(Collectors.toMap(i -> i, columns::get));
	}
}
