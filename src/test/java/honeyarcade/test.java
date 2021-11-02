package honeyarcade;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class test {

 	
	@Test
	public void asdf() {
		int year = 2017;
        int month = 2;
        int day = 1;
 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 
        Calendar cal = Calendar.getInstance();
 
        cal.set(year, month-1, day); //월은 -1해줘야 해당월로 인식
 
        System.out.println(dateFormat.format(cal.getTime()));
 
        System.out.println("해당년도: "+cal.get(Calendar.YEAR));
        System.out.println("해당월: "+(cal.get(Calendar.MONTH)+1)); //보여줄때 +1로 하여 사람기준으로 설정
        System.out.println("첫번째 일: "+cal.getMinimum(Calendar.DAY_OF_MONTH));
 
        System.out.println("마지막 일(현재 날짜 기준 최대수): "+cal.getActualMaximum(Calendar.DAY_OF_MONTH)); //기본적으로 이걸 사용
        System.out.println("마지막 일(Calender이 가진 최대수): "+cal.getMaximum(Calendar.DAY_OF_MONTH));
 
	}

	
	//	월요일 기준 - 일주일의 첫날(월요일)
	private String getFirstDayOfWeek(String yyyyMMdd) {
		String startDt = yyyyMMdd;
		String[] dateArray = startDt.split("-");		
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(dateArray[0]), (Integer.parseInt(dateArray[1]) - 1), Integer.parseInt(dateArray[2]));
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
		cal.add(Calendar.DAY_OF_MONTH, - dayOfWeek);
		
		return formatter.format(cal.getTime());
	}
	
	//	월요일 기준	- 일주일의 마지막날(일요일)
	private String getLastDayOfWeek(String yyyyMMdd) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		String stDt = getFirstDayOfWeek(yyyyMMdd);
		Calendar cal = Calendar.getInstance();
		String[] dateArray = stDt.split("-");	
		cal.set(Integer.parseInt(dateArray[0]), (Integer.parseInt(dateArray[1]) - 1), Integer.parseInt(dateArray[2]));
		cal.add(Calendar.DAY_OF_MONTH, 6);
		String edDt = formatter.format(cal.getTime());
		return edDt;
	}
}
