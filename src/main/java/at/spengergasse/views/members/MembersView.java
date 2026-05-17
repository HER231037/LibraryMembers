package at.spengergasse.views.members;

import at.spengergasse.domain.Member;
import at.spengergasse.service.MemberService;
import at.spengergasse.views.home.HomeView;
import com.vaadin.flow.component.grid.Grid;
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
import org.hibernate.event.spi.PreLoadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.LocalDate;
import java.util.Date;

@PageTitle("Member")
@Route("members")
@Menu(order = 1, icon = LineAwesomeIconUrl.AMILIA)
public class MembersView extends VerticalLayout {

    private final Grid<Member> grid = new Grid<>(Member.class, true);
    private final MemberService memberService;

    public MembersView(@Autowired MemberService memberService) {
        this.memberService = memberService;
        setSpacing(true);

        setSizeFull();
        grid.setSizeFull();
        add(grid);
        reload();
    }

    private void reload(){
        grid.setItems(memberService.findAll());
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
