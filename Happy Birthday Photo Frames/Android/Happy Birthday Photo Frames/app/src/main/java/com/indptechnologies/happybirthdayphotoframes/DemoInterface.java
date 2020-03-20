package com.indptechnologies.happybirthdayphotoframes;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by SINarayanReddy on 01-11-2017.
 */

public interface DemoInterface {
    @GET("/showads/services/lists")
    Call<DemoData> getMyJSON();
}
