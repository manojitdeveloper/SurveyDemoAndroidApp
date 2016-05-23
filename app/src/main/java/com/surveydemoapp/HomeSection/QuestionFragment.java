package com.surveydemoapp.HomeSection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.surveydemoapp.Global.Utils;
import com.surveydemoapp.R;
import com.surveydemoapp.database.DatabaseController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class QuestionFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView question, mid_header_text;
    LinearLayout options_layout;
    Context context;
    String answers;
    String[] ary;
    Button prev, next, submit;
    MainActivity mainActivity;
    EditText text_answer;
    ArrayList<String> selected_check_val = new ArrayList<String>();
    String question_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_question, container, false);
        mainActivity = (MainActivity) getActivity();

        question = (TextView) view.findViewById(R.id.question);
        options_layout = (LinearLayout) view.findViewById(R.id.options_layout);
        text_answer = (EditText) view.findViewById(R.id.text_answer);
        mid_header_text = (TextView) view.findViewById(R.id.mid_header_text);

        mid_header_text.setText("Survey Questions");
        prev = (Button) view.findViewById(R.id.prev);
        next = (Button) view.findViewById(R.id.next);
        submit = (Button) view.findViewById(R.id.submit);

        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        submit.setOnClickListener(this);

        if (getArguments() != null) {
            if (getArguments().getInt("position") == 0) {
                prev.setVisibility(View.GONE);
            } else {
                prev.setVisibility(View.VISIBLE);
            }
            if (getArguments().getString("last_index").equalsIgnoreCase("yes")) {
                next.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            } else {
                next.setVisibility(View.VISIBLE);
            }
        }


        context = getActivity();

        if (getArguments() != null) {

            try {

                JSONObject questionJson = new JSONObject(getArguments().getString("questionObject"));
                question.setText(questionJson.getString("question"));
                question_id = questionJson.getString("id");
                answers = DatabaseController.getAnswer(context, questionJson.getString("id"));

                ary = answers.split(",");
                Utils.SOP("sdfg" + questionJson);
                if (questionJson.getString("answer_type").equalsIgnoreCase("checkbox")) {
                    addCheckbox(ary.length, questionJson.getString("id"));
                    text_answer.setVisibility(View.GONE);
                } else if (questionJson.getString("answer_type").equalsIgnoreCase("dropdown")) {
                    addSpinner(questionJson.getString("id"));
                    text_answer.setVisibility(View.GONE);
                } else if (questionJson.getString("answer_type").equalsIgnoreCase("radio")) {
                    addRadioButtons(ary.length, questionJson.getString("id"));
                    text_answer.setVisibility(View.GONE);
                } else if (questionJson.getString("answer_type").equalsIgnoreCase("others")) {
                    text_answer.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        text_answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (String.valueOf(text_answer.getText()).trim().length() > 0) {

                    Utils.savePreferenceValues(context, question_id, String.valueOf(text_answer.getText()).trim());

                }


            }
        });

        return view;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.next:

                mainActivity.next_button(getArguments().getInt("position"));

                break;

            case R.id.prev:

                mainActivity.previous_button(getArguments().getInt("position"));

                break;

            case R.id.submit:

                mainActivity.submit();

                break;

            default:
                break;
        }

    }


    public void addRadioButtons(int number, final String id) {
        RadioGroup ll = new RadioGroup(context);
        for (int row = 0; row < 1; row++) {

            ll.setOrientation(LinearLayout.VERTICAL);

            for (int i = 1; i <= number; i++) {
                final RadioButton rdbtn = new RadioButton(context);
                rdbtn.setId((row * 2) + i);
                rdbtn.setText(ary[i - 1]);
                ll.addView(rdbtn);


                rdbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Utils.SOP("testttt" + rdbtn.getText());
                        Utils.savePreferenceValues(context, id, rdbtn.getText().toString());
                    }
                });
            }

        }
        ((ViewGroup) view.findViewById(R.id.options_layout)).addView(ll);

    }

    public void addCheckbox(int num, final String id) {

        for (int i = 0; i < num; i++) {
            final CheckBox ch = new CheckBox(context);
            ch.setId((i * 2) + i);
            ch.setText(ary[i]);

            ((ViewGroup) view.findViewById(R.id.options_layout)).addView(ch);

            ch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.SOP("check" + ch.getText());
                    if (ch.isChecked()) {
                        selected_check_val.add(ch.getText().toString());
                    } else {
                        selected_check_val.remove(ch.getText().toString());
                    }

                    String res = TextUtils.join(",", selected_check_val);
                    Utils.SOP("sssss" + res);
                    Utils.savePreferenceValues(context, id, res);

                }
            });
        }

    }

    public void addSpinner(final String id) {

        final Spinner spinner = new Spinner(context);
        spinner.setId((int) Math.random() + 1);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ary);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view

        spinner.setAdapter(spinnerArrayAdapter);

        ((ViewGroup) view.findViewById(R.id.options_layout)).addView(spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Utils.SOP("spp" + spinner.getSelectedItem());
                Utils.savePreferenceValues(context, id, spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

}
