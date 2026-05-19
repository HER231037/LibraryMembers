/*
    PROJEKT VON BRIAN HERTENBERGER 6ABIF 19.05.2026
 */

package at.spengergasse.service;

import at.spengergasse.domain.LibraryMemberException;
import at.spengergasse.domain.Member;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private ArrayList<Member> members;

    public MemberService() {
        members = new ArrayList<>(1000);
        fillTestData(100);
    }

    public void fillTestData(int count) {
        Member m;
        Faker faker = new Faker();

        String name;
        String email;
        LocalDate memberSince;
        Integer borrowedBooks;
        Integer maxBorrowLimit;
        Double openFees;
        String[] accountTypes = {"Standard", "Student", "Premium", "Mitarbeiter"};
        String accountType;
        Boolean membershipActive;

        for (int i = 0; i < count; i++) {
            name = faker.address().firstName();
            email = faker.internet().emailAddress();
            memberSince = LocalDate.now().minusDays((int) (Math.random() * 3650));
            maxBorrowLimit = faker.number().numberBetween(1, 10);
            borrowedBooks = faker.number().numberBetween(0, maxBorrowLimit);
            openFees = faker.number().randomDouble(2, 0, 100);
            accountType = accountTypes[faker.random().nextInt(0, accountTypes.length - 1)];
            membershipActive = faker.bool().bool();

            m = new Member(name, email, memberSince, borrowedBooks, maxBorrowLimit, openFees, accountType, membershipActive);
            members.add(m);
        }
    }

    public ArrayList<Member> findAll() {
        ArrayList<Member> copy;
        copy = new ArrayList<>(members);
        return copy;
    }

    public void removeAllAccounts() {
        members.clear();
    }

    public void addMember(Member m) {
        members.add(m);
    }

    public void remove1_oldschool(Long memberID) {
        if (memberID == null) throw new LibraryMemberException("Fehler remove1: kein Account übergeben!");
        boolean gefunden = false;
        Member m;
        Iterator<Member> iter = members.iterator();
        while (iter.hasNext()) {
            m = iter.next();
            if (m.getMemberID().equals(memberID)) {
                iter.remove();
                gefunden = true;
            }
        }
        if (!gefunden) throw new LibraryMemberException("Fehler remove1: Kein Account gefunden!");
    }

    public void remove1(Long memberID) {
        if (memberID == null) throw new LibraryMemberException("Fehler remove1: kein Account übergeben!");
        boolean gefunden = members.removeIf(member -> member.getMemberID().equals(memberID));
        if (!gefunden) throw new LibraryMemberException("Fehler remove1: Kein Account gefunden!");
    }

    public void newBorrow(Long memberID) {
        if (memberID == null) throw new LibraryMemberException("Fehler newBorrow: kein Account übergeben!");
        Member member = members.stream()
                .filter(m -> m.getMemberID().equals(memberID))
                .findFirst()
                .orElseThrow(() -> new LibraryMemberException("Fehler newBorrow: Kein Account gefunden!"));

        if (member.getBorrowedBooks() >= member.getMaxBorrowLimit()) {
            throw new LibraryMemberException("Mitglied darf nicht mehr Bücher ausborgen als das Limit!");

        }
        member.setBorrowedBooks(member.getBorrowedBooks() + 1);
    }

    public void returned(Long memberID) {
        if (memberID == null) {
            throw new LibraryMemberException("Fehler returned: kein Account übergeben!");
        }

        Member member = members.stream()
                .filter(m -> m.getMemberID().equals(memberID))
                .findFirst()
                .orElseThrow(() -> new LibraryMemberException("Fehler returned: Kein Account gefunden!"));

        if (member.getBorrowedBooks() <= 0) {
            throw new LibraryMemberException("Mitglied kann nicht weniger als 0 Bücher ausgeliehen haben!");
        }

        member.setBorrowedBooks(member.getBorrowedBooks() - 1);
    }

    @Override
    public String toString() {
        return members.stream()
                .map(s -> s.toString())
                .collect(Collectors.joining("\n"));
    }
}
