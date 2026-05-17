package at.spengergasse.service;

import at.spengergasse.domain.LibraryMemberException;
import at.spengergasse.domain.Member;
import com.github.javafaker.Faker;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.standard.AnnotatedEndpointConnectionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private ArrayList<Member> members;

    public MemberService(){
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
        String[] accountTypes = { "Standard", "Student", "Premium", "Mitarbeiter" };
        String accountType;
        Boolean membershipActive;

        for (int i = 0; i < count; i++) {
            name = faker.address().firstName();
            email = faker.internet().emailAddress();
            memberSince = LocalDate.now().minusDays((int) (Math.random() * 3650));
            maxBorrowLimit = faker.number().numberBetween(1, 10);
            borrowedBooks = faker.number().numberBetween(0,maxBorrowLimit);
            openFees = faker.number().randomDouble(2, 0, 100);
            accountType = accountTypes[faker.random().nextInt(0, accountTypes.length - 1)];
            membershipActive = faker.bool().bool();

            m = new Member(name, email, memberSince, borrowedBooks, maxBorrowLimit, openFees, accountType, membershipActive);
            members.add(m);
        }
    }

    public ArrayList<Member> findAll(){
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
        if(memberID == null) throw new LibraryMemberException("Fehler remove1: kein Account übergeben!");
        boolean gefunden = false;
        Member m;
        Iterator<Member> iter = members.iterator();
        while(iter.hasNext()){
            m = iter.next();
            if (m.getMemberID().equals(memberID)){
                iter.remove();
                gefunden = true;
            }
        }
        if(!gefunden) throw new LibraryMemberException("Fehler remove1: Kein Account gefunden!");
    }

    public void remove1(Long memberID){
        if(memberID == null) throw new LibraryMemberException("Fehler remove1: kein Account übergeben!");
        boolean gefunden = members.removeIf(member -> member.getMemberID().equals(memberID));
        if(!gefunden) throw new LibraryMemberException("Fehler remove1: Kein Account gefunden!");
    }

    public void newBorrow(Long memberID) {
        if(memberID == null) throw new LibraryMemberException("Fehler newBorrow: kein Account übergeben!");
       members.stream()
               .filter(member -> member.getMemberID().equals(memberID))
               .forEach(member -> member.setBorrowedBooks(member.getBorrowedBooks()+1));
    }

    public void returned(Long memberID) {
        if(memberID == null) throw new LibraryMemberException("Fehler returned: kein Account übergeben!");
        members.stream()
                .filter(member -> member.getMemberID().equals(memberID))
                .forEach(member -> member.setBorrowedBooks(member.getBorrowedBooks()-1));
    }

    @Override
    public String toString(){
        return members.stream()
                .map(s -> s.toString())
                .collect(Collectors.joining("\n"));
    }
}
