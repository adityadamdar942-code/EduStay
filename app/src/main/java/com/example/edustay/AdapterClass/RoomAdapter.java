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

        public RoomAdapter(List<RoomModel> roomList, Context context) {
            this.roomList = roomList;
            this.context = context;
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
            holder.tvRoomHeader.setBackgroundColor(Color.parseColor(room.getColor()));

            List<RoomModel.Bed> beds = room.getBeds();

            // तुमच्या कार्डमध्ये ३ बेड्स आहेत, त्यांना डेटा सेट करणे
            if (beds != null) {
                if (beds.size() > 0) holder.tvStatus1.setText(beds.get(0).getStatus());
                if (beds.size() > 1) holder.tvStatus2.setText(beds.get(1).getStatus());
                if (beds.size() > 2) holder.tvStatus3.setText(beds.get(2).getStatus());

                // स्टेटस नुसार रंग बदलणे (हिरवा किंवा राखाडी)
                setStatusStyle(holder.tvStatus1);
                setStatusStyle(holder.tvStatus2);
                setStatusStyle(holder.tvStatus3);
            }
        }

        private void setStatusStyle(TextView tv) {
            if (tv.getText().toString().equalsIgnoreCase("AVAILABLE")) {
                tv.setTextColor(Color.parseColor("#4CAF50"));
            } else {
                tv.setTextColor(Color.parseColor("#757575"));
            }
        }

        @Override
        public int getItemCount() { return roomList.size(); }

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

