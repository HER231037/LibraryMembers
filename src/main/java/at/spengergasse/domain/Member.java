package at.spengergasse.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@ToString
@EqualsAndHashCode(of = "memberID", callSuper = false)
@Entity
public class Member implements Cloneable{

    @Id
    private Long memberID;
    private static final AtomicLong sequence = new AtomicLong(1000);

        //TODO: eventuell noch ein Dropdown-String hinzufügen/ergänzen.

    private String name;
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
        setMaxBorrowLimit(5);
        setBorrowedBooks(4);
        setOpenFees(25.5);
        setAccountType("Mitarbeiter");
        setMembershipActive(true);
    }

    public Member(Long memberID, String name, String email, LocalDate memberSince, Integer borrowedBooks, Integer maxBorrowLimit, Double openFees, String accountType, Boolean membershipActive)  {
        setMemberId(memberID);
        setName(name);
        setEmail(email);
        setMemberSince(memberSince);
        setMaxBorrowLimit(maxBorrowLimit);
        setBorrowedBooks(borrowedBooks);
        setOpenFees(openFees);
        setAccountType(accountType);
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
        setMaxBorrowLimit(maxBorrowLimit);
        setBorrowedBooks(borrowedBooks);
        setOpenFees(openFees);
        setAccountType(accountType);
        setMembershipActive(membershipActive);
    }

    public void setName(String name) {
        if(name.isBlank()) throw new LibraryMemberException("Name ungültig!");
        this.name = name;
    }

    public void setEmail(String email) {
        if(email.isBlank()) throw new LibraryMemberException("E-Mail ungültig!");
        this.email = email;
    }

    public void setMemberSince(LocalDate memberSince) {
        this.memberSince = memberSince;
    }

    public void setBorrowedBooks(Integer borrowedBooks) {
        if(borrowedBooks > maxBorrowLimit) throw new LibraryMemberException("Mitglied darf nicht mehr Bücher ausborgen als das Limit!");
        if(borrowedBooks < 0) throw new LibraryMemberException("Ungueltiger Wert BorrowedBooks!");
        this.borrowedBooks = borrowedBooks;
    }

    public void setMaxBorrowLimit(Integer maxBorrowLimit) {
        if(maxBorrowLimit < 1) throw new LibraryMemberException("Ungueltiger Wert MaxBorrowedBooks!");
        this.maxBorrowLimit = maxBorrowLimit;
    }

    public void setOpenFees(Double openFees) {
        if(openFees < 0) throw new LibraryMemberException("Ungueltiger Wert openFees!");
        this.openFees = openFees;
    }

    public void setAccountType(String accountType) {
        if(!Arrays.asList(accountTypes).contains(accountType)) throw new LibraryMemberException("Fehler setAccountTypes!");
        this.accountType = accountType;
    }

    public void setMembershipActive(Boolean membershipActive) {
        this.membershipActive = membershipActive;
    }


    @Override
    public Member clone(){
        return new Member(memberID, name, email, memberSince, borrowedBooks, maxBorrowLimit, openFees, accountType, membershipActive);
    }
}
