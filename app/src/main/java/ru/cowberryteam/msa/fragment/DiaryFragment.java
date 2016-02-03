package ru.cowberryteam.msa.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ru.cowberryteam.msa.R;

public class DiaryFragment extends Fragment {
    public DiaryFragment() {
        // Required empty public constructor
    }

    public  static  DiaryFragment newInstance(int day){
        DiaryFragment fragment = new DiaryFragment();

        Bundle args = new Bundle();
        args.putInt("day", day);
        fragment.setArguments(args);

        return  fragment;
    }

    int day = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            day = savedInstanceState.getInt("day");
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = LessonTaskFragment.newInstance(1,"Тест","Задание 1","42");
        transaction.add(R.id.diaryLinearLayout, fragment);
        transaction.commit();

        return  view;
    }
}
