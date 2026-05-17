package at.spengergasse.service;

import at.spengergasse.domain.Member;
import com.github.javafaker.Faker;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private ArrayList<Member> members;

    public MemberService(){
        members = new ArrayList<>(1000);
        fillTestData(20);
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
            maxBorrowLimit = faker.number().numberBetween(0, 10);
            borrowedBooks = faker.number().numberBetween(0,maxBorrowLimit);
            openFees = faker.number().randomDouble(2, 0, 100);
            accountType = accountTypes[faker.random().nextInt(0, accountTypes.length - 1)];
            membershipActive = faker.bool().bool();

            m = new Member(name, email, memberSince, borrowedBooks, maxBorrowLimit, openFees, accountType, membershipActive);
            members.add(m);
        }
    }

    @Override
    public String toString(){
        return members.stream()
                .map(s -> s.toString())
                .collect(Collectors.joining("\n"));
    }
}
