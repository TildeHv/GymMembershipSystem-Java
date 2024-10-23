import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MainTest {

    @Test
    void getMembersTest() {
        Main main = new Main();
        List<Member> members = main.getMembers("data_inlamningsuppg2.txt");
        System.out.println(members);
        assertEquals(14, members.size());
    }

    @Test
    void getMembersTest_missingValue() {
        Main main = new Main();
        List<Member> members = main.getMembers("data_inlamningsuppg2_missingValue.txt");
        System.out.println(members);
        assertEquals(13, members.size());
    }

    @Test
    void getMembersTest_wrongDateFormat() {
        Main main = new Main();
        List<Member> members = main.getMembers("data_inlamningsuppg2_wrongDateFormat.txt");
        System.out.println(members);
        assertEquals(13, members.size());
    }

    @Test
    void getMembersTest_missingFirstLine() {
        Main main = new Main();
        List<Member> members = main.getMembers("data_inlamningsuppg2_missingFirstLine.txt");
        System.out.println(members);
        assertEquals(13, members.size());
    }

    @Test
    void checkDateTest() {
        Main main = new Main();
        LocalDate now = LocalDate.now();
        Member member = new Member(1234, "Lisa", now);
        assert(main.checkDate(member).isEqual(now));
    }

    @Test
    void checkOldDateTest() {
        Main main = new Main();
        LocalDate localDate = LocalDate.now().minusYears(2);
        Member member = new Member(1234, "Lisa", localDate);
        assertNull(main.checkDate(member));
    }

    @Test
    void checkFutureDateTest() {
        Main main = new Main();
        LocalDate localDate = LocalDate.now().plusYears(2);
        Member member = new Member(1234, "Lisa", localDate);
        assertNull(main.checkDate(member));
    }

    @Test
    void getMemberTest() {
        Main main = new Main();
        List<Member> memberList = new ArrayList<>();
        Member member = new Member(1234, "Lisa", LocalDate.now());
        memberList.add(member);
        assertEquals(main.getMember(memberList, "Lisa"), member);
    }

    @Test
    void getMemberWrongNameTest() {
        Main main = new Main();
        List<Member> memberList = new ArrayList<>();
        Member member = new Member(1234, "Karl", LocalDate.now());
        memberList.add(member);
        assertNull(main.getMember(memberList, "Lisa"));
    }

    @Test
    void getMessageTest() {
        Main main = new Main();
        Member member = new Member(1234, "Lisa", LocalDate.now());
        assertNull(main.validateMember(member));
    }

    @Test
    void getNoLongerMessageTest() {
        Main main = new Main();
        LocalDate localDate = LocalDate.now().minusYears(2);
        Member member = new Member(1234, "Lisa", localDate);
        assertEquals(main.validateMember(member), "Personen är inte längre medlem på gymmet");
    }

    @Test
    void getNoMemberMessageTest() {
        Main main = new Main();
        assertEquals(main.validateMember(null), "Personen har aldrig varit medlem på gymmet");
    }

}