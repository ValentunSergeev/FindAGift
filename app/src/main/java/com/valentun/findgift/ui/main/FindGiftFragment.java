package com.valentun.findgift.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.valentun.findgift.R;
import com.valentun.findgift.core.main.adapters.MainGiftAdapter;
import com.valentun.findgift.models.Gift;
import com.valentun.findgift.network.callback.BaseCallback;
import com.valentun.findgift.ui.abstracts.ApiFragment;
import com.valentun.findgift.ui.newgift.NewGiftActivity;
import com.valentun.findgift.utils.SearchUtils;
import com.valentun.findgift.utils.WidgetUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.valentun.findgift.Constants.FAB_SCROLL_THRESHOLD;
import static com.valentun.findgift.utils.WidgetUtils.hideKeyboard;

public class FindGiftFragment extends ApiFragment {
    private static final int NEW_GIFT_REQUEST_CODE = 1;

    @BindView(R.id.main_recycler) RecyclerView recyclerView;
    @BindView(R.id.main_progress) ProgressBar progressBar;
    @BindView(R.id.new_gift_button) FloatingActionButton fab;
    @BindView(R.id.slide_panel) ExpandableRelativeLayout slidePanel;
    @BindView(R.id.expand_button) ImageView expandButton;
    @BindView(R.id.age_search) EditText ageField;
    @BindView(R.id.gender_search) RadioGroup genderField;
    @BindView(R.id.event_search) Spinner eventField;
    @BindView(R.id.age_clear) ImageView clearAge;
    @BindView(R.id.toggle_panel) View toggle;

    private boolean isExpanded = false, isQueryChanged = false;
    private String selectedGender = null, selectedAge = null, selectedEvent = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_gift, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setListeners();

        setUpRecycler();

        setListeners();
    }

    private void refreshData() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        makeRequest();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_GIFT_REQUEST_CODE && resultCode == RESULT_OK) {
            showSnackbarMessage(R.string.gift_created_message);
            refreshData();
        }
    }

    private void setUpRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(parent));
        recyclerView.setAdapter(new MainGiftAdapter(null));
    }

    @Override
    protected void makeRequest() {
        apiClient.getGifts(selectedAge, selectedGender, selectedEvent)
                .enqueue(new BaseCallback<List<Gift>>(container, progressBar) {
                    @Override
                    public void onResponse(Call<List<Gift>> call, Response<List<Gift>> response) {
                        List<Gift> result = response.body();
                        recyclerView.setAdapter(new MainGiftAdapter(result));
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void setListeners() {
        ageField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    WidgetUtils.hideKeyboard(view);
                }
            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int drawableId = isExpanded ? R.drawable.ic_arrow_down_24dp : R.drawable.ic_arrow_up_24dp;
                expandButton.setImageResource(drawableId);
                slidePanel.toggle(100, new LinearInterpolator());
                isExpanded = !isExpanded;
            }
        });

        clearAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ageField.getText().clear();
                hideKeyboard(parent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, NewGiftActivity.class);
                startActivityForResult(intent, NEW_GIFT_REQUEST_CODE);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > FAB_SCROLL_THRESHOLD && fab.isShown())
                    fab.hide();
                else if (dy < -FAB_SCROLL_THRESHOLD && !isExpanded && !fab.isShown())
                    fab.show();
            }
        });

        genderField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                isQueryChanged = true;
            }
        });

        eventField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                isQueryChanged = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                isQueryChanged = true;
            }
        });

        slidePanel.setListener(new MainExpandableListener());
        ageField.addTextChangedListener(new AgeSearchWatcher());
    }

    private class MainExpandableListener implements ExpandableLayoutListener {

        @Override
        public void onAnimationStart() {
        }

        @Override
        public void onAnimationEnd() {
        }

        @Override
        public void onPreOpen() {
            fab.hide();
        }

        @Override
        public void onPreClose() {
            fab.show();
            hideKeyboard(parent);
            if (isQueryChanged) {
                int id = genderField.getCheckedRadioButtonId();
                int position = eventField.getSelectedItemPosition();

                selectedGender = SearchUtils.getGenderFromButtonId(id);
                selectedAge = ageField.getText().toString();
                selectedEvent = SearchUtils.getEventFromSelectedPosition(parent, position);

                refreshData();

                isQueryChanged = false;
            }
        }

        @Override
        public void onOpened() {
        }

        @Override
        public void onClosed() {
        }
    }

    private class AgeSearchWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() > 0) clearAge.setVisibility(View.VISIBLE);
            else clearAge.setVisibility(View.GONE);
            isQueryChanged = true;
        }
    }
}
