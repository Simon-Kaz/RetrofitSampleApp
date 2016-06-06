package org.simonsays.retrofitguide.rest;

import org.simonsays.retrofitguide.model.TopGamesResp;
import org.simonsays.retrofitguide.model.TopStreamsResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by szymonkaz on 05/06/16.
 */
public interface TwitchService {

    // get stream data for specified channel
    @GET("streams/{channel}")
    Call<TopStreamsResp> channelData(
            @Path("channel") String channel
    );

    // get stream data for stream using offset
    @GET("streams?limit=1")
    Call<TopStreamsResp> specificStreamResp(
            @Query("offset") int offset
    );

    // get top games (max viewers DESC)
    @GET("games/top")
    Call<TopGamesResp> topGamesResp();
}
