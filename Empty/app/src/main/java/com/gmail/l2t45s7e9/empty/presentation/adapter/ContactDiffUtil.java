package com.gmail.l2t45s7e9.empty.presentation.adapter;

import androidx.recyclerview.widget.DiffUtil;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import java.util.List;
public class ContactDiffUtil extends DiffUtil.Callback {

    private List<Contact> oldList;
    private List<Contact> newList;

    public ContactDiffUtil(List<Contact> oldList, List<Contact> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Contact oldContact = oldList.get(oldItemPosition);
        Contact newContact = newList.get(newItemPosition);
        return oldContact.getId() == newContact.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).getName() == newList.get(newItemPosition).getName() &&
                newList.get(newItemPosition).getFirstNumber() == newList.get(newItemPosition).getFirstNumber() &&
                newList.get(newItemPosition).getContactColor() == newList.get(newItemPosition).getContactColor();
    }
}
