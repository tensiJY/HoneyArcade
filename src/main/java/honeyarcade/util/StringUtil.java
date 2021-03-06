package honeyarcade.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class StringUtil {
	
	/**
	 * - (하이픈) 있음
	 * RandomUUID 생성
	 * @return
	 */
	public static String getRandomUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * - 하이픈 제거
	 * RadndomUUID()
	 * @return
	 */
	public static String randomUUID() {
		String str = getRandomUUID();
		return str.replaceAll("-", "");
	}
	
	/**
	 * 현재년도를 구하는 함수	String
	 * ex) 2020
	 * @return
	 */
	public static String getFullYearYYYY() {
		return String.valueOf(getFullYearInt()); 
	}
	
	/**
	 * 현재년도를 구하는 함수	int
	 * @return
	 */
	public static int getFullYearInt() {
		Calendar cal = Calendar.getInstance();
		return cal.get(cal.YEAR);
	}
	
	/**
	 * 현재 달을 구하는 함수	
	 * ex) 2자리이며, 1~9월일경우 0을 붙임 09
	 * @return
	 */
	public static String getMonthMM() {
		int month = getMonthInt();
		String temp = "";
		if(month<10) {
			temp = "0"+month;
		}else {
			temp = ""+month;
		}
		return temp;
	}
	
	/**
	 * 현재 달을 구하는 함수
	 * ex) 1월~9월은 1자리 // 10~12월은 2자리	
	 * @return
	 */
	public static String getMonthString() {
		return String.valueOf(getMonthInt());
	}
	
	/**
	 * 현재 달을 구하는 함수
	 * ex) 1월~9월은 1자리 // 10~12월은 2자리
	 * @return
	 */
	public static int getMonthInt() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get (cal.MONTH) + 1 ;
		return month;
	}
	
	/**
	 * 현재 일을 구하는 함수
	 * 1~9는 1자리
	 * @return
	 */
	public static int getDayInt() {
		Calendar cal = Calendar.getInstance();
		return cal.get(cal.DATE);
	}
	
	/**
	 * 현재 일을 구하는 함수
	 * 1~9는 0을 붙여서 01~09 이며, 나머지는 2자리
	 * @return
	 */
	public static String getDayDD() {
		int date = getDayInt();
		String temp = "";
		if(date<10) {
			temp = "0"+date;
		}else {
			temp = ""+date;
		}
		return temp;
	}
	
	/**
	 * 지정한 날짜에 일수를 더함
	 * @param ymd		yyyy-MM-dd
	 * @param amount	
	 * @return
	 */
	public static String addDate(String ymd, int amount) {
		 Calendar cal = Calendar.getInstance();
	     DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	     try {
			cal.setTime(df.parse(ymd));
			cal.add(Calendar.DATE, amount);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return df.format(cal.getTime());
	}
	
	/**
	 * 	월요일 기준 - 일주일의 첫날(월요일)
	 * @param yyyyMMdd
	 * @return
	 */
	public static String getFirstDayOfWeek(String yyyyMMdd) {
		String startDt = yyyyMMdd;
		String[] dateArray = startDt.split("-");		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(dateArray[0]), (Integer.parseInt(dateArray[1]) - 1), Integer.parseInt(dateArray[2]));
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
		cal.add(Calendar.DAY_OF_MONTH, - dayOfWeek);
		
		return formatter.format(cal.getTime());
	}
	
	/**
	 * 	월요일 기준	- 일주일의 마지막날(일요일)
	 * @param yyyyMMdd
	 * @return
	 */
	public static String getLastDayOfWeek(String yyyyMMdd) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		String stDt = getFirstDayOfWeek(yyyyMMdd);
		Calendar cal = Calendar.getInstance();
		String[] dateArray = stDt.split("-");	
		cal.set(Integer.parseInt(dateArray[0]), (Integer.parseInt(dateArray[1]) - 1), Integer.parseInt(dateArray[2]));
		cal.add(Calendar.DAY_OF_MONTH, 6);
		String edDt = formatter.format(cal.getTime());
		return edDt;
	}
	
	/**
	 * 비밀번호 암호화
	 * @param S
	 * @return
	 */
	public static String getPasswordEncode(String password) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		return bc.encode(password);
	}
	
	/**
	 * @description : <pre>주문번호 return</pre>
	 * @param len : 출력하려는 난수의 자릿수
	 * @return
	 */
	public static String createMerchantId(int len){
		String merChantId = "ORD-";
		StringBuffer rnd = new StringBuffer();
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		merChantId += format.format(now) + "-" + getRandomNumberStr(len);

		// ORD-YYYYMMDD-난수
		return merChantId;
	}
	
	/**
	 * @description : <pre>숫자 난수 발생</pre>
	 * @param len : 출력하려는 난수의 자릿수
	 * @return
	 */
	public static String getRandomNumberStr(int len){
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < len; i++) {
			temp.append((rnd.nextInt(10)));
		}
		return temp.toString();
	}
	
	/**
	 * @description : <pre>특수문자, 영문대소문자, 숫자 포함 난수 발생</pre>
	 * @param len : 출력하려는 난수의 자릿수
	 * @return
	 */
	public static String getRandomSpecialEnglishNumberStr(int len){
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < len; i++) {
			int rIndex = rnd.nextInt(4);
			switch (rIndex) {
				case 0:
					temp.append((char) ((int) (rnd.nextInt(26)) + 97)); // 소문자
					break;
				case 1:
					temp.append((char) ((int) (rnd.nextInt(26)) + 65)); // 대문자
					break;
				case 2:
					temp.append((char) ((int) (rnd.nextInt(15)) + 33)); // 특수문자
					break;
				case 3:
					temp.append((rnd.nextInt(10))); // 숫자
					break;
			}
		}
		return temp.toString();
	}

	/**
	 * 해당하는 월의 마지막 일자를 구하는 함수
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getLastDayOfMonth(Integer year, Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, 01);	//월은 -1해줘야 해당월로 인식
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
}
