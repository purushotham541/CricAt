package com.example.purushotham.vollyexample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable
{
    private List<Model> match_list;
    private List<Model> match_list_filter;

    Context context;
    public MyAdapter(List<Model> match_list, Context context) {
        this.match_list = match_list;
        this.context = context;
        match_list_filter=new ArrayList<>(match_list);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.mydesign,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        viewHolder.team1.setText(match_list.get(position).team1);
        viewHolder.team2.setText(match_list.get(position).team2);
        viewHolder.match_status.setText(match_list.get(position).match_status);
        viewHolder.match_type.setText(match_list.get(position).match_type);
        viewHolder.date.setText(match_list.get(position).date);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String options[]={"Match details","Player details"};
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(which==0)
                                {
                                    String uid=match_list.get(position).uniqid;
                                    String date=match_list.get(position).getDate();
                                    Toast.makeText(context,uid+"\n"+date,Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(context,MatchDetails.class);

                                    intent.putExtra("uid",uid);
                                    intent.putExtra("date",date);
                                    context.startActivity(intent);

                                }
                                if(which==1)
                                {
                                    String uid=match_list.get(position).uniqid;

                                    Intent intent=new Intent(context,PlayersDetails.class);

                                    intent.putExtra("uid",uid);

                                    context.startActivity(intent);

                                }

                            }
                        });
                builder.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return match_list.size();
    }

    @Override
    public Filter getFilter() {
        return mySearchFilter;
    }
    private Filter mySearchFilter=new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            List<Model> filtered_result=new ArrayList<>();
            if(constraint==null||constraint.length()==0)
            {
                filtered_result.addAll(match_list);
            }
            else
                {
                  String searchelement= constraint.toString().toLowerCase().trim();

                   for(Model model:match_list_filter)
                   {
                       if(model.getTeam1().toLowerCase().contains(searchelement))
                       {
                           filtered_result.add(model);
                       }
                   }

               }
               FilterResults filterResults=new FilterResults();
                filterResults.values=filtered_result;
                return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            match_list.clear();
            match_list.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView team1,team2,match_type,match_status,date;
        CardView cardView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            team1=itemView.findViewById(R.id.team1);
            team2=itemView.findViewById(R.id.team2);
            match_type=itemView.findViewById(R.id.m_type);
            match_status=itemView.findViewById(R.id.m_status);
            date=itemView.findViewById(R.id.m_date);
            cardView=itemView.findViewById(R.id.cardview);

        }
    }


}
