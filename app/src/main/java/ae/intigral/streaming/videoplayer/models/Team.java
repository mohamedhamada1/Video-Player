package ae.intigral.streaming.videoplayer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class Team {
    @SerializedName("Players")
    private List<Player> players;
}
