package com.company.jmixpmflowbase.view.user;

import com.company.jmixpmflowbase.app.ReportServiceBean;
import com.company.jmixpmflowbase.entity.User;
import com.company.jmixpmflowbase.view.main.MainView;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.reports.entity.ReportOutputType;
import io.jmix.reportsflowui.runner.ParametersDialogShowMode;
import io.jmix.reportsflowui.runner.UiReportRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {


    @ViewComponent
    private DataGrid<User> usersDataGrid;
    @Autowired
    private UiReportRunner uiReportRunner;
    @Autowired
    private ReportServiceBean reportServiceBean;
    @Autowired
    private Notifications notifications;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Downloader downloader;

    @Subscribe("usersDataGrid.runReportAction")
    public void onUsersDataGridRunReportAction(final ActionPerformedEvent event) {
        User user = usersDataGrid.getSingleSelectedItem();

        if (user == null) {
            return;
        }

        uiReportRunner.byReportCode("single-user-report")
                .withParams(Map.of("entity", user))
                .withOutputType(ReportOutputType.PDF)
                .withOutputNamePattern("single-user-report.pdf")
                .withParametersDialogShowMode(ParametersDialogShowMode.NO)
                .inBackground(this)
                .runAndShow();
    }

    @Subscribe("usersDataGrid.createDocumentAction")
    public void onUsersDataGridCreateDocumentAction(final ActionPerformedEvent event) {
        User user = usersDataGrid.getSingleSelectedItem();

        if (user == null) {
            return;
        }

        reportServiceBean.generateSingleUserReport(user);

        notifications.create("Document created!")
                .withType(Notifications.Type.SUCCESS)
                .show();
    }

    @Supply(to = "usersDataGrid.document", subject = "renderer")
    private Renderer<User> usersDataGridDocumentRenderer() {
        return new ComponentRenderer<JmixButton, User>(user -> {
            JmixButton button = uiComponents.create(JmixButton.class);
            button.setText(user.getDocument() == null ? "" : user.getDocument().getFileName());
            button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            button.addClickListener(clickEvent -> {
                downloader.download(user.getDocument());
            });
            return button;
        });
    }

}