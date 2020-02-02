package com.brandongogetap.stickyheaders.demo;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;

import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;
import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder> implements StickyHeaderHandler {

    private final List<Item> data = new ArrayList<>();

    void setData(List<Item> items) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SimpleDiffCallback(data, items));
        data.clear();
        data.addAll(items);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        final BaseViewHolder viewHolder;
        if (viewType == 0) {
            viewHolder = new MyViewHolder(view);
        } else {
            viewHolder = new MyOtherViewHolder(view);
        }
        view.setOnClickListener(v -> {
            // This is unsafe to do in OnClickListeners attached to sticky headers. The adapter
            // position of the holder will be out of sync if any items have been added/removed.
            // If a click listener needs to be set on a sticky header, it is recommended to identify the header
            // based on its backing model, rather than position in the data set.
            int position = viewHolder.getAdapterPosition();
            if (position != NO_POSITION) {
                List<Item> newData = new ArrayList<>(data);
                newData.remove(position);
                setData(newData);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Item item = data.get(position);
        holder.titleTextView.setText(item.title);
        holder.messageTextView.setText(item.message);
        if (position != 0 && position % 12 == 0) {
            holder.itemView.setPadding(0, 100, 0, 100);
        } else {
            holder.itemView.setPadding(0, 0, 0, 0);
        }
        if (item instanceof StickyHeader) {
            holder.itemView.setBackgroundColor(Color.CYAN);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position != 0 && position % 16 == 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public List<?> getAdapterData() {
        return data;
    }

    private static final class MyViewHolder extends BaseViewHolder {

        MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static final class MyOtherViewHolder extends BaseViewHolder {

        MyOtherViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView messageTextView;

        BaseViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_title);
            messageTextView = itemView.findViewById(R.id.tv_message);
        }
    }
}
