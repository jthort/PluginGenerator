package com.daawsomest.plugingenerator.plugin;

import java.io.File;

public class CreateFolders {
	
	public CreateFolders(String dir, String name) {
		File folder1 = new File(dir + name + "/data/com/pack");
		//File folder2 = new File(dir + name + "/output");
		
		folder1.mkdirs();
		//folder2.mkdirs();
	}
	
}
