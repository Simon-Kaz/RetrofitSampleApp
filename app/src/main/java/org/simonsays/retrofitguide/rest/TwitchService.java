package org.simonsays.retrofitguide.rest;

import org.simonsays.retrofitguide.model.TopGamesResponse;
import org.simonsays.retrofitguide.model.TopStreamsResponse;

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
    Call<TopStreamsResponse> channelData(
            @Path("channel") String channel
    );

    // get stream data using offset
    @GET("streams?limit=1")
    Call<TopStreamsResponse> singleStreamResponse(
            @Query("offset") int offset
    );

    // get top games (max viewers DESC)
    @GET("games/top")
    Call<TopGamesResponse> topGamesResponse();
}
