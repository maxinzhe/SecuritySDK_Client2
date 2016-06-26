package com.PKI;

public class Main {
		public static void main(String []args){
			RSACoderTest test=new RSACoderTest();
			try {
				test.setUp();
				 test.test();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
