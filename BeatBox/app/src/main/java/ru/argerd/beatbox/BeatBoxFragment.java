package ru.argerd.beatbox;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.argerd.beatbox.databinding.FragmentBeatBoxBinding;
import ru.argerd.beatbox.databinding.ListItemSoundBinding;

public class BeatBoxFragment extends Fragment {
    private BeatBox beatBox;

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        beatBox = new BeatBox(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beatBox.release();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_beat_box, container, false);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.recyclerView.setAdapter(new Adapter(beatBox.getSounds()));

        return binding.getRoot();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        ListItemSoundBinding binding;

        private ViewHolder(ListItemSoundBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setSoundViewModel(new SoundViewModel(beatBox));
        }

        public void bind(Sound sound) {
            binding.getSoundViewModel().setSound(sound);
            binding.executePendingBindings();
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Sound> soundList;

        public Adapter(List<Sound> soundList) {
            this.soundList = soundList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            ListItemSoundBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.list_item_sound, viewGroup, false);

            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Sound sound = soundList.get(i);
            viewHolder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return soundList.size();
        }
    }
}
