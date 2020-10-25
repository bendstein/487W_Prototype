package com.hotel.load;

import com.hotel.model.repo.MemberDatabase;
import com.hotel.model.repo.item.MenuItemDatabase;
import com.hotel.model.repo.request.*;
import com.hotel.model.repo.staff.StaffDatabase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Called when the application is initialized or refreshed.
 */
@Component
public class DatabaseLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final MemberDatabase database;

    private final StaffDatabase staffRepo;

    @Qualifier("Request")
    private final RequestDatabase requestRepo;

    @Qualifier("Maintenance")
    private final MaintenanceRequestDatabase maintenanceRepo;

    @Qualifier("General")
    private final GeneralRequestDatabase generalRepo;

    @Qualifier("Wakeup")
    private final WakeUpRequestDatabase wakeupRepo;

    private final MenuItemDatabase itemRepo;

    public DatabaseLoader(MemberDatabase database, StaffDatabase staffRepo, RequestDatabase requestRepo,
                          MaintenanceRequestDatabase maintenanceRepo, GeneralRequestDatabase generalRepo,
                          WakeUpRequestDatabase wakeupRepo, MenuItemDatabase itemRepo) {
        this.database = database;
        this.staffRepo = staffRepo;
        this.requestRepo = requestRepo;
        this.maintenanceRepo = maintenanceRepo;
        this.generalRepo = generalRepo;
        this.wakeupRepo = wakeupRepo;
        this.itemRepo = itemRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}