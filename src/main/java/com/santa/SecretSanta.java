package com.santa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.util.EmailAddressReader;
import com.util.SendMailTLS;

/**
 * Randomly generates Secret Santa assignments for a given group.
 * <p>
 * All valid possible assignments are equally likely to be generated (uniform
 * distribution).
 * 
 * @author Michael Zaccardo (mzaccardo@aetherworks.com)
 */
public class SecretSanta {

	private static final Random random = new Random();
	public static final Logger logger = Logger.getLogger("mylog");
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;
	//private static final String[] DEFAULT_NAMES = { "Rob", "Ally", "Angus", "Mike", "Shannon", "Greg", "Lewis",
			//"Isabel" };

	public static void main(final String[] args) {
		boolean append = true;
		FileHandler handler = null;
		try {
			fileTxt = new FileHandler("Logging.txt");
		} catch (SecurityException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// create a TXT formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);
		logger.setUseParentHandlers(false);
		//logger.info("callig read name maethod");
		final String[] names = getNamesToUse("emails.txt");
		//logger.info("calling generate assignment method");
		final List<Integer> assignments = generateAssignments(names.length);

		printAssignmentsWithNames(assignments, names);
		System.out.println("all pairings are done");
	}

	private static String[] getNamesToUse(final String filePath) {
		EmailAddressReader addressReader = new EmailAddressReader();
		String names[] = addressReader.getNamesFromFile(filePath);
		return names;
	}

	private static List<Integer> generateAssignments(final int size) {
		final List<Integer> assignments = generateShuffledList(size);

		while (!areValidAssignments(assignments)) {
			Collections.shuffle(assignments, random);
		}

		return assignments;
	}

	private static List<Integer> generateShuffledList(final int size) {
		final List<Integer> list = new ArrayList<>(size);

		for (int i = 0; i < size; i++) {
			list.add(i);
		}

		Collections.shuffle(list, random);

		return list;
	}

	private static boolean areValidAssignments(final List<Integer> assignments) {
		for (int i = 0; i < assignments.size(); i++) {
			if (i == assignments.get(i)) {
				return false;
			}
		}

		return true;
	}

	private static void printAssignmentsWithNames(final List<Integer> assignments, final String[] names) {
		SendMailTLS mailTLS = new SendMailTLS();
		for (int i = 0; i < assignments.size(); i++) {
			// System.out.println(names[i] + " --> " +
			// names[assignments.get(i)]);
			mailTLS.sendMail(names[i], names[assignments.get(i)]);
		}
	}
}