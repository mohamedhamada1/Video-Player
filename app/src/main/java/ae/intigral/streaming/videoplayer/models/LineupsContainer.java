package ae.intigral.streaming.videoplayer.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LineupsContainer {

    @SerializedName("Lineups")
    private Lineups lineups;
}
