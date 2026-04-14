package com.example.edustay.Model;

import java.util.List;

public class RoomModel {
        private String room_no;
        private String color;
        private List<Bed> beds;

        public static class Bed {
            private String bed_no;
            private String status;

            public String getBed_no() { return bed_no; }
            public String getStatus() { return status; }
        }

        public String getRoom_no() { return room_no; }
        public String getColor() { return color; }
        public List<Bed> getBeds() { return beds; }


}

