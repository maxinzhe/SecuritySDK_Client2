package com.sklois.haiyunKmsinvoip;

import com.sklois.haiyunKms.SoftLibs;

public class Main {
	public static void main(String args[]){
		SoftLibs softLibs=  SoftLibs.getInstance();
		System.out.println(softLibs.deleteLocalData("001"));
	}

}
