package com.gateway.lead_hunter.async;

import com.gateway.lead_hunter.objects.pojo.Show;

import java.util.List;

public interface AsyncResponse {
    void processListOfShows(List<Show> output);
}
