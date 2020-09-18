package com.evilbas.pekolang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Launcher {

	private static final String sourceFile = "C:\\dev\\source.txt";

	static List<Integer> memory;
	static Integer pc;
	static Integer mp;
	static Map<Integer, Integer> jumpMap;
	static String source;
	static String output;

	public static void main(String[] args) throws IOException {
		initAll();
		exec();
	}

	private static void exec() {

		Scanner sc = new Scanner(System.in);

		while (pc < source.length()) {
			switch (source.charAt(pc)) {
				case '>':
					mp++;
					pc++;
					break;
				case '<':
					mp--;
					pc++;
					break;
				case '+':
					memory.set(mp, memory.get(mp) + 1);
					pc++;
					break;
				case '-':
					memory.set(mp, memory.get(mp) - 1);
					pc++;
					break;
				case '.':
					output += (char) (int) memory.get(mp);
					pc++;
					break;
				case ',':
					String temp = sc.next();
					memory.set(mp, (int) temp.charAt(0));
					pc++;
					break;
				case '[':
					if (memory.get(mp) == 0) {
						pc = jumpMap.get(pc) + 1;
					} else {
						pc++;
					}
					break;
				case ']':
					if (memory.get(mp) != 0) {
						pc = jumpMap.get(pc) + 1;
					} else {
						pc++;
					}
					break;
			}
		}
		System.out.println(output);
		sc.close();
	}

	private static String readSource() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(sourceFile)));
		String in = "";
		String code = "";
		while ((in = br.readLine()) != null) {
			code += in;
			code += " ";
		}
		code = translate(code);

		br.close();
		return code;
	}

	private static String translate(String code) {
		String[] tokens = code.split(" ");
		StringBuilder bfCode = new StringBuilder();
		for (String s : tokens) {
			switch (s) {
				case "ahaha":
					bfCode.append(">");
					break;
				case "haha":
					bfCode.append("<");
					break;
				case "peko":
					bfCode.append("+");
					break;
				case "konpeko":
					bfCode.append("-");
					break;
				case "pe":
					bfCode.append(".");
					break;
				case "pekora":
					bfCode.append(",");
					break;
				case "bunbun":
					bfCode.append("[");
					break;
				case "cha":
					bfCode.append("]");
					break;

			}
		}

		return bfCode.toString();
	}

	private static void initAll() throws IOException {

		source = readSource();
		memory = new ArrayList<>();
		jumpMap = new HashMap<>();
		for (int i = 0; i < 1000000; i++) {
			memory.add(0);
		}
		pc = 0;
		mp = 0;
		output = "";
		buildJumpMap();
	}

	private static void buildJumpMap() {
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < source.length(); i++) {
			if (source.charAt(i) == '[') {
				stack.push(i);
			} else if (source.charAt(i) == ']') {
				jumpMap.put(stack.pop(), i);
			}
		}

		Map<Integer, Integer> temp = new HashMap<>();
		for (Integer i : jumpMap.keySet()) {
			temp.put(jumpMap.get(i), i);
		}
		jumpMap.putAll(temp);
		System.out.println(jumpMap);
	}
}
