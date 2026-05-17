package at.spengergasse.views.members;

import at.spengergasse.views.home.HomeView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.validation.constraints.Email;
import org.apache.catalina.webresources.CachedResource;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.LocalDate;
import java.util.Date;

@PageTitle("Member")
@Route("members")
@Menu(order = 1, icon = LineAwesomeIconUrl.AMILIA)
public class MembersView extends VerticalLayout {

    public MembersView() {
        setSpacing(false);
        add(HomeView.getHeader());

        H2 title = new H2("Member:");

        VerticalLayout member1 = createCard(1L,"Max Mustermann","max.mustermann@example.com",LocalDate.of(2024, 9, 12),3,5,0.0,true);
        VerticalLayout member2 = createCard(2L,"Anna Müller","anna.mueller@example.com",LocalDate.of(2023, 3, 25),5,5,2.50,true);
        VerticalLayout member3 = createCard(3L,"Lukas Bauer","lukas.bauer@example.com",LocalDate.of(2025, 1, 10),0,4,7.90,false);

        FlexLayout cardsLayout = new FlexLayout(member1,member2,member3);
        cardsLayout.setWidthFull();
        cardsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        cardsLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        add(cardsLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private VerticalLayout createCard(Long memberID,
                                      String name,
                                      String email,
                                      LocalDate memberSince,
                                      Integer borrowedBooks,
                                      Integer maxBorrowLimit,
                                      Double openFees,
                                      Boolean membershipActive){

        H2 memberId = new H2(String.valueOf("ID: " + memberID + " - " + name));
        Paragraph memberEmail = new Paragraph("E-Mail: " + email);
        Paragraph memberMemberSince = new Paragraph("Member since: " + memberSince);
        Paragraph memberBorrowedBooks = new Paragraph("Borrowed Books: " + borrowedBooks);
        Paragraph memberMaxBorrowLimit = new Paragraph("Booking Limit: " + maxBorrowLimit);
        Paragraph memberOpenFees = new Paragraph("Open Fees: " + openFees);
        Paragraph memberMembershipActive = new Paragraph(membershipActive?"Member is active":"Member is not active");

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

}
