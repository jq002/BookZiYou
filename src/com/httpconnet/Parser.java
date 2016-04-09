package com.httpconnet;



public class Parser {
	public String getreturn(String request) {
		String[] message = request.split("\n");
		String flag= message[0];
		return flag;
	}
	public String getHour(String request) {
		String[] message = request.split(" : ");
		String flag= message[0];
		return flag;
	}
	public String getMin(String request) {
		String[] message = request.split(" : ");
		String flag= message[1];
		return flag;
	}
	
}
