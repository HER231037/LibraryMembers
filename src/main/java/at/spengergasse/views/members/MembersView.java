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
import org.apache.catalina.webresources.CachedResource;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.Date;

@PageTitle("Members")
@Route("members")
@Menu(order = 1, icon = LineAwesomeIconUrl.AMILIA)
public class MembersView extends VerticalLayout {

    public MembersView() {
        setSpacing(false);
        add(HomeView.getHeader());

        H2 title = new H2("Members:");

        VerticalLayout member1 = createCard("Brian Hertenberger", 2505971);
        VerticalLayout member2 = createCard("Lukas Schmidt", 3112011);
        VerticalLayout member3 = createCard("Daniel Schumpeter", 1408021);

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

    private VerticalLayout createCard(String name, int id){
        H2 membername = new H2(name);
        Paragraph memberid = new Paragraph("Member-ID: " + id);

        VerticalLayout card = new VerticalLayout(membername, memberid);
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
