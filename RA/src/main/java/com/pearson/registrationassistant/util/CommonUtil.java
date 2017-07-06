package com.pearson.registrationassistant.util;

import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Key;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Abhay.Kulkarni
 */
public final class CommonUtil {
	private static final Logger logger = Logger.getLogger(CommonUtil.class);
	private static String algorithm = "AES";
	private static byte[] salt = {
					(byte) 0xA5, (byte) 0x9B, (byte) 0xC8, (byte) 0x33,
					(byte) 0x56, (byte) 0x32, (byte) 0xE4, (byte) 0x03,
					(byte) 0xA9, (byte) 0x9C, (byte) 0xC1, (byte) 0x32,
					(byte) 0x57, (byte) 0x35, (byte) 0xE3, (byte) 0x04
	};

	//TODO: use the formats based on locale.
	public static String[] ACCEPTABLE_DATE_FORMATS = new String[]{
					"dd-MM-yyyy HH:mm:ss"
					, "mm-dd-yyyy HH:mm:ss"
					, "mm/dd/yyyy HH:mm:ss"
					, "dd/mm/yyyy HH:mm:ss"
	};

	private static SimpleDateFormat dd_MM_yyyy = new SimpleDateFormat(ACCEPTABLE_DATE_FORMATS[0]);


	/**
	 * Converts an array of objects into List of String
	 *
	 * @param array - object array
	 * @return a List of String
	 */
	public static List<String> toStringList(Object[] array) {
		List<String> newList = new ArrayList<String>(array.length);
		for (Object object : array) {
			if (object != null) {
				newList.add(object.toString());
			}
		}

		return newList;
	}

	/**
	 * Overloaded method to convert an object into a double with a precision
	 * of 2 digits after the decimal point
	 *
	 * @param object - an object representing a double
	 * @return Double object
	 */
	public static Double toDouble(Object object) {
		return toDouble(object, 2);
	}

	/**
	 * A method to convert a given object from Object to Double with a given
	 * precision
	 *
	 * @param object    - an object representing a double
	 * @param precision - precision representing # of digits after decimal point
	 * @return a Double object restricted by the precision.
	 */
	public static Double toDouble(Object object, int precision) {
		if (object == null || !(object instanceof BigDecimal)) {
			return null;
		}
		BigDecimal bdValue = (BigDecimal) object;
		bdValue = bdValue.setScale(precision, BigDecimal.ROUND_FLOOR);
		return bdValue.doubleValue();
	}

	/**
	 * Converts a string value to a double value and eats away any exception
	 *
	 * @param input - the input to be converted.
	 * @return a double value
	 */
	public static Double stringToDouble(String input) {
		if (isEmpty(input)) return null;

		try {
			Double dbl = Double.valueOf(input);
			return dbl;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converts a timestamp object into a date object
	 *
	 * @param object - an object which internally represents a Timestamp
	 * @return Date object
	 */
	public static Date toDate(Object object) {
		if (object == null || !(object instanceof Timestamp)) {
			return null;
		}

		Timestamp ts = (Timestamp) object;
		return new Date(ts.getTime());
	}

	/**
	 * an API to check if the given string is empty considering null and blank
	 * as empty.
	 *
	 * @param string - input string
	 * @return true if the string is null or blank
	 */
	public static boolean isEmpty(String string) {
		return string == null || string.trim().isEmpty();
	}

	public static int generateRandomNumber() {
			Random random = new Random();
			return random.nextInt((99999999 - 99999) + 17) + 99999;
	}

	public static Timestamp getCurrTimestamp() {
		return new Timestamp(new java.util.Date().getTime());
	}

	/**
	 * Converts a csv string into a List of String
	 *
	 * @param csvString - string separated by delimiters
	 * @param delimiter - the delimiter in the csvString (,; etc)
	 * @return List<String> a string list
	 */
	public static List<String> toList(String csvString, String delimiter) {
		return Arrays
						.stream(csvString.split(delimiter)) // create a stream from an array
						.map(elem -> elem = elem.trim()) // trim every element
						.filter(elem -> !isEmpty(elem)) // remove the empty ones
						.collect(Collectors.toList()); // collect them into a list
	}

	/**
	 * This is a helper method used for local developement of the application
	 *
	 * @return true if the system property is set to "dev". False otherwise
	 */
	public static boolean isDev() {
		String environment = System.getProperties().getProperty("env");
		return !CommonUtil.isEmpty(environment)
						&& environment.trim().toLowerCase().equalsIgnoreCase("dev");
	}

	/**
	 * Converts a timestamp object to a String representation of the date
	 *
	 * @param timestamp - date in the form of timestamp
	 * @return a String representation of the date object.
	 */
	public static String timestampToString(Timestamp timestamp) {
		if (timestamp == null) return null;
		Date date = toDate(timestamp);
		return dd_MM_yyyy.format(date);
	}

	/**
	 * Decrypts a given text using the AES algorithm
	 *
	 * @param encryptedText - input text
	 * @return a decrypted value of the encrypted text if successful,
	 * RuntimeException otherwise
	 */
	public static String decrypt(String encryptedText) {
		try {
			Key key = new SecretKeySpec(salt, algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decodedText = Base64.getDecoder().decode(encryptedText);
			byte[] decValue = cipher.doFinal(decodedText);
			return new String(decValue);
		} catch (Exception e) {
			throw new RuntimeException("Could not decrypt value: " + encryptedText, e);
		}
	}

	/**
	 * Encrypts a given plain text data
	 *
	 * @param text - input text
	 * @return an encrypted text if successful, a RuntimeException otherwise.
	 */
	public static String encrypt(String text) {
		try {
			Key key = new SecretKeySpec(salt, algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = cipher.doFinal(text.getBytes());
			return Base64.getEncoder().encodeToString(encVal);
		} catch (Exception e) {
			throw new RuntimeException("Could not decrypt value: " + text, e);
		}
	}

	public static void createFile(byte[] bytes, String filePath) throws Exception {
		try {
			Path write = Files.write(Paths.get(filePath), bytes, StandardOpenOption.CREATE);
			System.out.println(write);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Handy method to convert an exception into String
	 *
	 * @param e to be converted into String
	 * @return a string object
	 */
	public static String stringify(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public static Double getRandomFloat() {
		return Math.random() * 100;
	}
}
