 package com.example.edustay.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RoomModel {

    @SerializedName("room_no")
    private String roomNo;

    @SerializedName("color")
    private String color;

    @SerializedName("beds")
    private List<Bed> beds;

    // Getters
    public String getRoom_no() { return roomNo; }
    public String getColor() { return color; }
    public List<Bed> getBeds() { return beds; }

    public static class Bed {
        @SerializedName("bed_no")
        private String bedNo;

        @SerializedName("status")
        private String status;

        // Getters
        public String getBed_no() { return bedNo; }
        public String getStatus() { return status; }
    }
}