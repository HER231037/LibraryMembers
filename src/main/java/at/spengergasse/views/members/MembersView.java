package at.spengergasse.views.members;

import at.spengergasse.domain.LibraryMemberException;
import at.spengergasse.domain.Member;
import at.spengergasse.service.MemberService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.awt.*;
import java.time.LocalDate;

@PageTitle("Member")
@Route("members")
@Menu(order = 1, icon = LineAwesomeIconUrl.AMILIA)
public class MembersView extends VerticalLayout {

    private final com.vaadin.flow.component.button.Button removeAll = new Button("Remove all members");
    private final com.vaadin.flow.component.button.Button add10Members = new Button("Add 10 Members");
    private final com.vaadin.flow.component.button.Button addWrongMember = new Button("Add Wrong Member");

    private final Grid<Member> grid = new Grid<>(Member.class, true);
    private final MemberService memberService;


    public MembersView(@Autowired MemberService memberService) {
        this.memberService = memberService;
        setSpacing(true);

        setSizeFull();
        grid.setSizeFull();

        removeAll.addClickListener(e -> removeAllMembers());
        add10Members.addClickListener(e -> add10Members());
        addWrongMember.addClickListener(e -> addWrongMember());

        HorizontalLayout buttons = new HorizontalLayout(removeAll, add10Members, addWrongMember);
        buttons.setSpacing(true);
        add(buttons);

        add(grid);
        reload();
    }

    private void reload() {
        grid.setItems(memberService.findAll());
    }

    private VerticalLayout createCard(Long memberID, String name, String email, LocalDate memberSince, Integer borrowedBooks, Integer maxBorrowLimit, Double openFees, Boolean membershipActive) {

        H2 memberId = new H2(String.valueOf("ID: " + memberID + " - " + name));
        Paragraph memberEmail = new Paragraph("E-Mail: " + email);
        Paragraph memberMemberSince = new Paragraph("Member since: " + memberSince);
        Paragraph memberBorrowedBooks = new Paragraph("Borrowed Books: " + borrowedBooks);
        Paragraph memberMaxBorrowLimit = new Paragraph("Booking Limit: " + maxBorrowLimit);
        Paragraph memberOpenFees = new Paragraph("Open Fees: " + openFees);
        Paragraph memberMembershipActive = new Paragraph(membershipActive ? "Member is active" : "Member is not active");

        VerticalLayout card = new VerticalLayout(memberId, memberEmail, memberMemberSince, memberBorrowedBooks, memberMaxBorrowLimit, memberOpenFees, memberMembershipActive);
        card.setWidth("350px");
        card.setPadding(true);
        card.setSpacing(false);

        card.getStyle()
                .set("border", "1px solid lightgray")
                .set("border-radius", "10px")
                .set("margin", "10px");

        return card;
    }

    private void removeAllMembers() {
        try {
            memberService.removeAllAccounts();
            removeAll.setEnabled(false);
            reload();
        } catch (LibraryMemberException e) {
            Notification.show(e.getMessage()); //Vaadin Klasse auswählen!
        }
    }

    private void add10Members() {
        try {
            memberService.fillTestData(10);
            removeAll.setEnabled(true);
            reload();
        } catch (LibraryMemberException e) {
            Notification.show(e.getMessage()); //Vaadin Klasse auswählen!
        }
    }

    private void addWrongMember() {
        try {
            Member m = new Member("  ", "  ", LocalDate.now(), -1, -1, -1d, "False", false);
            memberService.addMember(m);
            reload();
        } catch (LibraryMemberException e) {
            Notification.show(e.getMessage()); //Vaadin Klasse auswählen!
        }
    }

}
