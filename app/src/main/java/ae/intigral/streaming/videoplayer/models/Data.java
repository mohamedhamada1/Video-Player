package ae.intigral.streaming.videoplayer.views.models;

import com.google.gson.annotations.SerializedName;

@lombok.Data
public class Data {

    @SerializedName("HomeTeam")
    private Team homeTeam;
    @SerializedName("AwayTeam")
    private Team awayTeam;
}
