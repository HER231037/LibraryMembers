/*
    PROJEKT VON BRIAN HERTENBERGER 6ABIF 19.05.2026
 */

package at.spengergasse.views.members;

import at.spengergasse.domain.LibraryMemberException;
import at.spengergasse.domain.Member;
import at.spengergasse.service.MemberService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Dial;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.aspectj.weaver.ast.Test;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.awt.*;
import java.time.LocalDate;

@PageTitle("Members")
@Route("members")
@Menu(order = 1, icon = LineAwesomeIconUrl.AMILIA)
public class MembersView extends VerticalLayout {

    private final com.vaadin.flow.component.button.Button removeAll = new Button("Remove all members");
    private final com.vaadin.flow.component.button.Button add10Members = new Button("Add 10 Members");
    private final Button addNewMember = new Button("Add new Member");

    private final Grid<Member> grid = new Grid<>(Member.class, false);
    private final MemberService memberService;


    public MembersView(@Autowired MemberService memberService) {
        this.memberService = memberService;
        setSpacing(true);

        setSizeFull();
        Image i1 = new Image("images/id.jpg", "NameIMG");
        i1.setHeight("32px");
        grid.addColumn(m -> m.getMemberID())
                .setHeader(new HorizontalLayout(i1, new Span("ID")))
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(m -> m.getName())
                .setHeader("Name")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(m -> m.getEmail())
                .setHeader("E-Mail")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(m -> m.getAccountType())
                .setHeader("Account Type")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(m -> m.getMemberSince())
                .setHeader("m -> m. Since")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(m -> m.getBorrowedBooks())
                .setHeader("Borrowed Books")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(m -> m.getMaxBorrowLimit())
                .setHeader("Borrow Limit")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(m -> m.getOpenFees())
                .setHeader("Open Fees")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addComponentColumn(members -> {
                    com.vaadin.flow.component.checkbox.Checkbox cb = new Checkbox(members.getMembershipActive());
                    cb.setReadOnly(true);
                    return cb;
                })
                .setHeader("Active")
                .setSortable(true)
                .setComparator(m -> m.getMembershipActive());
        grid.addComponentColumn(members -> new Button("Buch geborgt", e -> addBook(members.getMemberID())))
                .setHeader("Borrowed new Book")
                .setSortable(false);
        grid.addComponentColumn(members -> new Button("Buch zurückgegeben", e -> returnBook(members.getMemberID())))
                .setHeader("return Book")
                .setSortable(false);
        grid.addComponentColumn(members -> new Button("Delete Member", e -> remove1(members.getMemberID())))
                .setHeader("Action")
                .setSortable(false);
        grid.addComponentColumn(member -> new Button("Edit Member", e -> addEditMember(member)))
                .setHeader("Task")
                .setSortable(false);

        grid.setSizeFull();

        removeAll.addClickListener(e -> removeAllMembers());
        add10Members.addClickListener(e -> add10Members());
        addNewMember.addClickListener(e -> addEditMember(null));


        HorizontalLayout buttons = new HorizontalLayout(removeAll, add10Members, addNewMember);
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
        Paragraph memberMemberSince = new Paragraph("m -> m. since: " + memberSince);
        Paragraph memberBorrowedBooks = new Paragraph("Borrowed Books: " + borrowedBooks);
        Paragraph memberMaxBorrowLimit = new Paragraph("Booking Limit: " + maxBorrowLimit);
        Paragraph memberOpenFees = new Paragraph("Open Fees: " + openFees);
        Paragraph memberMembershipActive = new Paragraph(membershipActive ? "m -> m. is active" : "m -> m. is not active");

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

    private void remove1(Long memberID) {
        try {
            memberService.remove1(memberID);
            reload();
        } catch (LibraryMemberException e) {
            Notification.show(e.getMessage()); //Vaadin Klasse auswählen!
        }
    }

    private void addBook(Long memberID) {
        try {
            memberService.newBorrow(memberID);
            reload();
        } catch (LibraryMemberException e) {
            Notification.show(e.getMessage()); //Vaadin Klasse auswählen!
        }
    }

    private void returnBook(Long memberID) {
        try {
            memberService.returned(memberID);
            reload();
        } catch (LibraryMemberException e) {
            Notification.show(e.getMessage()); //Vaadin Klasse auswählen!
        }
    }

    private void addEditMember(Member existingMember) {
        Member member;
        com.vaadin.flow.component.dialog.Dialog dialog = new Dialog();
        dialog.setHeaderTitle(existingMember == null? "Neues Mitglied hinzufügen" : "Edit Member");
        if(existingMember != null) member = existingMember;
        else {
            member = new Member();
            member.setMemberId();
        }

        com.vaadin.flow.component.textfield.TextField name = new com.vaadin.flow.component.textfield.TextField("Name");
        com.vaadin.flow.component.textfield.TextField  email = new com.vaadin.flow.component.textfield.TextField ("E-Mail Adresse");
        DatePicker date = new DatePicker("Eintrittsdatum");
        ComboBox<String> type = new ComboBox<>("Account-Typ");
        type.setItems("Standard", "Student", "Premium", "Mitarbeiter");
        IntegerField limit = new IntegerField("Limit");
        IntegerField borrowed = new IntegerField("Anzahl aktuell ausgeborger Bücher");
        NumberField fees = new NumberField("offene Gebühren");
        Checkbox active = new Checkbox("Aktiv");

        BeanValidationBinder<Member> binder = new BeanValidationBinder<>(Member.class);

        binder.forField(name).bind("name");
        binder.forField(email).bind("email");
        binder.forField(date).bind("memberSince");
        binder.forField(type).bind("accountType");
        binder.forField(limit).bind("maxBorrowLimit");
        binder.forField(borrowed).bind("borrowedBooks");
        binder.forField(fees).bind("openFees");
        binder.forField(active).bind("membershipActive");

        binder.setBean(member);

        VerticalLayout formLayout = new VerticalLayout(name, email, date, type, limit, borrowed, fees, active);

        Button saveButton = new Button("Speichern", event -> {
            if(binder.validate().isOk() && member.getBorrowedBooks() <= member.getMaxBorrowLimit()) {
                memberService.addMember(member);
                reload();
                dialog.close();
                Notification.show("Mitglied wurde angelegt!");
            } else Notification.show("Fehler bei der Eingabe!");
        });

        Button cancelButton = new Button("Abbrechen", event -> dialog.close());

        dialog.add(formLayout);
        dialog.getFooter().add(saveButton, cancelButton);

        dialog.open();
    }

}
