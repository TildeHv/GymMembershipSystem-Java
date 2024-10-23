import java.time.LocalDate;

public class Member {

    long socialSecurityNumber;
    String name;
    LocalDate dateJoinedGym;

    public Member(long socialSecurityNumber, String name, LocalDate dateJoinedGym) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
        this.dateJoinedGym = dateJoinedGym;
    }

    public long getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(long socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateJoinedGym() {
        return dateJoinedGym;
    }

    public void setDateJoinedGym(LocalDate dateJoinedGym) {
        this.dateJoinedGym = dateJoinedGym;
    }

    @Override
    public String toString() {
        return "Member{" +
                "socialSecurityNumber=" + socialSecurityNumber +
                ", name='" + name + '\'' +
                ", dateJoinedGym='" + dateJoinedGym + '\'' +
                '}';
    }
}
