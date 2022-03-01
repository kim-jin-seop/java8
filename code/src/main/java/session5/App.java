package session5;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class App {
    public static void main(String[] args) {
        /**
         * Date/Time API
         * 과거 API의 mutable한 문제를 해결 Immutable
         */

        /**
         * Instant : 기계 시간
         * now()로 가져오면 UTC, GMT를 가져온다.
         * atZone을 활용하여 해당 지역의 시간을 가져올 수 있다.
         */
        Instant instant = Instant.now(); // 기준시 UTC, GMT

        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = instant.atZone(zone); // 특정 지역으로 시간 보여주기

        /**
         * LocalDateTime : 사람 시간
         * now()를 활용하면 현재 시간을 알 수 있다.
         * of()를 활용해 특정 시간에 대한 정보를 표현해줄 수 있다.
         */
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now);
        LocalDateTime myBirthDay = LocalDateTime.of(1997, Month.MARCH, 24, 0, 0, 0, 0);

        /**
         * zonedDateTime : 특정 지역의 시간
         * ZoneId를 활용해 특정지역의 Id를 가져올 수 있다
         * ZonedDateTime을 활용하여 특정지역의 현재시간을 가져올 수 있다.
         */
        ZonedDateTime nowInSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        /**
         * Period
         */
        LocalDate today = LocalDate.now();
        LocalDate thisYearBirthday = LocalDate.of(2022, 03, 24);

        Period period = Period.between(today, thisYearBirthday);
        System.out.println(period.getDays());
        Period until = thisYearBirthday.until(today);
        System.out.println(until.get(ChronoUnit.DAYS));

        /**
         * Duration : Machine용 시간 비교
         */
        Instant start = Instant.now();
        Instant plus = start.plus(10, ChronoUnit.SECONDS);
        Duration between = Duration.between(start, plus);
        System.out.println("Duration : "+ between.getSeconds());

        /**
         * DateTime의 format()
         * 시간을 formatting할 수 있음. -> DateTimeFormatter 활용
         */
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formatTime = time.format(format);

        /**
         * parse() : 특정 포멧으로 들어오는 데이터를 파싱해주는 것
         */
        LocalDate parse = LocalDate.parse("06/30/2022", format);
        
        /**
         * 레거시 API 지원
         */
        Date date = new Date();
        Instant dateToInstant = date.toInstant();
        Date InstantToDate = Date.from(dateToInstant);
    }
}