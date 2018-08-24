package com.mohred.studentnetwork.page_chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.ChatMessage;

/**
 * Created by Redan on 3/9/2017.
 */

public class FragmentChatRoom extends Fragment implements View.OnClickListener
{
    private View view;
    private FloatingActionButton floatingActionButton;
    private FirebaseListAdapter<ChatMessage> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                                      @Nullable Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_chat_room, container, false);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        displayChatMessages();

        return view;
    }

    private void displayChatMessages()
    {
        ListView listOfMessages = (ListView)view.findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(getActivity(), ChatMessage.class,
                                    R.layout.message, FirebaseDatabase.getInstance().getReference())
        {
            @Override
            protected void populateView(View v, ChatMessage model, int position)
            {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                                                                            model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.fab:
                postAMessage();
                break;
        }
    }

    private void postAMessage()
    {
        EditText input = (EditText)view.findViewById(R.id.input);
        FirebaseDatabase.getInstance()
                .getReference()
                .push()
                .setValue(new ChatMessage(input.getText().toString(), DataManager.getInstance().
                                                       getCurrentUser(getContext()).getUsername())
                );

        // Clear the input
        input.setText("");
    }
}
