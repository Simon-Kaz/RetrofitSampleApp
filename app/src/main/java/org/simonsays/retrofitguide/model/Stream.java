package org.simonsays.retrofitguide.model;

/**
 * Stream contains general info about the streamer and the type of broadcast e.g. playlist, live.
 */
public class Stream {

    public String game;
    public Integer viewers;
    public Integer video_height;
    public Float average_fps;
    public Channel channel;

    public Channel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return game + " broadcasted for " + viewers;
    }

    // Channel contains detailed information about the streamer
    public class Channel {

        public Boolean mature;
        public String status;
        public String broadcaster_language;
        public String display_name;
        public String game;
        public String language;
        public Integer id;
        public String name;
        public String created_at;
        public String updated_at;
        public String logo;
        public String video_banner;
        public String profile_banner;
        public String profile_banner_background_color;
        public Boolean partner;
        public String url;
        public Integer views;
        public Integer followers;

        @Override
        public String toString() {
            return display_name + " broadcasting " + game;
        }
    }
}