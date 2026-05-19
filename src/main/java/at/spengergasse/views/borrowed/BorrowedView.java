/*
    PROJEKT VON BRIAN HERTENBERGER 6ABIF 19.05.2026
 */

package at.spengergasse.views.borrowed;

import at.spengergasse.views.home.HomeView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Borrowed")
@Route("borrowed")
@Menu(order = 2, icon = LineAwesomeIconUrl.BOOK_SOLID)
public class BorrowedView extends VerticalLayout {

    public BorrowedView() {
        setSpacing(false);
        add(HomeView.getHeader());

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
