package ae.intigral.streaming.videoplayer.models;


import com.google.gson.annotations.SerializedName;

@lombok.Data
public class Lineups {

    private boolean Success;

    @SerializedName("Data")
    private Data data;
}
