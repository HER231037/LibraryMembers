package at.spengergasse.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Member createValidMember() {
        Member member = new Member();

        member.setName("Max Mustermann");
        member.setEmail("max.mustermann@example.com");
        member.setMemberSince(LocalDate.now().minusDays(10));
        member.setMaxBorrowLimit(5);
        member.setBorrowedBooks(2);
        member.setOpenFees(0.0);
        member.setAccountType("Standard");
        member.setMembershipActive(true);

        return member;
    }

    @Test
    void validMemberShouldHaveNoViolations() {
        Member member = createValidMember();

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertTrue(violations.isEmpty());
    }

    @Test
    void nameMustNotBeBlank() {
        Member member = createValidMember();
        member.setName("");

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void nameMustHaveAtLeastThreeCharacters() {
        Member member = createValidMember();
        member.setName("Ma");

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void nameMustNotHaveMoreThanFiftyCharacters() {
        Member member = createValidMember();
        member.setName("A".repeat(51));

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void emailMustBeValid() {
        Member member = createValidMember();
        member.setEmail("ungueltige-email");

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void memberSinceMustNotBeNull() {
        Member member = createValidMember();
        member.setMemberSince(null);

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("memberSince")));
    }

    @Test
    void memberSinceMustNotBeInFuture() {
        Member member = createValidMember();
        member.setMemberSince(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("memberSince")));
    }

    @Test
    void maxBorrowLimitMustNotBeNull() {
        Member member = createValidMember();
        member.setMaxBorrowLimit(null);

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("maxBorrowLimit")));
    }

    @Test
    void maxBorrowLimitMustBeAtLeastOne() {
        Member member = createValidMember();
        member.setMaxBorrowLimit(0);

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("maxBorrowLimit")));
    }

    @Test
    void maxBorrowLimitMustNotBeGreaterThanTen() {
        Member member = createValidMember();
        member.setMaxBorrowLimit(11);

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("maxBorrowLimit")));
    }

    @Test
    void borrowedBooksMustNotBeNull() {
        Member member = createValidMember();
        member.setBorrowedBooks(null);

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("borrowedBooks")));
    }

    @Test
    void borrowedBooksMustNotBeNegative() {
        Member member = createValidMember();
        member.setBorrowedBooks(-1);

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("borrowedBooks")));
    }

    @Test
    void borrowedBooksMustNotBeGreaterThanMaxBorrowLimit() {
        Member member = createValidMember();
        member.setMaxBorrowLimit(5);
        member.setBorrowedBooks(6);

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("borrowedBooksValid")));
    }

    @Test
    void openFeesMustNotBeNull() {
        Member member = createValidMember();
        member.setOpenFees(null);

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("openFees")));
    }

    @Test
    void openFeesMustNotBeNegative() {
        Member member = createValidMember();
        member.setOpenFees(-1.0);

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("openFees")));
    }

    @Test
    void openFeesMustNotBeGreaterThanFiveHundred() {
        Member member = createValidMember();
        member.setOpenFees(501.0);

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("openFees")));
    }

    @Test
    void accountTypeMustNotBeBlank() {
        Member member = createValidMember();
        member.setAccountType("");

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("accountType")));
    }

    @Test
    void accountTypeMustBeOneOfAllowedValues() {
        Member member = createValidMember();
        member.setAccountType("VIP");

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("accountType")));
    }

    @Test
    void validAccountTypesShouldBeAccepted() {
        String[] validAccountTypes = {
                "Standard",
                "Student",
                "Premium",
                "Mitarbeiter"
        };

        for (String accountType : validAccountTypes) {
            Member member = createValidMember();
            member.setAccountType(accountType);

            Set<ConstraintViolation<Member>> violations = validator.validate(member);

            violations.forEach(v -> System.out.println(
                    v.getPropertyPath() + ": " + v.getMessage()
            ));

            assertTrue(violations.isEmpty(), "AccountType sollte gültig sein: " + accountType);
        }
    }
}