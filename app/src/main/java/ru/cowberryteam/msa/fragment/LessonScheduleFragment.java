package ru.cowberryteam.msa.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.cowberryteam.msa.R;

public class LessonScheduleFragment extends Fragment {
    public LessonScheduleFragment() {
        // Required empty public constructor
    }

    public  static LessonScheduleFragment newInstance(int number, String subject, String time){
        LessonScheduleFragment fragment = new LessonScheduleFragment();

        Bundle args = new Bundle();
        args.putInt("number", number);
        args.putString("subject", subject);
        args.putString("time", time);
        fragment.setArguments(args);

        return fragment;
    }

    private int number = -1;
    private String subject = "";
    private String time = "";

    public int getNumber() {
        return number;
    }
    public String getSubject() {
        return subject;
    }
    public String getTime() {
        return time;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            number = savedInstanceState.getInt("number");
            subject = savedInstanceState.getString("subject");
            time = savedInstanceState.getString("time");
        }catch (Exception e){ e.printStackTrace(); }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_lesson_task, container, false);
        ((TextView)view.findViewById(R.id.textSubjectNum1)).setText(getNumber()+".");
        ((TextView)view.findViewById(R.id.textSubject1)).setText(subject);
        ((TextView)view.findViewById(R.id.textTimetable1)).setText(time);
        return  view;
    }
}
