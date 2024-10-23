import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    String getFilePath = "data_inlamningsuppg2.txt";

    public static void main(String[] args) {
        Main main = new Main();
        List<Member> members = main.getMembers(main.getFilePath);
        main.menu(members);
    }

    public void menu(List<Member> memberList) {
        String memberSsnOrName = JOptionPane.showInputDialog("Skriv in personnummer eller namn: ");
        Member member = getMember(memberList, memberSsnOrName);
        String message = validateMember(member);
        if (message == null) {
            printToTrainerFile(member.getName() + " har tränat: " + LocalDate.now());
            message = "Personen är medlem på gymmet";
        }
        JOptionPane.showMessageDialog(null, message);
    }

    Member getMember(List<Member> memberList, String memberSsnOrName) {
        for (Member member : memberList) {
            if (memberSsnOrName.equalsIgnoreCase(member.getName()) ||
                    memberSsnOrName.equals(String.valueOf(member.getSocialSecurityNumber()))) {
                return member;
            }
        }
        return null;
    }

    void printToTrainerFile(String message) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter
                ("Medlemmar_träning.txt", true)))) {
            printWriter.println(message);
        } catch (IOException e) {
            System.out.println("Error... Något gick fel: " + e.getMessage());
        }
    }

    String validateMember(Member member) {
        if (member == null) {
            return "Personen har aldrig varit medlem på gymmet";
        }

        if (checkDate(member) == null) {
            return "Personen är inte längre medlem på gymmet";
        }
        return null;
    }

    List<Member> getMembers(String getFilePath) {
        String firstLine;
        String secondLine;
        List<Member> memberList = new ArrayList<>();
        Path filePath;

        String[] memberDataFirstLine = new String[2];
        LocalDate memberDataSecondLine = null;
        filePath = Paths.get(getFilePath);

        try (Scanner scan = new Scanner(filePath)) {

            while (scan.hasNextLine()) {
                firstLine = scan.nextLine();
                memberDataFirstLine = firstLine.split(",");

                if (memberDataFirstLine.length != 2) {
                    System.out.println("Personnummer och namn har fel format eller saknas");
                    continue;
                }

                if (!scan.hasNextLine()) {
                    System.out.println("Datum raden saknas, medlemmen ignoreras");
                    continue;
                }

                secondLine = scan.nextLine().trim();

                try {
                    memberDataSecondLine = LocalDate.parse(secondLine);
                } catch (DateTimeParseException e) {
                    System.out.println("Datumet är inte ett giltigt format, medlemmen ignoreras.");
                    continue;
                }

                try {
                    Member member = new Member(Long.parseLong(memberDataFirstLine[0].trim()),
                            memberDataFirstLine[1].trim(), memberDataSecondLine);
                    memberList.add(member);
                } catch (NumberFormatException e) {
                    System.out.println("Ogiltigt nummer");
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("Ett oväntat fel inträffade");
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Filen hittades inte");
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Det gick inte att köra programmet");
            e.printStackTrace();
            System.exit(0);
        }
        return memberList;
    }

    public LocalDate checkDate(Member member) {
        LocalDate currentDate = LocalDate.now();
        LocalDate aYearAgo = currentDate.minusYears(1);

        LocalDate memberDate = member.getDateJoinedGym();

        if ((memberDate.isAfter(aYearAgo) || memberDate.isEqual(aYearAgo)) &&
                (memberDate.isBefore(currentDate) || memberDate.isEqual(currentDate))) {
            return memberDate;
        } else {
            return null;
        }
    }
}
