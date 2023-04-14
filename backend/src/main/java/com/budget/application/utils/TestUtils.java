package com.budget.application.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.json.JSONObject;

import com.budget.application.model.Expense;
import com.budget.application.model.Tag;

public class TestUtils {

	public static String getRequestParamStringFromArray(String paramName, List<String> paramValues) {
		StringBuilder sb = new StringBuilder();

		for (String value : paramValues) {
			sb.append(paramName + "=" + value + "&");
		}

		String result = sb.toString();
		result = result.substring(0, result.length() - 1);

		return result;
	}

	public static String getRandomTextFromUUID() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 5);
	}

	// public <T> List<T> append(List<T> list, T... args) {
	// return Stream.concat(list.stream(),
	// Stream.of(args)).collect(Collectors.toList());
	// }

	public static byte[] getBytesArrayFromJSON(JSONObject object) {
		return object.toString().getBytes();
	}

	public static Expense generateTestExpense(int amountOfTagsToGenerate, LocalDateTime expenseDate) {
		Expense expense = new Expense();

		expense.setCreationDate(expenseDate != null ? expenseDate : LocalDateTime.now());
		expense.setTags(TestUtils.generateTestTags(amountOfTagsToGenerate));
		expense.setValue(TestUtils.getRandomDoubleFromGivenRange(0.0, 10000));

		return expense;
	}

	public static List<Tag> generateTestTags(int amountOfTagsToGenerate) {
		List<Tag> randomTags = new ArrayList<Tag>();
		for (int i = 0; i < amountOfTagsToGenerate; i++) {
			randomTags.add(new Tag(TestUtils.getRandomTextFromUUID()));
		}

		return randomTags;
	}

	public static double getRandomDoubleFromGivenRange(double rangeMin, double rangeMax) {
		Random r = new Random();

		return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
	}

	public static List<Expense> generateGivenAmounOfTestExpenseObjects(int amountOfExpenses,
			int amountOfTagsInEachExpense, LocalDateTime expenseDate) {
		List<Expense> expenses = new ArrayList<Expense>();
		for (int i = 0; i < amountOfExpenses; i++) {
			expenses.add(TestUtils.generateTestExpense(amountOfTagsInEachExpense, expenseDate));
		}

		return expenses;
	}

	public static Timestamp getDateFromGivenFormat(String dateInProperFormat, String format) throws ParseException {
		DateFormat formatter = new SimpleDateFormat();
		Date date = formatter.parse(dateInProperFormat);
		Timestamp timestamp = new Timestamp(date.getTime());

		return timestamp;
	}

	public static LocalDateTime getLocalDateTimeFromGivenFormat(String dateInProperFormat, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		LocalDateTime dateTime = LocalDateTime.parse(dateInProperFormat, formatter);

		return dateTime;
	}

	public static String getISOStringFromLocalDateTime(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("+yyyy-MM-dd'T'HH:mm:ss'Z'")
				.withZone(ZoneId.of("UTC"));
		String formattedLDT = localDateTime.format(formatter).replace("+", "");

		return formattedLDT;
	}
}
