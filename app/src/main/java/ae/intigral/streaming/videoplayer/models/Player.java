package ae.intigral.streaming.videoplayer.views.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Player {
    @SerializedName("Order")
    private int order;
    @SerializedName("StartInField")
    private boolean startInField;
    @SerializedName("IsCaptain")
    private boolean isCaptain;

    @SerializedName("Role")
    private String role;
    @SerializedName("jerseyNumber")
    private String JerseyNumber;
    @SerializedName("Id")
    private long id;
    @SerializedName("Name")
    private String name;
    @SerializedName("XCoordinate")
    private int xCoordinate;
    @SerializedName("YCoordinate")
    private int yCoordinate;


}
