package com.example.someandroidfunc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder>{
    ArrayList<Person> personList;
    LayoutInflater inflater;
    OnPersonListener onPersonListener;

    public PersonAdapter(Context context, ArrayList<Person> personList, OnPersonListener onPersonListener){
        inflater = LayoutInflater.from(context);
        this.personList = personList;
        this.onPersonListener = onPersonListener;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_person_card, parent, false);
        PersonViewHolder holder = new PersonViewHolder(view, onPersonListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position){
        Person selectedPerson = personList.get(position);
        holder.setData(selectedPerson,position);
    }

    @Override
    public int getItemCount(){
        return personList.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView personUserName, personPassword;
        ImageView personImage;
        boolean passwordHidden = false;
        String password;

        OnPersonListener onPersonListener;

        public PersonViewHolder(View itemView, OnPersonListener onPersonListener){
            super(itemView);
            personUserName = itemView.findViewById(R.id.itemPersonUserName);
            personPassword = itemView.findViewById(R.id.itemPersonPassword);
            personImage = itemView.findViewById(R.id.itemPersonImage);

            this.onPersonListener = onPersonListener;
            itemView.setOnClickListener(this);
        }

        public void setData(Person selectedPerson, int position){
            password = selectedPerson.getPassword();
            this.personUserName.setText(selectedPerson.getUserName());
            this.personPassword.setText(selectedPerson.getPassword());
            this.personImage.setImageResource(selectedPerson.getImageID());
        }
        @Override
        public void onClick(View v){
            if(!passwordHidden){
                passwordHidden = true;
                personPassword.setText("***");
            }else{
                passwordHidden = false;
                personPassword.setText(password);
            }
            onPersonListener.onPersonClick(getAdapterPosition());
        }
    }
    public interface OnPersonListener{
        void onPersonClick(int position);
    }

}
