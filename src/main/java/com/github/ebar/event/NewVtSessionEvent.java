package com.github.ebar.event;


import com.github.ebar.barrier.EventTemplate;

import java.util.Map;

/**
 * Daneel Yaitskov
 */
public class NewVtSessionEvent extends EventTemplate {
    public final Map userProfile;

    public NewVtSessionEvent(Map userProfile) {
        this.userProfile = userProfile;
    }
}
