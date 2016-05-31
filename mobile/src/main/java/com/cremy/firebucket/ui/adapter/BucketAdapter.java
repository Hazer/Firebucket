package com.cremy.firebucket.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cremy.firebucket.R;
import com.cremy.firebucket.databinding.ItemBucketHeaderBinding;
import com.cremy.firebucket.databinding.ItemBucketTaskBinding;
import com.cremy.firebucket.ui.viewModel.BucketHeaderViewModel;
import com.cremy.firebucket.ui.viewModel.BucketTaskViewModel;
import com.cremy.shared.data.model.Task;

import java.util.List;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.BindingHolder> {

    //region ViewTypes
    private final static int TYPE_DEFAULT = 0;
    private final static int TYPE_HEADER = 1;
    //endregion

    private Context context;
    private List<Task> model;


    //region CONSTRUCTOR and SET
    /**
     * Allows to create the TasksAdapter
     * @param _context
     * @param _model
     */
    public BucketAdapter(Context _context,
                         List<Task> _model) {
        this.context = _context;
        this.model = _model;
    }


    /**
     * Allows to set new items
     * @param _model
     */
    public void setItems(List<Task> _model) {
        this.model = _model;
        notifyDataSetChanged();
    }
    //endregion

    //region ADD
    /**
     * Allows to add MULTIPLE items
     * @param _model
     */
    public void addItems(List<Task> _model) {
        this.model.addAll(_model);
        notifyDataSetChanged();
    }
    //endregion

    //region DELETE
    /**
     * Allows to delete an item with a given item index
     * @param _index
     */
    public void deleteItem(final int _index) {
        this.model.remove(_index);
        notifyItemRemoved(_index);
    }
    //endregion

    //region Getters

    /**
     * Allows to get the item with a given adapter position
     * @param _position
     * @return
     */
    public Task getItem(int _position) {
        return this.model.get(_position);
    }
    //endregion

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            ItemBucketHeaderBinding itemBucketHeaderBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_bucket_header,
                    parent,
                    false);
            return new BindingHolder(itemBucketHeaderBinding);
        }

        ItemBucketTaskBinding itemBucketTaskBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_bucket_task,
                parent,
                false);
        return new BindingHolder(itemBucketTaskBinding);

    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final int viewType = getItemViewType(position);

        if (viewType == TYPE_HEADER) {
            ItemBucketHeaderBinding itemBucketHeaderBinding = (ItemBucketHeaderBinding) holder.binding;
            itemBucketHeaderBinding.setViewModel(new BucketHeaderViewModel(this.context, this.model.get(position), position));
        }
        else {
            ItemBucketTaskBinding itemBucketTaskBinding = (ItemBucketTaskBinding) holder.binding;
            itemBucketTaskBinding.setViewModel(new BucketTaskViewModel(this.context, this.model.get(position), position));
        }
    }


    @Override
    public int getItemCount() {
        return this.model.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (this.model.get(position).getDisplayedDeadline()==null) {
            return TYPE_HEADER;
        }
        return TYPE_DEFAULT; // Default value
    }



    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(ItemBucketHeaderBinding binding) {
            super(binding.item);
            this.binding = binding;
        }
        public BindingHolder(ItemBucketTaskBinding binding) {
            super(binding.item);
            this.binding = binding;
        }
    }
}