package com.example.edustay.AdapterClass;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.edustay.Model.RoomModel;
import com.example.edustay.R;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private List<RoomModel> roomList;
    private Context context;
    private String currentStudentId;

    public RoomAdapter(List<RoomModel> roomList, Context context, String currentStudentId) {
        this.roomList = roomList;
        this.context = context;
        this.currentStudentId = currentStudentId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoomModel room = roomList.get(position);
        holder.tvRoomHeader.setText("Room No: " + room.getRoom_no());

        try {
            holder.tvRoomHeader.setBackgroundColor(Color.parseColor(room.getColor()));
        } catch (Exception e) {
            holder.tvRoomHeader.setBackgroundColor(Color.parseColor("#2196F3"));
        }

        List<RoomModel.Bed> beds = room.getBeds();

        // Reset visibility
        holder.tvStatus1.setVisibility(View.GONE);
        holder.tvStatus2.setVisibility(View.GONE);
        holder.tvStatus3.setVisibility(View.GONE);

        if (beds != null) {
            if (beds.size() > 0) setupBedUI(holder.tvStatus1, beds.get(0));
            if (beds.size() > 1) setupBedUI(holder.tvStatus2, beds.get(1));
            if (beds.size() > 2) setupBedUI(holder.tvStatus3, beds.get(2));
        }
    }

    private void setupBedUI(TextView tv, RoomModel.Bed bed) {
        tv.setVisibility(View.VISIBLE);
        String status = bed.getStatus();

        // Logical check for the UI display
        if (status.equalsIgnoreCase("AVAILABLE")) {
            tv.setText("Bed " + bed.getBed_no() + ": Available");
            tv.setTextColor(Color.parseColor("#4CAF50"));
        } else if (status.equalsIgnoreCase("OCCUPIED")) {
            tv.setText("Bed " + bed.getBed_no() + ": Occupied");
            tv.setTextColor(Color.parseColor("#F44336"));
        } else {
            // This displays the name (from PHP logic) if it's the user's room
            tv.setText("Bed " + bed.getBed_no() + ": " + status);
            tv.setTextColor(Color.parseColor("#1A237E"));
            tv.setTextSize(15f); // Make roommates slightly more prominent
        }
    }

    @Override
    public int getItemCount() { return roomList != null ? roomList.size() : 0; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomHeader, tvStatus1, tvStatus2, tvStatus3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomHeader = itemView.findViewById(R.id.tvRoomNumberHeader);
            tvStatus1 = itemView.findViewById(R.id.tvBed1Status);
            tvStatus2 = itemView.findViewById(R.id.tvBed2Status);
            tvStatus3 = itemView.findViewById(R.id.tvBed3Status);
        }
    }
}