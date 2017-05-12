package org.simonsays.retrofitguide.model;

import java.util.List;

public class TopStreamsResponse {

    private List<Stream> streams;
    private int _total;

    public List<Stream> getStreams() {
        return streams;
    }

    public Stream getStream(int i) {
        return streams.get(i);
    }

}
