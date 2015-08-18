package com.test.java;

import java.io.InputStream;
import java.util.Arrays;
/**
 * java中常用的各种类型的转换，包括bytesToHexString，hexStringToBytes
 * 
 * @author penghong
 *
 */

public class ComTool {
	/**
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * charToByte，ConcatArrays(连接bytes)，GetPartofbyteArray，getBitStringBuffer，getFileBytes，bitStringTObyte
	 * byteToBit，ConvertToBinaryArray等等。
	 * @param src
	 *            byte[] data
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * Utility method to concatenate two byte arrays.
	 * 
	 * @param first
	 *            First array
	 * @param rest
	 *            Any remaining arrays
	 * @return Concatenated copy of input arrays
	 */
	public static byte[] ConcatArrays(byte[] first, byte[]... rest) {
		int totalLength = first.length;
		for (byte[] array : rest) {
			totalLength += array.length;
		}
		byte[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (byte[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	// get some bytes of byte[]
	public static byte[] GetPartofbyteArray(int nStartIndex, int nEndIndex,
			byte[] data) {
		byte[] byRet;
		if (nStartIndex > data.length || nEndIndex > data.length
				|| nStartIndex < 0 || nEndIndex < 0 || nStartIndex > nEndIndex) {
			return null;
		} else {
			byRet = new byte[nEndIndex - nStartIndex];
			for (int i = 0; i < nEndIndex - nStartIndex; i++) {
				byRet[i] = data[nStartIndex + i];
			}

			return byRet;
		}
	}

	/**
	 * 返回一个文件的bit流，用String表示此bit流
	 * 
	 * @param srcFileStream
	 *            文件流
	 * @param code
	 *            文件字符编码 一般为gb2312 utf-8或者 gbk编码
	 * @return 返回文件bit流
	 */

	// public static int testVal;
	public static StringBuffer getBitStringBuffer(InputStream srcFileStream,
			String code) {
		StringBuffer sb = new StringBuffer();
		byte[] filebytes = getFileBytes(srcFileStream, code);
		for (int i = 0; i < filebytes.length; i++) {
			sb.append(byteToBit(filebytes[i]));
		}
		return sb;

	}

	/**
	 * 得到输入文件的 byte[]。
	 * 
	 * @param srcFileStream
	 *            输入文件流
	 * @param code
	 *            文档编码
	 * @return 返回文件的byte[]
	 */

	public static byte[] getFileBytes(InputStream srcFileStream, String code) {
		StringBuffer sb = new StringBuffer();
		byte[] bytes = new byte[1024 * 10];

		try {

			// InputStreamReader isr=new InputStreamReader(srcFileStream,code);
			// BufferedReader in=new BufferedReader(isr);
			// while(in.ready()){
			// sb.append(in.readLine()+"\n");
			// }
			// while(srcFileStream.read())
			srcFileStream.read(bytes);

			srcFileStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// return sb.toString().getBytes();
		return bytes;

	}

	/**
	 * 将位流转换为byte[]
	 * 
	 * @param sb
	 *            输入的位流
	 * @return 返回一个byte[]
	 */

	public static byte[] bitStringTObyte(StringBuffer sb) {
		int len = sb.length() / 8;
		len = len * 8;
		byte[] bytes = new byte[sb.length() / 8];
		int k, n = 0;
		char[] buff = new char[8];
		for (int i = 0, j = i + 8; i < len; i = i + 8, j = j + 8) {
			sb.getChars(i, j, buff, 0);
			if (buff[0] == '0')
				k = Integer.parseInt(new String(buff), 2);
			else
				k = Integer.parseInt(new String(buff), 2) - 256;
			bytes[n++] = (byte) k;

		}
		return bytes;
	}

	/**
	 * 将byte转换为bit串
	 * 
	 * @param b
	 * @return
	 */

	public static String byteToBit(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
				+ (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
				+ (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
				+ (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
	}

	/**
	 * 将long型转换为 二进制
	 * 
	 * @param x
	 * @return
	 */
	private byte[] ConvertToBinaryArray(long x) {
		byte[] binaryArray = new byte[24];
		for (int i = 0; i != 23; i++) {
			binaryArray[23 - i] = (byte) (x & 1);
			x = x >> 1;
		}
		return binaryArray;
	}

	/**
	 * 将byte 转为二进制
	 * 
	 * @param array
	 * @return
	 */
	private byte[] ConvertToBinaryArray(byte[] array) {
		byte[] binaryArray = new byte[24];
		int a = array[0];
		int b = array[1];
		int c = array[2];
		for (int i = 0; i != 8; i++) {
			binaryArray[7 - i] = (byte) (a & 1);
			a = a >> 1;
		}
		for (int i = 0; i != 8; i++) {
			binaryArray[15 - i] = (byte) (b & 1);
			b = b >> 1;
		}
		for (int i = 0; i != 8; i++) {
			binaryArray[23 - i] = (byte) (c & 1);
			c = c >> 1;
		}
		return binaryArray;
	}

}
