package com.llb.cxy.core.utils;

import java.util.Base64;

public class Base64Util {

	public Base64Util() {
	}

	public static String decodeBuffer(String encodedText) throws Exception {
		Base64.Decoder decoder = Base64.getDecoder();
		return new String(decoder.decode(encodedText), "UTF-8");
	}

	public static String encodeBuffer(String text) throws Exception {
		Base64.Encoder encoder = Base64.getEncoder();
		if (text != null) {
			return encoder.encodeToString(text.getBytes("UTF-8"));
		} else {
			return "";
		}
	}
}
