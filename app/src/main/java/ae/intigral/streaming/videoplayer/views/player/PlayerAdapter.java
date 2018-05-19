package ae.intigral.streaming.videoplayer.views.player;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import ae.intigral.streaming.videoplayer.R;
import ae.intigral.streaming.videoplayer.databinding.RowItemBinding;
import ae.intigral.streaming.videoplayer.models.Player;
import ae.intigral.streaming.videoplayer.utils.DataBoundListAdapter;
import ae.intigral.streaming.videoplayer.utils.DataBoundViewHolder;

/**
 * adapter to handle player recycler views
 */
public class PlayerAdapter extends DataBoundListAdapter<Player, RowItemBinding> {


    @Override
    protected RowItemBinding createBinding(ViewGroup parent) {
        RowItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_item,
                parent,
                false);
        return binding;
    }

    @Override
    protected void bind(DataBoundViewHolder<RowItemBinding> holder, RowItemBinding binding, Player item) {
        binding.setPlayer(item);
    }

    @Override
    protected boolean areItemsTheSame(Player oldItem, Player newItem) {
        return oldItem.equals(newItem);

    }

    @Override
    protected boolean areContentsTheSame(Player oldItem, Player newItem) {
        return oldItem.equals(newItem);

    }
}
