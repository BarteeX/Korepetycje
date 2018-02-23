package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Term;

import java.util.List;

/**
 * Created by Monika on 2018-02-04.
 */

public class TermsArrayAdapter extends ArrayAdapter<Term> {
    private List<Term> terms;
    private Activity context;

    private int resource;

    public TermsArrayAdapter(@NonNull Activity context, int resource, @NonNull List<Term> terms) {
        super(context, resource);

        this.resource = resource;
        this.context = context;
        this.terms = terms;
    }

    static public class TermViewHolder {
        public TextView termName;
    }

    @NonNull
    @Override
    public View getView(int position, View currentView, @NonNull ViewGroup parent) {
        TermViewHolder termViewHolder;
        View rowView = currentView;

        Term term = terms.get(position);

        if (rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(resource, null, true);

            termViewHolder = new TermViewHolder();
            termViewHolder.termName = rowView.findViewById(R.id.term_description);

            rowView.setTag(termViewHolder);

        } else {
            termViewHolder = (TermViewHolder) rowView.getTag();
        }

        termViewHolder.termName.setText(term.toString());

        return rowView;
    }

    @Override
    public int getCount() {
        return this.terms.size();
    }


    public List<Term> getTerms() {
        return terms;
    }
}
