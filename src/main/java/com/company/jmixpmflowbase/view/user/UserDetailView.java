package com.company.jmixpmflowbase.view.user;

import com.company.jmixpmflowbase.entity.User;
import com.company.jmixpmflowbase.view.main.MainView;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.EntityStates;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.common.util.URLEncodeUtils;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Route(value = "users/:id", layout = MainView.class)
@ViewController("User.detail")
@ViewDescriptor("user-detail-view.xml")
@EditedEntityContainer("userDc")
public class UserDetailView extends StandardDetailView<User> {

    @ViewComponent
    private TypedTextField<String> usernameField;
    @ViewComponent
    private PasswordField passwordField;
    @ViewComponent
    private PasswordField confirmPasswordField;
    @ViewComponent
    private ComboBox<String> timeZoneField;
    @ViewComponent
    private Div documentFrame;
    //    @ViewComponent
//    private IFrame displayPdf;
    @Autowired
    private EntityStates entityStates;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationContext applicationContext;

    private static final String REST_PATH = "/rest/files?fileRef=";
    @Autowired
    private FileStorage fileStorage;

    @Subscribe
    public void onInit(final InitEvent event) {
        timeZoneField.setItems(List.of(TimeZone.getAvailableIDs()));
    }

    @Subscribe
    public void onInitEntity(final InitEntityEvent<User> event) {
        usernameField.setReadOnly(false);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            usernameField.focus();
        }
    }

    @Subscribe
    public void onValidation(final ValidationEvent event) {
        if (entityStates.isNew(getEditedEntity())
                && !Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
            event.getErrors().add(messageBundle.getMessage("passwordsDoNotMatch"));
        }
    }

    @Subscribe
    protected void onBeforeSave(final BeforeSaveEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));
        }
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        addFileToPdfViewer();
        //updateIFrame();
        
    }

    private void addFileToPdfViewer() {
        User editedEntity = getEditedEntity();
        FileRef document = editedEntity.getDocument();

        InputStream inputStream = fileStorage.openStream(document);

        PdfViewer pdfViewer = new PdfViewer();
        StreamResource streamResource = new StreamResource(document.getFileName(), () -> inputStream);
        pdfViewer.setSrc(streamResource);
        pdfViewer.openThumbnailsView();
        pdfViewer.setHeight("100%");

        documentFrame.add(pdfViewer);

    }

    private void updateIFrame() {
        User editedEntity = getEditedEntity();
        FileRef document = editedEntity.getDocument();

        if (document!= null) {
            Page page = UI.getCurrent().getPage();

            page.fetchCurrentURL(url -> {
                String contextPath = ((WebApplicationContext)applicationContext)
                        .getServletContext().getContextPath();
                String documentSrc = url.getProtocol() + "://" + url.getAuthority() + contextPath + REST_PATH
                        + URLEncodeUtils.encodeUtf8(document.toString());

                //displayPdf.setSrc(documentSrc);
            });
        }
    }

}