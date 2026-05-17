package at.spengergasse.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "memberID", callSuper = false)
@Entity
public class Member implements Cloneable{

    @Id
    private Long memberID;
    private static final AtomicLong sequence = new AtomicLong(1000);

        //TODO: eventuell noch ein Dropdown-String hinzufügen/ergänzen.
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    private LocalDate memberSince;
    private Integer borrowedBooks;
    private Integer maxBorrowLimit;
    private Double openFees;

    private String accountType;
    private static final String[] accountTypes = { "Standard", "Student", "Premium", "Mitarbeiter" };

    private Boolean membershipActive;

    public Member() {
        setMemberId();
        setName("Brian Hertenberger");
        setEmail("hertenberger@spengergasse.at");
        setMemberSince(LocalDate.now());
        setBorrowedBooks(4);
        setMaxBorrowLimit(5);
        setOpenFees(25.5);
        setAccountTypes("Mitarbeiter");
        setMembershipActive(true);
    }

    public Member(Long memberID, String name, String email, LocalDate memberSince, Integer borrowedBooks, Integer maxBorrowLimit, Double openFees, String accountType, Boolean membershipActive)  {
        setMemberId(memberID);
        setName(name);
        setEmail(email);
        setMemberSince(memberSince);
        setBorrowedBooks(borrowedBooks);
        setMaxBorrowLimit(maxBorrowLimit);
        setOpenFees(openFees);
        setAccountTypes(accountType);
        setMembershipActive(membershipActive);
    }

    public void setMemberId(){
        memberID = sequence.getAndIncrement();
    }

    private void setMemberId(Long memberID) {
        this.memberID = memberID;
    }

    public Member(String name, String email, LocalDate memberSince, Integer borrowedBooks, Integer maxBorrowLimit, Double openFees, String accountType, Boolean membershipActive)  {
        setMemberId();
        setName(name);
        setEmail(email);
        setMemberSince(memberSince);
        setBorrowedBooks(borrowedBooks);
        setMaxBorrowLimit(maxBorrowLimit);
        setOpenFees(openFees);
        setAccountTypes(accountType);
        setMembershipActive(membershipActive);
    }


    public void setAccountTypes(String accountType) {
        if(!Arrays.asList(accountTypes).contains(accountType)) throw new LibraryMemberException("Fehler setAccountTypes!");
        this.accountType = accountType;
    }

    @Override
    public Member clone(){
        return new Member(memberID, name, email, memberSince, borrowedBooks, maxBorrowLimit, openFees, accountType, membershipActive);
    }
}
