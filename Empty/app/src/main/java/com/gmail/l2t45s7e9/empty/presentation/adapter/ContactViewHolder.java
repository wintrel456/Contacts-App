package com.gmail.l2t45s7e9.empty.presentation.adapter;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.l2t45s7e9.empty.R;
import com.gmail.l2t45s7e9.empty.entity.Contact;
public class ContactViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView number;
    private ImageView avatar;

    public ContactViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.userName);
        number = itemView.findViewById(R.id.userNumber);
        avatar = itemView.findViewById(R.id.avatar);
    }

    public void bind(Contact contact) {
        name.setText(contact.getName());
        number.setText(contact.getFirstNumber());
        avatar.setBackgroundTintList(ColorStateList.valueOf(contact.getContactColor()));
    }
}
