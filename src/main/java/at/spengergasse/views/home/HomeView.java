/*
    PROJEKT VON BRIAN HERTENBERGER 6ABIF 19.05.2026
 */

package at.spengergasse.views.home;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Home")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.HOME_SOLID)
public class HomeView extends VerticalLayout {

    public HomeView() {
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getHeader());

        Paragraph description = new Paragraph(
                "LibraryMembers ist eine moderne Online-Bibliothek zur einfachen Verwaltung von Bibliotheksmitgliedern und Büchern. " +
                        "Die Plattform ermöglicht es, Mitgliederdaten übersichtlich zu erfassen, zu verwalten und schnell wiederzufinden."
        );
        description.setWidth("auto");

        description.setWidth("500px");
        description.getStyle()
                .set("font-size", "22px")
                .set("line-height", "1.6")
                .set("text-align", "left");

        H3 name = new H3("LibraryMembers");
        H3 street = new H3("74th Onlinestreet");
        H3 city = new H3("172.0.0.1, Somewhere");
        HorizontalLayout address = new HorizontalLayout(name, street, city);
        address.getStyle().set("gap", "40px");


        add(description, address);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    public static Component getHeader() {
        H1 companyName = new H1("Willkommen bei LibraryMembers");
        companyName.getStyle()
                .set("font-family", "cursive")
                .set("font-size", "6rem")
                .set("margin", "0");

        H2 subname = new H2("Online Bibliothek");
        subname.getStyle()
                .set("margin", "0")
                .set("color", "gray");

        Image img = new Image("images/logo.png", "LibraryMember Logo");
        img.setWidth("220px");

        VerticalLayout headerLayout = new VerticalLayout(companyName, subname, img);
        headerLayout.setSpacing(false);
        headerLayout.setPadding(false);
        headerLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        return headerLayout;
    }

}