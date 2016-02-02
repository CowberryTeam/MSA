package ru.cowberryteam.msa.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.cowberryteam.msa.R;

public class LessonTaskFragment extends Fragment {
    public LessonTaskFragment() {
        // Required empty public constructor
    }

    public  static LessonTaskFragment newInstance(int number, String subject, String task, String room){
        LessonTaskFragment fragment = new LessonTaskFragment();

        Bundle args = new Bundle();
        args.putInt("number", number);
        args.putString("subject", subject);
        args.putString("task", task);
        args.putString("room", room);
        fragment.setArguments(args);

        return fragment;
    }

    private int number = -1;
    private String subject = "";
    private String task = "";
    private String room = "";

    public int getNumber() {
        return number;
    }
    public String getSubject() {
        return subject;
    }
    public String getTask() {
        return task;
    }
    public String getRoom() { return room; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        try {
            number = args.getInt("number");
            subject = args.getString("subject");
            task = args.getString("task");
            room = args.getString("room");
        }catch (Exception e){ e.printStackTrace(); }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_lesson_task, container, false);
        //((TextView)view.findViewById(R.id.textSubjectNum1)).setText(getNumber()+"."); // Optional
        ((TextView)view.findViewById(R.id.textSubject1)).setText(subject);
        ((TextView)view.findViewById(R.id.textHomeWork1)).setText(task);
        //((TextView)view.findViewById(R.id.textSubjectRoom1)).setText(room);
        return  view;
    }
}
