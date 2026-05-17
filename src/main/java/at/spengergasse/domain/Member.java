package at.spengergasse.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
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

    @NotBlank(message = "Name darf nicht leer sein!")
    @Size(min = 3, max = 50, message = "Name muss zwischen 3 und 50 Zeichen lang sein!")
    private String name;
    @Email(message = "Ungültige E-Mail Adresse!")
    private String email;
    @NotNull(message = "Datum muss angegeben werden!")
    @PastOrPresent(message = "Datum darf nicht in der Zukunft liegen!")
    private LocalDate memberSince;
    @NotNull(message = "Anzahl max. Anzahl ausgeborgter Bücher darf nicht null/leer sein!")
    @Min(value = 1, message = "Wert unter 1 ist unzulässig!")
    @Max(value = 10, message = "Member darf nicht mehr als 10 Bücher gleichzeitig ausborgen!")
    private Integer maxBorrowLimit;
    @NotNull(message = "Anzahl ausgeborgter Bücher darf nicht null/leer sein!")
    @Min(value = 0, message = "Wert unter 0 ist unzulässig!")
    private Integer borrowedBooks;
    @NotNull(message = "Anzahl der offenen Gebühren muss mindestens 0 sein!")
    @DecimalMin(value = "0", message = "Anzahl der offenen Gebühren darf nicht weniger als 0 sein!")
    @DecimalMax(value = "500", message = "Member darf offene Gebühren von 500€ nicht überschreiten!")
    private Double openFees;
    @NotBlank(message = "Accounttype darf nicht leer sein!")
    @Pattern(regexp = "Standard|Student|Premium|Mitarbeiter", message = "Folgende Accounttypes sind zulässig: Standard, Student, Premium, Mitarbeiter")
    private String accountType;
    private Boolean membershipActive;

    @AssertTrue(message = "Anzahl ausgeborgter Bücher darf nicht größer als das maximale Ausleihlimit sein!")
    public boolean isBorrowedBooksValid() {
        if (borrowedBooks == null || maxBorrowLimit == null) {
            return true;
        }
        return borrowedBooks <= maxBorrowLimit;
    }

    public Member() {
        setMemberId();
        setName("");
        setEmail("");
        setMemberSince(LocalDate.now());
        setMaxBorrowLimit(1);
        setBorrowedBooks(0);
        setOpenFees(0d);
        setAccountType("");
        setMembershipActive(false);
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

    @Override
    public Member clone(){
        return new Member(memberID, name, email, memberSince, borrowedBooks, maxBorrowLimit, openFees, accountType, membershipActive);
    }
}
