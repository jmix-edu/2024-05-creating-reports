package com.company.jmixpmflowbase.view.timeentry;

import com.company.jmixpmflowbase.entity.TimeEntry;

import com.company.jmixpmflowbase.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "timeEntries/:id", layout = MainView.class)
@ViewController("TimeEntry.detail")
@ViewDescriptor("time-entry-detail-view.xml")
@EditedEntityContainer("timeEntryDc")
public class TimeEntryDetailView extends StandardDetailView<TimeEntry> {
}