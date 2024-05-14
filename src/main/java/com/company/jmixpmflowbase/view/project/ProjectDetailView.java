package com.company.jmixpmflowbase.view.project;

import com.company.jmixpmflowbase.entity.Project;

import com.company.jmixpmflowbase.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "projects/:id", layout = MainView.class)
@ViewController("Project.detail")
@ViewDescriptor("project-detail-view.xml")
@EditedEntityContainer("projectDc")
public class ProjectDetailView extends StandardDetailView<Project> {
}