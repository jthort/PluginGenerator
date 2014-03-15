package com.daawsomest.plugingenerator.plugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.text.StyledEditorKit.BoldAction;

public class JavaWriter {
	BufferedWriter out;
	
	public JavaWriter(String name, String dir) throws IOException {
		out = new BufferedWriter(new FileWriter(dir + name + "/data/com/pack/" + name + ".java"));
	}
	
	public void addClass(String name, String extra) throws IOException {
		out.write("public class " + name + " " + extra + " "+ "{");
		out.newLine();
	}
	
	public void addClass(String name) throws IOException {
		out.write("public class " + name + " "+ "{");
		out.newLine();
	}
	
	public void addImport(String importDir) throws IOException {
		out.write("import " + importDir + ";");
		out.newLine();
	}
	
	public void declarePackage(String packageName) throws IOException {
		out.write("package " + packageName + ";");
		out.newLine();
	}
	
	public void write(String string) throws IOException {
		out.write(string);
		out.newLine();
	}
	
	
	public void newLine() throws IOException {
		out.newLine();
	}
	
	public void addReturn(boolean b) throws IOException {
		out.write("return " + Boolean.toString(b) + ";");
		out.newLine();
	}
	
	public void declareInt(String modifier,String name, int value) throws IOException {
		out.write(modifier + " int " + name + " = " + value + ";");
		out.newLine();
	}
	
	public void declareInt(String name, int value) throws IOException {
		out.write("int " + name + " = " + value + ";");
		out.newLine();
	}
	
	public void declareString(String modifier,String name, String value) throws IOException {
		out.write(modifier + " " + name + " = " + value + ";");
		out.newLine();
	}
	
	public void method(String modifier, String type, String name, String args) throws IOException {
		out.write(modifier +" " + type + " " + name + "(" + args + ") {");
		out.newLine();
	}
	
	public void method(String modifier, String type, String name) throws IOException {
		out.write(modifier +" " + type + " " + name + "() {");
		out.newLine();
	}
	
	public void print(String string) throws IOException {
		out.write("System.out.println(" + string + ");");
		out.newLine();
	}
	public void addMain() throws IOException {
		out.write("public static void main(String[] args) {");
		out.newLine();
	}
	
	public void end() throws IOException {
		out.write("}");
		out.newLine();
	}
	public void close() throws IOException {
		out.close();
	}
	
	public void annotation(String annotation) throws IOException {
		out.write("@" + annotation);
		out.newLine();
	}
	
}
