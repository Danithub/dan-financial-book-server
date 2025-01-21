package dan.example.dan_financial_book.calendar.utils;

import dan.example.dan_financial_book.calendar.dto.DateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@Slf4j
public class DateUtils {

    /**
     * 오늘 일자를 리턴한다.
     * @return LocalDate
     */
    public static LocalDate getToday(){
        return LocalDate.now();
    }

    /**
     * yyyyMMdd 형식의 오늘 일자를 문자열로 리턴한다.
     * @return 문자열
     */
    public static String getTodayString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.now().format(formatter);
    }

    /**
     * 패턴 형식의 오늘 일자를 문자열로 리턴한다.
     * @param pattern ex) yyyy-MM-dd
     * @return 문자열
     */
    public static String getTodayString(String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.now().format(formatter);
    }

    /**
     * 문자열을 날짜 객체로 변환한다.
     * @param date yyyyMMdd 형식의 문자열
     * @return Date
     */
    public static Date parseDate(String date){
        return convertStringToDate(date, "yyyyMMdd");
    }

    /**
     * 문자열을 날짜 객체로 변환한다.
     * @param date 날짜 형식의 문자열
     * @param formatter ex) yyyy-MM-dd
     * @return Date
     */
    public static Date parseDate(String date, String formatter){
        return convertStringToDate(date, formatter);
    }

    private static Date convertStringToDate(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try{
            return sdf.parse(date);
        } catch (ParseException ex) {
            log.info("ParseException ========> {}", ex.toString());
            return null;
        }
    }

    public static DateDto getTimeSpan(String from, String to) {
        return getDateDiff(from, to, "yyyyMMdd");
    }

    public static DateDto getTimeSpan(String from, String to, String pattern) {
        return getDateDiff(from, to, pattern);
    }

    private static DateDto getDateDiff(String _from ,String _to, String pattern) {
        // 문자열을 LocalDate로 변환
        LocalDate from;
        LocalDate to;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            from = dateFormat.parse(_from).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            to = dateFormat.parse(_to).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException ex){
            log.info("ParseException =========> {}", ex.toString());
            return null;
        }

        // 두 날짜 간의 차이 계산
        Period period = Period.between(from, to);

        // 결과를 DateDifference 객체로 반환
        return DateDto.builder().years(period.getYears()).month(period.getMonths()).day(period.getDays()).build();
    }

    public static DateDto toDateDtoInstance(LocalDate date){
         return DateDto.builder().years(date.getYear()).month(date.getMonthValue()).day(date.getDayOfMonth()).build();
    }
}
